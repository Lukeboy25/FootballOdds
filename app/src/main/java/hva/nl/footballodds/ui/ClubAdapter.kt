package hva.nl.footballodds.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import hva.nl.footballodds.R
import hva.nl.footballodds.model.Club
import kotlinx.android.synthetic.main.club_item.view.*

class ClubAdapter(private val clubs: List<Club>) : RecyclerView.Adapter<ClubAdapter.ViewHolder>() {

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
        fun bind(club: Club) {
            itemView.tvClubName.text = club.name
            Glide.with(context).load(club.imageUrl).into(itemView.ivClub)
        }
    }
}