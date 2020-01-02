package hva.nl.footballodds.ui.adapters

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import hva.nl.footballodds.DateFormatter.Companion.formatDutchDateShort
import hva.nl.footballodds.DateFormatter.Companion.getCorrectDate
import hva.nl.footballodds.R
import hva.nl.footballodds.api.FootballMatchesList
import hva.nl.footballodds.model.Club
import kotlinx.android.synthetic.main.match_item.view.*


@RequiresApi(Build.VERSION_CODES.O)
class FootballAdapter(private val matches : List<FootballMatchesList.Matches>, private val clubs : List<Club>, private val onClick: (matchItem : FootballMatchesList.Matches) -> Unit) :
    RecyclerView.Adapter<FootballAdapter.ViewHolder>() {

    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context

        return ViewHolder(
            LayoutInflater.from(context).inflate(R.layout.match_item, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return matches.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(matches[position])
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener {
                onClick(matches[adapterPosition])
            }
        }

        fun bind(matchItem: FootballMatchesList.Matches) {
           val date = getCorrectDate(matchItem.commence_time)
            val homeTeam = matchItem.teams[0]
            val awayTeam = matchItem.teams[1]

            itemView.tvHomeTeam.text = homeTeam
            itemView.tvAwayTeam.text = awayTeam
            itemView.tvTime.text = formatDutchDateShort(date)

            if (matchItem.sites_count!! > 0) {
                val firstMatchOdds = matchItem.sites!![0].odds.h2h

                itemView.tvOddsHome.text = String.format("%.2f", firstMatchOdds[0])
                itemView.tvOddsAway.text = String.format("%.2f", firstMatchOdds[1])
                itemView.tvOddsDraw.text = String.format("%.2f", firstMatchOdds[2])
            }

            for (club in clubs) {
                if (club.name == homeTeam) {
                    Glide.with(context).load(club.imageUrl).into(itemView.ivHomeTeam)
                } else if (club.name == awayTeam) {
                    Glide.with(context).load(club.imageUrl).into(itemView.ivAwayTeam)
                }
            }
        }
    }
}