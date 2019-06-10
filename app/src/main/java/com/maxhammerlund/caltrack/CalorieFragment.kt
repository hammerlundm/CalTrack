package com.maxhammerlund.caltrack

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_calorie.*

class CalorieFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_calorie, container, false)
    }

    fun setValue() {
        var calories = 0
        var carbs = 0
        var fat = 0
        var protein = 0
        val act = activity
        if (act != null) {
            val a = act.application as CalTrackApplication
            for (item in a.food_today) {
                calories += item.calories
                carbs += item.carbs
                fat += item.fat
                protein += item.protein
            }
            calorie_bar.progress = calories
            carb_bar.progress = carbs
            fat_bar.progress = fat
            protein_bar.progress = protein
        }
    }

    override fun onStart() {
        super.onStart()
        val app = activity?.application as CalTrackApplication
        calorie_bar.max = app.goal_calories
        carb_bar.max = app.goal_carbs
        fat_bar.max = app.goal_fat
        protein_bar.max = app.goal_protein
        setValue()
    }
}
