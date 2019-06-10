package com.maxhammerlund.caltrack

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputFilter
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.core.app.NavUtils
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_new_food.*
import java.lang.Exception

class NewFoodActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_food)
        val f = InputFilter {source, start, end, dest, dstart, dend ->
            for (char in source) {
                if (!char.isLetterOrDigit()) {
                    return@InputFilter ""
                }
            }
            null
        }
        name_str.filters = arrayOf(f)
    }

    fun addFood(view: View) {
        val name: String
        val cals: Int
        val carbs: Int
        val fat: Int
        val protein: Int
        try {
            name = name_str.text.toString()
            cals = calorie_num.text.toString().toInt()
            carbs = carb_num.text.toString().toInt()
            fat = fat_num.text.toString().toInt()
            protein = protein_num.text.toString().toInt()
        }
        catch (e: Exception) {
            val s = Snackbar.make(view, getString(R.string.invalid_add), 200)
            s.show()
            return
        }
        val f = Food(name, cals, carbs, fat, protein)
        val app = application as CalTrackApplication
        app.foods.add(f)
        app.writeFoods()
        NavUtils.navigateUpFromSameTask(this)
    }
}
