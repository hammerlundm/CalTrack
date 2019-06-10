package com.maxhammerlund.caltrack

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_food_edit.*

class FoodEditActivity : AppCompatActivity() {
    var fragments: MutableMap<FoodFragment, Food> = mutableMapOf()
    lateinit var list: MutableList<Food>

    override fun onCreate(savedInstanceState: Bundle?) {
        if (intent.hasExtra("list")) {
            val a = application as CalTrackApplication
            when (intent.getStringExtra("list")) {
                "food_today" -> list = a.food_today
                "foods" -> list = a.foods
            }
        }
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_food_list)
        val asdf = supportFragmentManager.beginTransaction()
        for (food in list) {
            val f = FoodFragment.newInstance(food)
            fragments[f] = food
            asdf.add(R.id.list, f)
        }
        asdf.commit()
    }

    override fun onStart() {
        super.onStart()
        if (!list.isEmpty()) {
            empty_text?.visibility = TextView.INVISIBLE
        }
        for ((frag, food) in fragments) {
            frag.view?.setOnClickListener { v: View ->
                val a = application as CalTrackApplication
                list.remove(food)
                a.saveData()
                a.writeFoods()
                val i = Intent(this, MainActivity::class.java)
                startActivity(i)
            }
        }
    }
}
