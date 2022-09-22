package com.example.mysportsbookapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mysportsbookapp.R
import com.example.mysportsbookapp.domain.viewModels.SportsTitleViewModel
import com.example.mysportsbookapp.common.Utils
import kotlinx.android.synthetic.main.group_item.view.*

class VerticalScrollAdapter :
    RecyclerView.Adapter<VerticalScrollAdapter.VerticalViewHolder>() {

    private var data: List<SportsTitleViewModel> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VerticalViewHolder {
        val inflater =
            LayoutInflater.from(parent.context).inflate(R.layout.group_item, parent, false)
        return VerticalViewHolder(inflater)
    }

    override fun onBindViewHolder(holder: VerticalViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun updateView(prepareDataForExpandableAdapter: List<SportsTitleViewModel>) {
        data = prepareDataForExpandableAdapter
        notifyDataSetChanged()
    }

    class VerticalViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        private var imageViewIcon = view.icon
        private var sportTextureView = view.sportTextview
        private var toggleButton = view.arrowToggle
        private var relativeLayout = view.bottomLayout
        private var recyclerView = view.horizontalRecycler

        fun bind(data: SportsTitleViewModel) {
            //hide view for games when app open
            relativeLayout.visibility = View.GONE
            toggleButton.isChecked

            //sports categories
            sportTextureView.text = data.sport

            //sports images
            imageViewIcon.background = view.context?.let {
                data.sport?.let { it1 ->
                    Utils.fetchDrawable(it1, it)
                }
            }

            //hide and show view for games
            toggleButton.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    relativeLayout.visibility = View.VISIBLE
                } else {
                    relativeLayout.visibility = View.GONE
                }
            }

            //set horizontal adapter for games
            recyclerView.apply {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                val horizontalRecyclerViewAdapter =
                    HorizontalScrollAdapter(data.sportsDetailsViewModel)
                adapter = horizontalRecyclerViewAdapter

            }

        }
    }
}