package com.thoughtbot.expandablerecyclerview.sample.singlecheck

import android.view.LayoutInflater
import android.view.ViewGroup
import com.thoughtbot.expandablecheckrecyclerview.CheckableChildRecyclerViewAdapter
import com.thoughtbot.expandablecheckrecyclerview.models.CheckedExpandableGroup
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup
import com.thoughtbot.expandablerecyclerview.sample.Artist
import com.thoughtbot.expandablerecyclerview.sample.R
import com.thoughtbot.expandablerecyclerview.sample.expand.GenreViewHolder

class SingleCheckGenreAdapter(groups: List<SingleCheckGenre?>?) :
        CheckableChildRecyclerViewAdapter<GenreViewHolder, SingleCheckArtistViewHolder>(groups) {
    override fun onCreateCheckChildViewHolder(
            parent: ViewGroup,
            viewType: Int
    ): SingleCheckArtistViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.list_item_singlecheck_arist, parent, false)
        return SingleCheckArtistViewHolder(view)
    }

    override fun onBindCheckChildViewHolder(
            holder: SingleCheckArtistViewHolder, position: Int,
            group: CheckedExpandableGroup, childIndex: Int
    ) {
        val artist = group.items[childIndex] as Artist
        holder.setArtistName(artist.name)
    }

    override fun onCreateGroupViewHolder(parent: ViewGroup, viewType: Int): GenreViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.list_item_genre, parent, false)
        return GenreViewHolder(view)
    }

    override fun onBindGroupViewHolder(
            holder: GenreViewHolder, flatPosition: Int,
            group: ExpandableGroup<*>
    ) {
        holder.setGenreTitle(group)
    }
}