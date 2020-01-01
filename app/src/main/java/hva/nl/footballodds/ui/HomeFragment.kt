package hva.nl.footballodds.ui

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import hva.nl.footballodds.api.FootballMatchesList
import hva.nl.footballodds.model.Club
import androidx.recyclerview.widget.LinearLayoutManager
import hva.nl.footballodds.*

/**
 * The Fragment with the responsibility to initialize the list of matches.
 */

const val DETAIL_MATCH_CODE = 100

@RequiresApi(Build.VERSION_CODES.O)
class HomeFragment : Fragment() {
    private val matchesItems = arrayListOf<FootballMatchesList.Matches>()
    private val clubs = arrayListOf<Club>()
    private lateinit var footballViewModel: FootballViewModel
    private lateinit var clubViewModel: ClubViewModel

    private val footballAdapter =
        FootballAdapter(matchesItems, clubs) { matchItem ->
            onDetailMatch(matchItem)
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val rootView = inflater.inflate(R.layout.fragment_home, container, false)
        val rvMatches = rootView.findViewById(R.id.rvMatches) as RecyclerView
        rvMatches.layoutManager = LinearLayoutManager(activity)
        rvMatches.adapter = footballAdapter

        initViewModel()

        footballViewModel.getFootballMatches()

        return rootView
    }

    private fun initViewModel() {
        footballViewModel = ViewModelProviders.of(this).get(FootballViewModel::class.java)
        clubViewModel = ViewModelProviders.of(this).get(ClubViewModel::class.java)

        footballViewModel.matches.observe(this, Observer { matches ->
            matchesItems.clear()
            matches.data?.forEach { movie -> matchesItems.add(movie) }
            footballAdapter.notifyDataSetChanged()
        })

        clubViewModel.clubs.observe(this, Observer { clubs ->
            this@HomeFragment.clubs.clear()
            this@HomeFragment.clubs.addAll(clubs)
            footballAdapter.notifyDataSetChanged()
        })
    }

    private fun onDetailMatch(matchItem : FootballMatchesList.Matches) {
        val intent = Intent(activity!!.baseContext, DetailMatch::class.java)
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
