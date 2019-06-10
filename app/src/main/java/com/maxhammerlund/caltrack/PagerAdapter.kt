package com.maxhammerlund.caltrack

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class PagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
    private var calorieFragment = CalorieFragment()
    private var foodListFragment = FoodListFragment()

    override fun getCount(): Int = 2

    override fun getItem(i: Int): Fragment {
        if (i == 0) {
            return calorieFragment
        }
        else if (i == 1) {
            return foodListFragment
        }
        else {
            throw IndexOutOfBoundsException("There are only two fragments")
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        if (position == 0) {
            return "Calories"
        }
        else if (position == 1) {
            return "Today's Food"
        }
        else {
            throw java.lang.IndexOutOfBoundsException("There are only two pages")
        }
    }

}