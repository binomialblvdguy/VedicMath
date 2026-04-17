package com.vedicmath.app.ui.screens

import android.os.Bundle
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.vedicmath.app.R
import com.vedicmath.app.ui.screens.fragments.VedicCalculatorFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            showCalculatorFragment()
        }

        onBackPressedDispatcher.addCallback(this) {
            if (supportFragmentManager.backStackEntryCount > 0) {
                supportFragmentManager.popBackStack()
            } else {
                finish()
            }
        }
    }

    fun replaceFragment(fragment: Fragment, addToBackStack: Boolean = true) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.main_container, fragment)
            if (addToBackStack) addToBackStack(null)
            commit()
        }
    }

    private fun showCalculatorFragment() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_container, VedicCalculatorFragment())
            .commit()
    }
}