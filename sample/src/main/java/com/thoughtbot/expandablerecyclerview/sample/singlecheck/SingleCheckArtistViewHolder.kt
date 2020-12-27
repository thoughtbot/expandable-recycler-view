package com.thoughtbot.expandablerecyclerview.sample.singlecheck

import android.view.View
import android.widget.Checkable
import android.widget.CheckedTextView
import com.thoughtbot.expandablecheckrecyclerview.viewholders.CheckableChildViewHolder
import com.thoughtbot.expandablerecyclerview.sample.R

class SingleCheckArtistViewHolder(itemView: View) : CheckableChildViewHolder(itemView) {
    private val childCheckedTextView: CheckedTextView
    override fun getCheckable(): Checkable {
        return childCheckedTextView
    }

    fun setArtistName(artistName: String?) {
        childCheckedTextView.text = artistName
    }

    init {
        childCheckedTextView = itemView.findViewById<View>(R.id.list_item_singlecheck_artist_name) as CheckedTextView
    }
}