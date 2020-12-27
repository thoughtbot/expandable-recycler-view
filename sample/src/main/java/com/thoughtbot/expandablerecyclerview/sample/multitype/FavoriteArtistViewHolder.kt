package com.thoughtbot.expandablerecyclerview.sample.multitype

import android.view.View
import android.widget.TextView
import com.thoughtbot.expandablerecyclerview.sample.R
import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder

class FavoriteArtistViewHolder(itemView: View) : ChildViewHolder(itemView) {
    private val favoriteArtistName: TextView
    fun setArtistName(name: String?) {
        favoriteArtistName.text = name
    }

    init {
        favoriteArtistName = itemView.findViewById<View>(R.id.list_item_favorite_artist_name) as TextView
    }
}