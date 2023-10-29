package com.tenkovskaya.testprogect.domain

import android.content.Context

class SharedPreferencesManager(private val context: Context) {
    private val preferences = context.getSharedPreferences(context.packageName, Context.MODE_PRIVATE)

    fun saveLastVisitedPage(url: String) {
        preferences.edit().putString("last_visited_page", url).apply()
    }

    fun getLastVisitedPage(): String {
        return preferences.getString("last_visited_page", "") ?: ""
    }
}