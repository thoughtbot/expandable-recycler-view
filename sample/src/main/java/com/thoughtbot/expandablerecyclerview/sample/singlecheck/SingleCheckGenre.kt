package com.thoughtbot.expandablerecyclerview.sample.singlecheck

import android.os.Parcel
import android.os.Parcelable
import com.thoughtbot.expandablecheckrecyclerview.models.SingleCheckExpandableGroup

class SingleCheckGenre : SingleCheckExpandableGroup {
    var iconResId: Int
        private set

    constructor(title: String?, items: List<*>?, iconResId: Int) : super(title, items) {
        this.iconResId = iconResId
    }

    protected constructor(`in`: Parcel) : super(`in`) {
        iconResId = `in`.readInt()
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        super.writeToParcel(dest, flags)
        dest.writeInt(iconResId)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object {
        val CREATOR: Parcelable.Creator<SingleCheckGenre?> = object : Parcelable.Creator<SingleCheckGenre?> {
            override fun createFromParcel(`in`: Parcel): SingleCheckGenre? {
                return SingleCheckGenre(`in`)
            }

            override fun newArray(size: Int): Array<SingleCheckGenre?> {
                return arrayOfNulls(size)
            }
        }
    }
}