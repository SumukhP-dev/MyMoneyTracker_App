package com.example.mymoneytracker.model

import android.app.Application
import com.example.mymoneytracker.ui.history.ItemsViewModel
import com.google.firebase.auth.FirebaseUser

class User: Application() {
        companion object {
                // ArrayList of class ItemsViewModel
                var data2 = ArrayList<ItemsViewModel>()
                var dates2 = ArrayList<String>()
                var amounts2 = ArrayList<Int>()
                var netWorthCalculated2 = 0

                // Check if user is signed in (non-null) and update UI accordingly.
                var currentUser2: FirebaseUser? = null

                fun getInstance():User {
                        return User()
                }
        }

        fun getData(): ArrayList<ItemsViewModel> {
                return data2
        }

        fun getDates(): ArrayList<String> {
                return dates2
        }

        fun getAmounts(): ArrayList<Int> {
                return amounts2
        }

        fun getNetWorthCalculated(): Int {
                return netWorthCalculated2
        }

        fun getCurrentUser(): FirebaseUser? {
                return currentUser2
        }

        fun addData(data: ItemsViewModel) {
                data2.add(data)
        }

        fun addDates(date: String) {
                dates2.add(date)
        }

        fun addAmounts(amount: Int) {
                amounts2.add(amount)
        }

        fun setNetWorthCalculated(netWorth: Int) {
                netWorthCalculated2 = netWorth
        }

        fun setCurrentUser(newUser: FirebaseUser?) {
                currentUser2 = newUser
        }
}