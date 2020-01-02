package hva.nl.footballodds.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import hva.nl.footballodds.*
import hva.nl.footballodds.model.Club
import hva.nl.footballodds.ui.adapters.ClubAdapter
import hva.nl.footballodds.ui.ClubViewModel
import kotlinx.android.synthetic.main.fragment_club.*

class ClubFragment : Fragment() {
    private val clubs = arrayListOf<Club>()
    private lateinit var viewModel: ClubViewModel

    private val clubAdapter = ClubAdapter(clubs) { club -> onFavoriteClub(club) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val rootView = inflater.inflate(R.layout.fragment_club, container, false)
        val rvClubs = rootView.findViewById(R.id.rvClubs) as RecyclerView
        rvClubs.layoutManager = LinearLayoutManager(activity)
        rvClubs.adapter = clubAdapter
        createItemTouchHelper().attachToRecyclerView(rvClubs)

        initViewModel()

        return rootView
    }

    private fun initViewModel() {
        viewModel = ViewModelProviders.of(this).get(ClubViewModel::class.java)

        viewModel.clubs.observe(this, Observer { clubs ->
            this@ClubFragment.clubs.clear()
            this@ClubFragment.clubs.addAll(clubs)
            clubAdapter.notifyDataSetChanged()
        })


        /** Uncomment when list of clubs needs to be filled */
//        viewModel.deleteAllClubs()
//
//        for (i in Club.CLUB_NAMES.indices) {
//            viewModel.addClub(Club(null, Club.CLUB_NAMES[i], Club.CLUB_LOGO_IDS[i], false))
//        }

    }

    private fun createItemTouchHelper(): ItemTouchHelper {
        val callback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val clubToDelete = clubs[position]

                viewModel.deleteClub(clubToDelete)

                Toast.makeText(activity, "Successfully deleted club: " + clubToDelete.name, Toast.LENGTH_LONG).show()
            }
        }
        return ItemTouchHelper(callback)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ivAddClub.bringToFront()
        ivAddClub.setOnClickListener {
            findNavController().navigate(R.id.action_clubFragment_to_addClubFragment)
        }
    }

    private fun onFavoriteClub(club: Club) {
        viewModel.triggerFavoriteClub(club.name, club.isFavorite!!)
    }
}