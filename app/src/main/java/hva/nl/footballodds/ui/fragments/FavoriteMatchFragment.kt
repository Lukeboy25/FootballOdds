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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import hva.nl.footballodds.R
import hva.nl.footballodds.api.FootballMatchesList
import hva.nl.footballodds.model.Club
import hva.nl.footballodds.ui.*
import hva.nl.footballodds.ui.adapters.FootballAdapter

/**
 * Fragment that show your personal favorite matches based on club.
 */
@RequiresApi(Build.VERSION_CODES.O)
class FavoriteMatchFragment : Fragment() {
    private val favoriteMatchItems = arrayListOf<FootballMatchesList.Matches>()
    private val distinctFavoriteMatchItems = arrayListOf<FootballMatchesList.Matches>()
    private val clubs = arrayListOf<Club>()
    private val favoriteClubs = arrayListOf<Club>()
    private lateinit var clubViewModel: ClubViewModel
    private lateinit var footballViewModel: FootballViewModel

    private val favoriteMatchAdapter = FootballAdapter(distinctFavoriteMatchItems, clubs) { matchItem ->
        (activity as MainActivity).onDetailMatch(matchItem)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val rootView = inflater.inflate(R.layout.fragment_favorite_matches, container, false)
        val rvFavoriteMatches = rootView.findViewById(R.id.rvFavoriteMatches) as RecyclerView
        rvFavoriteMatches.layoutManager = LinearLayoutManager(activity)
        rvFavoriteMatches.adapter = favoriteMatchAdapter

        initViewModels()

        footballViewModel.getFootballMatches()

        return rootView
    }

    private fun initViewModels() {
        footballViewModel = ViewModelProviders.of(this).get(FootballViewModel::class.java)
        clubViewModel = ViewModelProviders.of(this).get(ClubViewModel::class.java)

        clubViewModel.favoriteClubs.observe(this, Observer { favoriteClubs ->
            this@FavoriteMatchFragment.favoriteClubs.clear()
            this@FavoriteMatchFragment.favoriteClubs.addAll(favoriteClubs)
        })

        clubViewModel.clubs.observe(this, Observer { clubs ->
            this@FavoriteMatchFragment.clubs.clear()
            this@FavoriteMatchFragment.clubs.addAll(clubs)
        })

        footballViewModel.matches.observe(this, Observer { matches ->
            favoriteMatchItems.clear()
            matches.data?.forEach { match ->
                for (favoriteClub in favoriteClubs) {
                    if(favoriteClub.name == match.teams[0] || favoriteClub.name == match.teams[1]) {
                        favoriteMatchItems.add(match)
                    }
                }
            }
            distinctFavoriteMatchItems.addAll(favoriteMatchItems.distinct())
            favoriteMatchAdapter.notifyDataSetChanged()
        })
    }
}
