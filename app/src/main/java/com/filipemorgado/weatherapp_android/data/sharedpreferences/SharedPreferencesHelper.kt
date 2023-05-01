package com.filipemorgado.weatherapp_android.data.sharedpreferences

import android.content.Context
import com.google.gson.Gson

class SharedPreferencesHelper(context: Context) {
    private val sharedPreferences =
        context.getSharedPreferences("my_preferences", Context.MODE_PRIVATE)

    fun saveObject(key: String, obj: Any) {
        val json = Gson().toJson(obj)
        sharedPreferences.edit().putString(key, json).apply()
    }

    fun getObject(key: String, clazz: Class<*>): Any? {
        val json = sharedPreferences.getString(key, null)
        return Gson().fromJson(json, clazz)
    }
}

