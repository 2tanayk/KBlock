package com.kamathtanay.kblock.util

import android.content.Context
import android.content.SharedPreferences

object SharedPrefsUtil {

    fun createAndEditSharedPrefs(context: Context, name: String): SharedPreferences.Editor =
        context.getSharedPreferences(name, Context.MODE_PRIVATE).edit()

    fun retrieveSharedPrefs(context: Context, name: String): SharedPreferences =
        context.getSharedPreferences(name, Context.MODE_PRIVATE)

}