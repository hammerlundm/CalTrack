package com.maxhammerlund.caltrack

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import androidx.viewpager.widget.ViewPager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_calorie.*

class MainActivity : AppCompatActivity() {
    private lateinit var pagerAdapter: PagerAdapter
    private lateinit var viewPager: ViewPager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        pagerAdapter = PagerAdapter(supportFragmentManager)
        viewPager = findViewById(R.id.pager)
        viewPager.adapter = pagerAdapter
        setSupportActionBar(toolbar)
    }

    override fun onResume() {
        super.onResume()
        val app = application as CalTrackApplication
        app.loadData()
        app.readFoods()
        if (app.food_today.size == 0) {
            val cal_frag = pagerAdapter.getItem(0) as CalorieFragment
            cal_frag.setValue()
            val list_frag = pagerAdapter.getItem(1) as FoodListFragment
            list_frag.populate()
        }
    }

    override fun onPause() {
        super.onPause()
        val app = application as CalTrackApplication
        app.saveData()
        app.writeFoods()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.action_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.change_goal -> changeGoal()
            R.id.new_food -> newFood()
            R.id.list -> deleteFood()
            R.id.delete_log -> deleteItem()
        }
        return true
    }

    fun newFood() {
        val i = Intent(this, NewFoodActivity::class.java)
        startActivity(i)
    }

    fun foodList(view: View) {
        val i = Intent(this, FoodListActivity::class.java)
        startActivity(i)
    }

    fun changeGoal() {
        val i = Intent(this, ChangeGoalsActivity::class.java)
        startActivity(i)
    }

    fun deleteFood() {
        val i = Intent(this, FoodEditActivity::class.java)
        i.putExtra("list", "foods")
        startActivity(i)
    }

    fun deleteItem() {
        val i = Intent(this, FoodEditActivity::class.java)
        i.putExtra("list", "food_today")
        startActivity(i)
    }
}

data class Food (val name: String, val calories: Int, val carbs: Int, val fat: Int, val protein: Int)