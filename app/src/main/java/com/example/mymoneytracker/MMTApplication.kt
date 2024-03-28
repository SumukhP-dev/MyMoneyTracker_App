package com.example.mymoneytracker

import android.app.Application
import com.example.mymoneytracker.ui.history.ItemsViewModel

// Static class to store data
class MMTApplication: Application() {
    // ArrayList of class ItemsViewModel
    var data = ArrayList<ItemsViewModel>()
}