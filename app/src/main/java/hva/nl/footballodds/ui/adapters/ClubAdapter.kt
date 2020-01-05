package hva.nl.footballodds.ui.adapters

import android.content.Context
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import hva.nl.footballodds.R
import hva.nl.footballodds.model.Club
import kotlinx.android.synthetic.main.club_item.view.*

class ClubAdapter(private val clubs: List<Club>, private val onFavoriteClick: (club : Club) -> Unit) : RecyclerView.Adapter<ClubAdapter.ViewHolder>() {

    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context

        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.club_item, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return clubs.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(clubs[position])
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener {
                onFavoriteClick(clubs[adapterPosition])
            }
        }

        fun bind(club: Club) {
            itemView.tvClubName.text = club.name
            Glide.with(context).load(BitmapFactory.decodeFile(club.imageUrl)).into(itemView.ivClub)

            if(club.isFavorite!!) {
                Glide.with(context).load(R.drawable.ic_star_orange_40dp).into(itemView.ivFavorite)
            } else {
                Glide.with(context).load(R.drawable.ic_star_border_green_40dp).into(itemView.ivFavorite)
            }
        }
    }
}