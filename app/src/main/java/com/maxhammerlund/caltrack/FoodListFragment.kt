package com.maxhammerlund.caltrack

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import kotlinx.android.synthetic.main.fragment_food_list.*

class FoodListFragment : Fragment() {
    var food_list: MutableList<Food> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        populate()

    }

    fun populate() {
        val act = activity
        if (act != null) {
            val app = act.application as CalTrackApplication
            val asdf = fragmentManager?.beginTransaction()
            list?.removeAllViews()
            for (food in app.food_today) {
                val f = FoodFragment.newInstance(food)
                food_list.add(food)
                asdf?.add(R.id.list, f)
            }
            asdf?.commit()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_food_list, container, false)
    }

    override fun onStart() {
        super.onStart()
        val app = activity?.application as CalTrackApplication
        if (!app.food_today.isEmpty()) {
            empty_text.visibility = TextView.INVISIBLE
        }
    }
}
