package hva.nl.footballodds.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import hva.nl.footballodds.R
import kotlinx.android.synthetic.main.activity_detail_match.*
class DetailMatch : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_match)

        tvDetailHomeTeam.text = intent.extras?.get("homeTeam").toString()
        tvDetailAwayTeam.text = intent.extras?.get("awayTeam").toString()
        tvDate.text = intent.extras?.get("date").toString()
        Glide.with(this).load(intent.extras?.get("homeLogo")).into(ivDetailHomeTeam)
        Glide.with(this).load(intent.extras?.get("awayLogo")).into(ivDetailAwayTeam)
    }
}
