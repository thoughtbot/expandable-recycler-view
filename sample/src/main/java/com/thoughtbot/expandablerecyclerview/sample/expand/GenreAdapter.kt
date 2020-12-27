package com.thoughtbot.expandablerecyclerview.sample.expand

import android.view.LayoutInflater
import android.view.ViewGroup
import com.thoughtbot.expandablerecyclerview.ExpandableRecyclerViewAdapter
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup
import com.thoughtbot.expandablerecyclerview.sample.Genre
import com.thoughtbot.expandablerecyclerview.sample.R

class GenreAdapter(groups: List<ExpandableGroup<*>?>?) : ExpandableRecyclerViewAdapter<GenreViewHolder, ArtistViewHolder>(groups) {
    override fun onCreateGroupViewHolder(parent: ViewGroup, viewType: Int): GenreViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.list_item_genre, parent, false)
        return GenreViewHolder(view)
    }

    override fun onCreateChildViewHolder(parent: ViewGroup, viewType: Int): ArtistViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.list_item_artist, parent, false)
        return ArtistViewHolder(view)
    }

    override fun onBindChildViewHolder(holder: ArtistViewHolder, flatPosition: Int,
                                       group: ExpandableGroup<*>, childIndex: Int) {
        val artist = (group as Genre).items[childIndex]!!
        holder.setArtistName(artist.name)
    }

    override fun onBindGroupViewHolder(holder: GenreViewHolder, flatPosition: Int,
                                       group: ExpandableGroup<*>) {
        holder.setGenreTitle(group)
    }
}