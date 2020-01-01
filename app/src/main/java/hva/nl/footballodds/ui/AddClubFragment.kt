package hva.nl.footballodds.ui


import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import hva.nl.footballodds.R
import hva.nl.footballodds.model.Club
import kotlinx.android.synthetic.main.fragment_add_club.*

/**
 * A Fragment for adding a new club.
 */
class AddClubFragment : Fragment() {
    private lateinit var viewModel: ClubViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProviders.of(this).get(ClubViewModel::class.java)

        return inflater.inflate(R.layout.fragment_add_club, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnSave.setOnClickListener {
            onAddNewClub()
        }
    }

    private fun validateEmptyFields(): Boolean {
        if (etClubName.text.toString().isBlank()) {
            Toast.makeText(activity, "Please fill in a club name!", Toast.LENGTH_SHORT).show()
            return false
        }

        return true
    }

    private fun onAddNewClub() {
        if (validateEmptyFields()) {
            val club = Club(null, etClubName.text.toString(), R.drawable.logo_groot_adodenhaag, false)
            viewModel.addClub(club)

            Toast.makeText(activity, "Successfully added a new club: " + club.name, Toast.LENGTH_SHORT).show()

            // Navigate back
            findNavController().navigate(R.id.action_addClubFragment_to_clubFragment)

            // Hide keyboard
            val imm = context?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view?.windowToken, 0)
        }
    }

}
