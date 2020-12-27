package com.thoughtbot.expandablerecyclerview.sample.expand

import android.view.View
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import android.widget.ImageView
import android.widget.TextView
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup
import com.thoughtbot.expandablerecyclerview.sample.Genre
import com.thoughtbot.expandablerecyclerview.sample.R
import com.thoughtbot.expandablerecyclerview.sample.multicheck.MultiCheckGenre
import com.thoughtbot.expandablerecyclerview.sample.singlecheck.SingleCheckGenre
import com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder

class GenreViewHolder(itemView: View) : GroupViewHolder(itemView) {
    private val genreName: TextView
    private val arrow: ImageView
    private val icon: ImageView
    fun setGenreTitle(genre: ExpandableGroup<*>) {
        if (genre is Genre) {
            genreName.text = genre.getTitle()
            icon.setBackgroundResource(genre.iconResId)
        }
        if (genre is MultiCheckGenre) {
            genreName.text = genre.getTitle()
            icon.setBackgroundResource(genre.iconResId)
        }
        if (genre is SingleCheckGenre) {
            genreName.text = genre.getTitle()
            icon.setBackgroundResource(genre.iconResId)
        }
    }

    override fun expand() {
        animateExpand()
    }

    override fun collapse() {
        animateCollapse()
    }

    private fun animateExpand() {
        val rotate = RotateAnimation(360f, 180f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f)
        rotate.duration = 300
        rotate.fillAfter = true
        arrow.animation = rotate
    }

    private fun animateCollapse() {
        val rotate = RotateAnimation(180f, 360f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f)
        rotate.duration = 300
        rotate.fillAfter = true
        arrow.animation = rotate
    }

    init {
        genreName = itemView.findViewById<View>(R.id.list_item_genre_name) as TextView
        arrow = itemView.findViewById<View>(R.id.list_item_genre_arrow) as ImageView
        icon = itemView.findViewById<View>(R.id.list_item_genre_icon) as ImageView
    }
}