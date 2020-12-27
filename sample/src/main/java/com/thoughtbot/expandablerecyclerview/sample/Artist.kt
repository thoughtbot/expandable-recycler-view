package com.thoughtbot.expandablerecyclerview.sample

import android.os.Parcel
import android.os.Parcelable

class Artist : Parcelable {
    var name: String?
        private set
    var isFavorite = false
        private set

    constructor(name: String?, isFavorite: Boolean) {
        this.name = name
        this.isFavorite = isFavorite
    }

    protected constructor(`in`: Parcel) {
        name = `in`.readString()
    }

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o !is Artist) return false
        val artist = o
        if (isFavorite != artist.isFavorite) return false
        return if (name != null) name == artist.name else artist.name == null
    }

    override fun hashCode(): Int {
        var result = if (name != null) name.hashCode() else 0
        result = 31 * result + if (isFavorite) 1 else 0
        return result
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(name)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object {
        val CREATOR: Parcelable.Creator<Artist?> = object : Parcelable.Creator<Artist?> {
            override fun createFromParcel(`in`: Parcel): Artist? {
                return Artist(`in`)
            }

            override fun newArray(size: Int): Array<Artist?> {
                return arrayOfNulls(size)
            }
        }
    }
}