package com.thoughtbot.expandablerecyclerview.sample

import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup

class Genre(title: String?, items: List<Artist?>?, val iconResId: Int) : ExpandableGroup<Artist?>(title, items) {
    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o !is Genre) return false
        return iconResId == o.iconResId
    }

    override fun hashCode(): Int {
        return iconResId
    }
}