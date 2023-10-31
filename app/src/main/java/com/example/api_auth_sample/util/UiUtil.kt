package com.example.api_auth_sample.util

import android.content.SharedPreferences
import androidx.appcompat.app.ActionBar
import android.view.View
import com.google.android.material.snackbar.Snackbar

class UiUtil {

    companion object {
        fun showSnackBar(layout: View, message: String) {
            val snackBar: Snackbar = Snackbar.make(layout, message, Snackbar.LENGTH_LONG)
            snackBar.show()
        }

        fun hideActionBar(supportActionBar: ActionBar) {
            try {
                supportActionBar.hide()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        fun writeToSharedPreferences(sharedPreferences: SharedPreferences, key: String, value: String) {
            with(sharedPreferences.edit()) {
                putString(key, value)
                apply()
            }
        }

        fun readFromSharedPreferences(sharedPreferences: SharedPreferences, key: String): String? {
            return sharedPreferences.getString(key, null)
        }
    }

}