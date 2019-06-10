package com.maxhammerlund.caltrack

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_change_goals.*

class ChangeGoalsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_goals)
    }

    override fun onStart() {
        super.onStart()
        val a = application as CalTrackApplication
        calorie_text.setText(a.goal_calories.toString())
        carb_text.setText(a.goal_carbs.toString())
        fat_text.setText(a.goal_fat.toString())
        protein_text.setText(a.goal_protein.toString())
    }

    fun update(view: View) {
        val a = application as CalTrackApplication
        a.goal_calories = calorie_text.text.toString().toInt()
        a.goal_carbs = carb_text.text.toString().toInt()
        a.goal_fat = fat_text.text.toString().toInt()
        a.goal_protein = protein_text.text.toString().toInt()
        a.saveData()
        val i = Intent(this, MainActivity::class.java)
        startActivity(i)
    }
}
