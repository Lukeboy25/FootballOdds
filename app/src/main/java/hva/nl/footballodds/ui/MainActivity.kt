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
import android.app.Activity
import kotlinx.android.synthetic.main.fragment_add_club.*
import android.graphics.BitmapFactory
import android.provider.MediaStore

const val DETAIL_MATCH_CODE = 100
const val GALLERY_REQUEST_CODE = 200
lateinit var IMAGE_URL: String

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

    fun pickImageFromGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"

        val mimeTypes = arrayOf("image/jpeg", "image/png")
        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)

        startActivityForResult(intent, GALLERY_REQUEST_CODE)
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK)
            when (requestCode) {
                GALLERY_REQUEST_CODE -> {
                    val selectedImage = data?.data

                    val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)
                    val cursor = contentResolver.query(selectedImage!!, filePathColumn, null, null, null)
                    cursor!!.moveToFirst()
                    val columnIndex = cursor.getColumnIndex(filePathColumn[0])
                    val encodeImageUrl = cursor.getString(columnIndex)
                    cursor.close()

                    ivSelectedImage.setImageBitmap(BitmapFactory.decodeFile(encodeImageUrl))
                    IMAGE_URL = encodeImageUrl
                }
            }
    }
}
