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
                RetrofitInstance.apiService.getApiResponse()
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

    /* set tha values inside the expandable item
    * @titleRow -> the sport value
    * @teamsDetailsRow -> the rival teams and time start */
    fun setValuesForExpandableAdapter(value: List<SportsResponseItem>): List<SportsTitleViewModel> {
        val sportsResult: MutableList<SportsTitleViewModel> = mutableListOf()

        value.forEach {
            val titleRow = SportsTitleViewModel()
            titleRow.sport = it.d
            val gamesList: MutableList<SportsDetailsViewModel> = mutableListOf()
            it.e.forEach { child ->
                val teamDetailsRow = SportsDetailsViewModel()
                teamDetailsRow.timeStart = child.eventStartingTime
                teamDetailsRow.firstTeam =
                    Utils.splitStringAndReturn(child.gameInfo, 2, Regex("-"), 0)
                teamDetailsRow.secondTeam =
                    Utils.splitStringAndReturn(child.gameInfo, 2, Regex("-"), 1)
                gamesList.add(teamDetailsRow)
            }
            titleRow.sportsDetailsViewModel = gamesList
            sportsResult.add(titleRow)
        }
        return sportsResult
    }
}