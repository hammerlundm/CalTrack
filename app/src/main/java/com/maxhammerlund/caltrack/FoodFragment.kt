package com.maxhammerlund.caltrack

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_food.*

private const val ARG_CALORIES = "calories"
private const val ARG_CARBS = "carbs"
private const val ARG_FAT = "fat"
private const val ARG_PROTEIN = "protein"
private const val ARG_NAME = "name"

open class FoodFragment : Fragment() {
    private var calories: Int? = null
    private var carbs: Int? = null
    private var fat: Int? = null
    private var protein: Int? = null
    private var name: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            calories = it.getInt(ARG_CALORIES)
            carbs = it.getInt(ARG_CARBS)
            fat = it.getInt(ARG_FAT)
            protein = it.getInt(ARG_PROTEIN)
            name = it.getString(ARG_NAME)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_food, container, false)
    }

    override fun onStart() {
        super.onStart()
        calorie_text.text = calories.toString()
        carb_text.text = carbs.toString()
        fat_text.text = fat.toString()
        protein_text.text = protein.toString()
        name_text.text = name
    }

    companion object {
        @JvmStatic
        fun newInstance(name: String, calories: Int, carbs: Int, fat: Int, protein: Int) =
                FoodFragment().apply {
                    arguments = Bundle().apply {
                        putInt(ARG_CALORIES, calories)
                        putInt(ARG_CARBS, carbs)
                        putInt(ARG_FAT, fat)
                        putInt(ARG_PROTEIN, protein)
                        putString(ARG_NAME, name)
                    }
                }
        @JvmStatic
        fun newInstance(food: Food) =
                FoodFragment().apply {
                    arguments = Bundle().apply {
                        putInt(ARG_CALORIES, food.calories)
                        putInt(ARG_CARBS, food.carbs)
                        putInt(ARG_FAT, food.fat)
                        putInt(ARG_PROTEIN, food.protein)
                        putString(ARG_NAME, food.name)
                    }
                }
    }
}
