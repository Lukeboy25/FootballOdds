package hva.nl.footballodds.ui.fragments

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import hva.nl.footballodds.api.FootballMatchesList
import hva.nl.footballodds.model.Club
import androidx.recyclerview.widget.LinearLayoutManager
import hva.nl.footballodds.*
import hva.nl.footballodds.ui.ClubViewModel
import hva.nl.footballodds.ui.adapters.FootballAdapter
import hva.nl.footballodds.ui.FootballViewModel
import hva.nl.footballodds.ui.MainActivity

/**
 * The Fragment with the responsibility to initialize the list of matches.
 */

@RequiresApi(Build.VERSION_CODES.O)
class HomeFragment : Fragment() {
    private val matchesItems = arrayListOf<FootballMatchesList.Matches>()
    private val clubs = arrayListOf<Club>()
    private lateinit var footballViewModel: FootballViewModel
    private lateinit var clubViewModel: ClubViewModel

    private val footballAdapter = FootballAdapter(matchesItems, clubs) { matchItem ->
            (activity as MainActivity).onDetailMatch(matchItem)
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val rootView = inflater.inflate(R.layout.fragment_home, container, false)
        val rvMatches = rootView.findViewById(R.id.rvMatches) as RecyclerView
        rvMatches.layoutManager = LinearLayoutManager(activity)
        rvMatches.adapter = footballAdapter
        rvMatches.addItemDecoration(DividerItemDecoration(activity, DividerItemDecoration.VERTICAL))

        initViewModels()

        footballViewModel.getFootballMatches()

        return rootView
    }

    private fun initViewModels() {
        footballViewModel = ViewModelProviders.of(this).get(FootballViewModel::class.java)
        clubViewModel = ViewModelProviders.of(this).get(ClubViewModel::class.java)

        footballViewModel.matches.observe(this, Observer { matches ->
            matchesItems.clear()
            matches.data?.forEach { match -> matchesItems.add(match) }
            footballAdapter.notifyDataSetChanged()
        })

        clubViewModel.clubs.observe(this, Observer { clubs ->
            this@HomeFragment.clubs.clear()
            this@HomeFragment.clubs.addAll(clubs)
            footballAdapter.notifyDataSetChanged()
        })
    }
}
