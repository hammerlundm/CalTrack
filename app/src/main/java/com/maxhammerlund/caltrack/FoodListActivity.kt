package com.maxhammerlund.caltrack

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_food_list.*

class FoodListActivity : AppCompatActivity() {
    var fragments: MutableMap<FoodFragment, Food> = mutableMapOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_food_list)
        val asdf = supportFragmentManager.beginTransaction()
        val a = application as CalTrackApplication
        for (food in a.foods) {
            val f = FoodFragment.newInstance(food)
            fragments[f] = food
            asdf.add(R.id.list, f)
        }
        asdf.commit()
    }

    override fun onStart() {
        super.onStart()
        val a = application as CalTrackApplication
        if (!a.foods.isEmpty()) {
            empty_text.visibility = TextView.INVISIBLE
        }
        for ((frag, food) in fragments) {
            frag.view?.setOnClickListener { v: View ->
                val app = application as CalTrackApplication
                app.food_today.add(food)
                app.saveData()
                val i = Intent(this, MainActivity::class.java)
                startActivity(i)
            }
        }
    }
}