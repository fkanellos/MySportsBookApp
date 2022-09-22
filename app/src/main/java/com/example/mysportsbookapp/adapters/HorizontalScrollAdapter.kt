package com.example.mysportsbookapp.adapters

import android.content.Context
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mysportsbookapp.domain.viewModels.SportsDetailsViewModel
import com.example.mysportsbookapp.R
import com.example.mysportsbookapp.common.Constants
import kotlinx.android.synthetic.main.details_item.view.*
import java.util.concurrent.TimeUnit

class HorizontalScrollAdapter(
    private var sportsDetailsViewModel: MutableList<SportsDetailsViewModel>,
) :
    RecyclerView.Adapter<HorizontalScrollAdapter.HorizontalViewHolder>() {

    //High-order function for favourite toggle
    private var onToggleButtonClicked: ((Boolean, Int) -> Unit) = { isChecked: Boolean, i: Int ->
        this.sportsDetailsViewModel[i].favourite = isChecked
        this.sportsDetailsViewModel.sortByDescending { it.favourite }
        notifyDataSetChanged()
    }

    inner class HorizontalViewHolder(
        view: View,
        private var onToggleButtonClicked: (Boolean, Int) -> Unit,
    ) : RecyclerView.ViewHolder(view) {
        private var toggleButton = view.favouriteBtn
        private var textViewTeam1 = view.textView1
        private var textViewTeam2 = view.textView2
        var countdownTextureView = view.countdown
        var countDownTimer: CountDownTimer? = null

        fun bind(data: SportsDetailsViewModel) {
            //set info for games
            textViewTeam1.text = data.team1
            textViewTeam2.text = data.team2

            //favourite icon
            toggleButton.setOnCheckedChangeListener(null)
            toggleButton.isChecked = data.favourite


            toggleButton.setOnCheckedChangeListener { _, isChecked ->
                data.favourite = isChecked
                //use High-order function
                onToggleButtonClicked.invoke(isChecked, adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HorizontalViewHolder {
        val inflater =
            LayoutInflater.from(parent.context).inflate(R.layout.details_item, parent, false)
        return HorizontalViewHolder(inflater, onToggleButtonClicked)
    }

    override fun onBindViewHolder(holder: HorizontalViewHolder, position: Int) {

        holder.countDownTimer?.cancel()
        holder.countDownTimer = startTimer(
            holder.countdownTextureView,
            sportsDetailsViewModel[position],
            holder.itemView.context
        ).start()

        holder.bind(sportsDetailsViewModel[position])
    }

    override fun getItemCount(): Int {
        return sportsDetailsViewModel.size
    }

    //countDown function
    private fun startTimer(
        countdownTextureView: TextView,
        data: SportsDetailsViewModel,
        context: Context?,
    ): CountDownTimer {

        val timeOfEventMillis: Long? =
            data.timeStart?.times(Constants.CHANGE_TO_MILLIS)
        return object : CountDownTimer(timeOfEventMillis!!, Constants.MILLIS_TO_SEC) {

            override fun onTick(millisUntilFinished: Long) {
                countdownTextureView.text = context?.getString(
                    R.string.formatted_time,
                    TimeUnit.MILLISECONDS.toHours(millisUntilFinished) % 60,
                    TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) % 60,
                    TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) % 60
                )
                data.timeStart = millisUntilFinished.div(Constants.CHANGE_TO_MILLIS)
            }

            override fun onFinish() {
                //no documentation
            }
        }
    }
}
