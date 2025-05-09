package com.project.projectnew;

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    override fun; Object savedInstanceState = new Object();
    onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNavigation = findViewById<BottomNavigationView>(R.id.bottomNavigationLayout)

        // Set default fragment
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, HomeFragment())
                    .commit()
        }

        // Handle navigation item clicks
        bottomNavigation.setOnNavigationItemSelectedListener { item ->
                val selectedFragment: Fragment = when (item.itemId) {
            R.id.menu_home -> HomeFragment()
            R.id.menu_search -> SearchFragment()
            R.id.menu_notification -> NotificationFragment()
            R.id.menu_profile -> ProfileFragment()
                else -> HomeFragment()
        }

            supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, selectedFragment)
                    .commit()

            true
        }
    }
}
