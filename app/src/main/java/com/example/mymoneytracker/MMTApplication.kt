package com.example.mymoneytracker

import android.app.Application
import com.example.mymoneytracker.ui.history.ItemsViewModel
import com.google.firebase.auth.FirebaseUser

// Static class to store data
class MMTApplication: Application() {
    // ArrayList of class ItemsViewModel
    var data = ArrayList<ItemsViewModel>()

    var dates = ArrayList<String>()
    var amounts = ArrayList<Int>()
    var netWorthCalculated = 0

    // Check if user is signed in (non-null) and update UI accordingly.
    var currentUser: FirebaseUser? = null
}