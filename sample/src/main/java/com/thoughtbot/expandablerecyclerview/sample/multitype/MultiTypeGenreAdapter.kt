package com.thoughtbot.expandablerecyclerview.sample.multitype

import android.view.LayoutInflater
import android.view.ViewGroup
import com.thoughtbot.expandablerecyclerview.MultiTypeExpandableRecyclerViewAdapter
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup
import com.thoughtbot.expandablerecyclerview.models.ExpandableListPosition
import com.thoughtbot.expandablerecyclerview.sample.Genre
import com.thoughtbot.expandablerecyclerview.sample.R
import com.thoughtbot.expandablerecyclerview.sample.expand.ArtistViewHolder
import com.thoughtbot.expandablerecyclerview.sample.expand.GenreViewHolder
import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder

class MultiTypeGenreAdapter(groups: List<Genre?>?) : MultiTypeExpandableRecyclerViewAdapter<GenreViewHolder, ChildViewHolder>(groups) {
    override fun onCreateGroupViewHolder(parent: ViewGroup, viewType: Int): GenreViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.list_item_genre, parent, false)
        return GenreViewHolder(view)
    }

    override fun onCreateChildViewHolder(parent: ViewGroup, viewType: Int): ChildViewHolder {
        return when (viewType) {
            ARTIST_VIEW_TYPE -> {
                val artist = LayoutInflater.from(parent.context).inflate(R.layout.list_item_artist, parent, false)
                ArtistViewHolder(artist)
            }
            FAVORITE_VIEW_TYPE -> {
                val favorite = LayoutInflater.from(parent.context).inflate(R.layout.list_item_favorite_artist, parent, false)
                FavoriteArtistViewHolder(favorite)
            }
            else -> throw IllegalArgumentException("Invalid viewType")
        }
    }

    override fun onBindChildViewHolder(holder: ChildViewHolder, flatPosition: Int, group: ExpandableGroup<*>,
                                       childIndex: Int) {
        val viewType = getItemViewType(flatPosition)
        val artist = (group as Genre).items[childIndex]!!
        when (viewType) {
            ARTIST_VIEW_TYPE -> (holder as ArtistViewHolder).setArtistName(artist.name)
            FAVORITE_VIEW_TYPE -> (holder as FavoriteArtistViewHolder).setArtistName(artist.name)
        }
    }

    override fun onBindGroupViewHolder(holder: GenreViewHolder, flatPosition: Int,
                                       group: ExpandableGroup<*>) {
        holder.setGenreTitle(group)
    }

    override fun getChildViewType(position: Int, group: ExpandableGroup<*>, childIndex: Int): Int {
        return if ((group as Genre).items[childIndex]!!.isFavorite) {
            FAVORITE_VIEW_TYPE
        } else {
            ARTIST_VIEW_TYPE
        }
    }

    override fun isGroup(viewType: Int): Boolean {
        return viewType == ExpandableListPosition.GROUP
    }

    override fun isChild(viewType: Int): Boolean {
        return viewType == FAVORITE_VIEW_TYPE || viewType == ARTIST_VIEW_TYPE
    }

    companion object {
        const val FAVORITE_VIEW_TYPE = 3
        const val ARTIST_VIEW_TYPE = 4
    }
}