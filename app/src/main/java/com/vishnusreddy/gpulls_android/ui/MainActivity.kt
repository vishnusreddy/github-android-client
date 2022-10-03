package com.vishnusreddy.gpulls_android.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.FragmentManager
import com.vishnusreddy.gpulls_android.databinding.ActivityMainBinding
import com.vishnusreddy.gpulls_android.ui.login.LoginFragment

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()
        handleOnBackPressed()

        openUserNameFragment()
    }

    private fun openUserNameFragment() {
        this.supportFragmentManager.beginTransaction()
            .replace(binding.fragmentContainerMain.id, LoginFragment())
            .addToBackStack(null).commit()
    }

    private fun handleOnBackPressed() {
        val callback: OnBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val fragmentManager = supportFragmentManager
                if (fragmentManager.backStackEntryCount > 1) {
                    fragmentManager.popBackStack(
                        fragmentManager.getBackStackEntryAt(fragmentManager.backStackEntryCount - 1).id,
                        FragmentManager.POP_BACK_STACK_INCLUSIVE
                    )
                } else {
                    finish()
                }
            }
        }
        this.onBackPressedDispatcher.addCallback(this, callback)
    }
}