package hva.nl.footballodds.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import hva.nl.footballodds.DateFormatter
import hva.nl.footballodds.R
import hva.nl.footballodds.api.FootballMatchesList
import hva.nl.footballodds.model.Club
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

const val DETAIL_MATCH_CODE = 100

class MainActivity : AppCompatActivity() {
    private val clubs = arrayListOf<Club>()
    private lateinit var clubViewModel: ClubViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        initNavigation()
        initViewModel()
    }

    private fun initNavigation() {
        val navController = findNavController(R.id.navHostFragment)

        NavigationUI.setupWithNavController(navView, navController)

        navController.addOnDestinationChangedListener { _, _, _ ->
            navView.visibility = View.VISIBLE
        }
    }

    private fun initViewModel() {
        clubViewModel = ViewModelProviders.of(this).get(ClubViewModel::class.java)

        clubViewModel.clubs.observe(this, Observer { clubs ->
            this@MainActivity.clubs.clear()
            this@MainActivity.clubs.addAll(clubs)
        })
    }

    fun onDetailMatch(matchItem : FootballMatchesList.Matches) {
        val intent = Intent(this.baseContext, DetailMatch::class.java)
        val firstMatchOdds = matchItem.sites!![0].odds.h2h
        val date = DateFormatter.getCorrectDate(matchItem.commence_time)

        intent.putExtra("homeTeam", matchItem.teams[0])
        intent.putExtra("awayTeam", matchItem.teams[1])
        for (club in clubs) {
            if (club.name == matchItem.teams[0]) {
                intent.putExtra("homeLogo", club.imageUrl)
            } else if (club.name == matchItem.teams[1]) {
                intent.putExtra("awayLogo", club.imageUrl)
            }
        }
        intent.putExtra("homeOdd", String.format("%.2f", firstMatchOdds[0]))
        intent.putExtra("awayOdd", String.format("%.2f", firstMatchOdds[1]))
        intent.putExtra("drawOdd", String.format("%.2f", firstMatchOdds[2]))
        intent.putExtra("date", DateFormatter.formatDutchDateLong(date))
        startActivityForResult(intent, DETAIL_MATCH_CODE)
    }
}
