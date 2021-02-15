package com.example.bottomnavigationapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.doOnNextLayout
import com.example.bottomnavigationapp.ui.BottomNavigationAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private val bottomNavigationAdapter by lazy { BottomNavigationAdapter(supportFragmentManager) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bottomNavigationAdapter.createAllTabs()
        val navView = findViewById<BottomNavigationView>(R.id.nav_view)
        navView.setOnNavigationItemSelectedListener {
            if (it.itemId != navView.selectedItemId) {
                bottomNavigationAdapter.openTab(it.itemId)
            }
            true
        }
        navView.doOnNextLayout {
            bottomNavigationAdapter.openTab(R.id.navigation_home)
        }
    }
}