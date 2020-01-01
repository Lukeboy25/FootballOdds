package hva.nl.footballodds.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import hva.nl.footballodds.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        initNavigation()
    }

    private fun initNavigation() {
        val navController = findNavController(R.id.navHostFragment)

        NavigationUI.setupWithNavController(navView, navController)

        navController.addOnDestinationChangedListener { _, _, _ ->
            navView.visibility = View.VISIBLE
        }
    }
}
