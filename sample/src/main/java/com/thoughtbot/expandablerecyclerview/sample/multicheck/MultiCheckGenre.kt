package com.thoughtbot.expandablerecyclerview.sample.multicheck

import com.thoughtbot.expandablecheckrecyclerview.models.MultiCheckExpandableGroup
import com.thoughtbot.expandablerecyclerview.sample.Artist

class MultiCheckGenre(title: String?, items: List<Artist?>?, val iconResId: Int) : MultiCheckExpandableGroup(title, items)