package com.thoughtbot.expandablerecyclerview.sample.multitypeandcheck

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.thoughtbot.expandablecheckrecyclerview.ChildCheckController
import com.thoughtbot.expandablecheckrecyclerview.listeners.OnCheckChildClickListener
import com.thoughtbot.expandablecheckrecyclerview.listeners.OnChildCheckChangedListener
import com.thoughtbot.expandablecheckrecyclerview.listeners.OnChildrenCheckStateChangedListener
import com.thoughtbot.expandablecheckrecyclerview.models.CheckedExpandableGroup
import com.thoughtbot.expandablerecyclerview.MultiTypeExpandableRecyclerViewAdapter
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup
import com.thoughtbot.expandablerecyclerview.sample.Artist
import com.thoughtbot.expandablerecyclerview.sample.R
import com.thoughtbot.expandablerecyclerview.sample.expand.ArtistViewHolder
import com.thoughtbot.expandablerecyclerview.sample.expand.GenreViewHolder
import com.thoughtbot.expandablerecyclerview.sample.singlecheck.SingleCheckArtistViewHolder
import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder
import java.util.*

class MultiTypeCheckGenreAdapter(groups: List<ExpandableGroup<*>?>?) : MultiTypeExpandableRecyclerViewAdapter<GenreViewHolder, ChildViewHolder>(groups), OnChildCheckChangedListener, OnChildrenCheckStateChangedListener {
    private val childCheckController: ChildCheckController
    private val childClickListener: OnCheckChildClickListener? = null
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
                val view = LayoutInflater.from(parent.context)
                        .inflate(R.layout.list_item_singlecheck_arist, parent, false)
                val holder = SingleCheckArtistViewHolder(view)
                holder.setOnChildCheckedListener(this)
                holder
            }
            else -> throw IllegalArgumentException("$viewType is an Invalid viewType")
        }
    }

    override fun onBindChildViewHolder(holder: ChildViewHolder, flatPosition: Int, group: ExpandableGroup<*>,
                                       childIndex: Int) {
        val viewType = getItemViewType(flatPosition)
        val artist = group.items[childIndex] as Artist
        when (viewType) {
            ARTIST_VIEW_TYPE -> (holder as ArtistViewHolder).setArtistName(artist.name)
            FAVORITE_VIEW_TYPE -> {
                val listPosition = expandableList.getUnflattenedPosition(flatPosition)
                (holder as SingleCheckArtistViewHolder)
                        .onBindViewHolder(flatPosition, childCheckController.isChildChecked(listPosition))
                holder.setArtistName(artist.name)
            }
        }
    }

    override fun onBindGroupViewHolder(holder: GenreViewHolder, flatPosition: Int,
                                       group: ExpandableGroup<*>) {
        holder.setGenreTitle(group)
    }

    override fun onChildCheckChanged(view: View, checked: Boolean, flatPos: Int) {
        val listPos = expandableList.getUnflattenedPosition(flatPos)
        childCheckController.onChildCheckChanged(checked, listPos)
        childClickListener?.onCheckChildCLick(view, checked,
                expandableList.getExpandableGroup(listPos) as CheckedExpandableGroup, listPos.childPos)
    }

    override fun updateChildrenCheckState(firstChildFlattenedIndex: Int, numChildren: Int) {
        notifyItemRangeChanged(firstChildFlattenedIndex, numChildren)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        //outState.putParcelableArrayList(CHECKED_STATE_MAP, ArrayList<Any?>(expandableList.groups))
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        if (savedInstanceState == null || !savedInstanceState.containsKey(CHECKED_STATE_MAP)) {
            return
        }
        expandableList.groups = savedInstanceState.getParcelableArrayList(CHECKED_STATE_MAP)
        super.onRestoreInstanceState(savedInstanceState)
    }

    fun clearChoices() {
        childCheckController.clearCheckStates()

        //only update the child views that are visible (i.e. their group is expanded)
        for (i in groups.indices) {
            val group = groups[i]
            if (isGroupExpanded(group)) {
                notifyItemRangeChanged(expandableList.getFlattenedFirstChildIndex(i), group.itemCount)
            }
        }
    }

    override fun isChild(viewType: Int): Boolean {
        return viewType == FAVORITE_VIEW_TYPE || viewType == ARTIST_VIEW_TYPE
    }

    override fun getChildViewType(position: Int, group: ExpandableGroup<*>, childIndex: Int): Int {
        return if ((group.items[childIndex] as Artist).isFavorite) {
            FAVORITE_VIEW_TYPE
        } else {
            ARTIST_VIEW_TYPE
        }
    }

    companion object {
        private const val CHECKED_STATE_MAP = "child_check_controller_checked_state_map"
        const val FAVORITE_VIEW_TYPE = 3
        const val ARTIST_VIEW_TYPE = 4
    }

    init {
        childCheckController = ChildCheckController(expandableList, this)
    }
}