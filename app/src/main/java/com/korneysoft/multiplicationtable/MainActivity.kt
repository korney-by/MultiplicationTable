package com.korneysoft.multiplicationtable

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.korneysoft.multiplicationtable.data.SoundRepository

class MainActivity : AppCompatActivity() {

    val repo by lazy { getRepository() }
    private fun getRepository(): SoundRepository? {
        return SoundRepository(this)
    }

    private val navController: NavController by lazy {
        val navHost =
            supportFragmentManager.findFragmentById(R.id.fragmentContainer) as NavHostFragment
        navHost.navController
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        NavigationUI.setupActionBarWithNavController(this, navController)
    }
}
