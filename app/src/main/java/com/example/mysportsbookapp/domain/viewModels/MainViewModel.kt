package com.example.mysportsbookapp.domain.viewModels


import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mysportsbookapp.data.api.RetrofitInstance
import com.example.mysportsbookapp.data.api.dao.SportsResponseItem
import com.example.mysportsbookapp.common.Utils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject
constructor() : ViewModel() {
    private val result = MutableLiveData<List<SportsResponseItem>>()
    val obtainResult: LiveData<List<SportsResponseItem>> = result

    init {
        viewModelScope.launch {
            val response = try {
                RetrofitInstance.api.getTodos()
            } catch (e: IOException) {
                Log.e(TAG, "IOException, you might not have internet connection")
                return@launch
            } catch (e: HttpException) {
                Log.e(TAG, "HttpException, unexpected response")
                return@launch
            }
            if (response.isSuccessful && response.body() != null) {
                result.value = response.body()!!
                Log.e(TAG, response.body().toString())
            } else {
                Log.e(TAG, "Response not successful")
            }
        }
    }

    fun prepareDataForExpandableAdapter(value: List<SportsResponseItem>): List<SportsTitleViewModel> {
        val result: MutableList<SportsTitleViewModel> = mutableListOf()

        value.forEach {
            val parentRow = SportsTitleViewModel()
            parentRow.sport = it.d
            val gamesList: MutableList<SportsDetailsViewModel> = mutableListOf()
            it.e.forEach { child ->
                val childRow = SportsDetailsViewModel()
                childRow.timeStart = child.eventStartingTime
                childRow.team1 = Utils.splitStringAndReturn(child.gameInfo, 2, Regex("-"), 0)
                childRow.team2 = Utils.splitStringAndReturn(child.gameInfo, 2, Regex("-"), 1)
                gamesList.add(childRow)
            }
            parentRow.sportsDetailsViewModel = gamesList
            result.add(parentRow)
        }
        return result
    }
}