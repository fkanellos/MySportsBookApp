package com.example.mysportsbookapp.common

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import com.example.mysportsbookapp.R

object Utils {

    fun splitStringAndReturn(text: String, parts: Int, regex: Regex, partToReturn: Int): String {
        if (text.isEmpty() || partToReturn > parts) {
            return ""
        }
        val result = text.split(regex, parts)

        return when (partToReturn) {
            0 -> result[0].trim()
            1 -> result[1].trim()
            else -> result[2].trim()
        }
    }

    fun fetchDrawable(text: String, context: Context): Drawable {

        return when (text) {

            context.resources.getString(R.string.soccer) -> {
                ContextCompat.getDrawable(context, R.drawable.football_icon)!!
            }

            context.resources.getString(R.string.basketball) -> {
                ContextCompat.getDrawable(context, R.drawable.basketball_icon)!!
            }

            context.resources.getString(R.string.tennis) -> {
                ContextCompat.getDrawable(context, R.drawable.tennis_icon)!!
            }

            context.resources.getString(R.string.table_tennis) -> {
                ContextCompat.getDrawable(context, R.drawable.table_tennis_icon)!!
            }

            context.resources.getString(R.string.volleyball) -> {
                ContextCompat.getDrawable(context, R.drawable.volleyball_icon)!!
            }

            context.resources.getString(R.string.esports) -> {
                ContextCompat.getDrawable(context, R.drawable.esports_icon)!!
            }

            context.resources.getString(R.string.ice_hockey) -> {
                ContextCompat.getDrawable(context, R.drawable.ice_hockey_icon)!!
            }

            context.resources.getString(R.string.handball) -> {
                ContextCompat.getDrawable(context, R.drawable.handball_icon)!!
            }

            context.resources.getString(R.string.beach_volleyball) -> {
                ContextCompat.getDrawable(context, R.drawable.beach_volley_icon)!!
            }

            context.resources.getString(R.string.snooker) -> {
                ContextCompat.getDrawable(context, R.drawable.snooker_icon)!!
            }

            context.resources.getString(R.string.badminton) -> {
                ContextCompat.getDrawable(context, R.drawable.badminton_icon)!!
            }

            //if the dummy call never change it should never run the else code
            else -> {
                ContextCompat.getDrawable(context, R.drawable.football_icon)!!
            }
        }
    }
}