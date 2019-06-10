package com.maxhammerlund.caltrack

import android.app.Application
import android.content.Context
import android.util.Log
import java.io.*
import java.lang.StringBuilder
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern

const val FOODS_FILE = "foods.txt"
const val DATA_FILE = "metadata.txt"

class CalTrackApplication: Application() {
    var foods: MutableList<Food> = mutableListOf()
    var food_today: MutableList<Food> = mutableListOf()
    var goal_calories: Int = 2000
    var goal_carbs: Int = 200
    var goal_fat: Int = 50
    var goal_protein: Int = 50

    fun readFoods() {
        val l = mutableListOf<Food>()
        val reader: FileInputStream
        try {
            reader = applicationContext.openFileInput(FOODS_FILE)
        }
        catch (e: FileNotFoundException) {
            return
        }
        val buff = reader.bufferedReader()
        val p = Pattern.compile("(\\w+)-(\\d+)-(\\d+)-(\\d+)-(\\d+)")
        buff.forEachLine { line ->
            val m = p.matcher(line)
            if (m.matches()) {
                val name = m.group(1)
                val cals = m.group(2).toInt()
                val carbs = m.group(3).toInt()
                val fat = m.group(4).toInt()
                val protein = m.group(5).toInt()
                val food = Food(name, cals, carbs, fat, protein)
                l.add(food)
            }
            else {
                throw StreamCorruptedException("Corrupt Foods File")
            }
        }
        buff.close()
        reader.close()
        foods = l
    }

    fun writeFoods() {
        val s = StringBuilder()
        for (food in foods) {
            s.append(food.name)
            s.append("-")
            s.append(food.calories)
            s.append("-")
            s.append(food.carbs)
            s.append("-")
            s.append(food.fat)
            s.append("-")
            s.append(food.protein)
            s.append("\n")
        }
        val writer = applicationContext.openFileOutput(FOODS_FILE, Context.MODE_PRIVATE)
        val buff = writer.bufferedWriter()
        buff.write(s.toString())
        buff.close()
        writer.close()
    }

    fun loadData() {
        val l = mutableListOf<Food>()
        val reader: FileInputStream
        try {
            reader = applicationContext.openFileInput(DATA_FILE)
        }
        catch (e: FileNotFoundException) {
            return
        }
        val buff = reader.bufferedReader()
        val p = Pattern.compile("(\\w+)-(\\d+)-(\\d+)-(\\d+)-(\\d+)")
        val formatter = SimpleDateFormat.getInstance()
        var i = 0
        var newDay = false
        buff.forEachLine {line ->
            when (i) {
                0 -> {
                    val date_cal = Calendar.getInstance()
                    val today_cal = Calendar.getInstance()
                    val date = formatter.parse(line)
                    val today = Date()
                    date_cal.time = date
                    today_cal.time = today
                    if (date_cal.get(Calendar.DAY_OF_YEAR) != today_cal.get(Calendar.DAY_OF_YEAR) ||
                            date_cal.get(Calendar.YEAR) != today_cal.get(Calendar.YEAR)) {
                        food_today = mutableListOf()
                        newDay = true
                    }
                }
                1 -> goal_calories = line.toInt()
                2 -> goal_carbs = line.toInt()
                3 -> goal_fat = line.toInt()
                4 -> goal_protein = line.toInt()
                else -> {
                    val m = p.matcher(line)
                    if (m.matches()) {
                        val name = m.group(1)
                        val cals = m.group(2).toInt()
                        val carbs = m.group(3).toInt()
                        val fat = m.group(4).toInt()
                        val protein = m.group(5).toInt()
                        val food = Food(name, cals, carbs, fat, protein)
                        l.add(food)
                    }
                    else {
                        throw StreamCorruptedException("Corrupt Metadata File")
                    }
                }
            }
            i += 1
        }
        buff.close()
        reader.close()
        if (!newDay) {
            food_today = l
        }
    }

    fun saveData() {
        val s = StringBuilder()
        val formatter = SimpleDateFormat.getInstance()
        val date = formatter.format(Date())
        s.appendln(date)
        s.appendln(goal_calories)
        s.appendln(goal_carbs)
        s.appendln(goal_fat)
        s.appendln(goal_protein)
        for (food in food_today) {
            s.append(food.name)
            s.append("-")
            s.append(food.calories)
            s.append("-")
            s.append(food.carbs)
            s.append("-")
            s.append(food.fat)
            s.append("-")
            s.append(food.protein)
            s.append("\n")
        }
        val writer = applicationContext.openFileOutput(DATA_FILE, Context.MODE_PRIVATE)
        val buff = writer.bufferedWriter()
        buff.write(s.toString())
        buff.close()
        writer.close()
    }
}