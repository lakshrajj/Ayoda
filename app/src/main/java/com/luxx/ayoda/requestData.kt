package com.luxx.ayoda

import android.os.Parcel
import android.os.Parcelable

data class requestData(
    val des: String ?= null,
    val imageUrl: String ?= null,
    val landmark: String ?= null,
    val location: String ?= null,
    val status: String ?= null,
    val user: String ?= null
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(des)
        parcel.writeString(imageUrl)
        parcel.writeString(landmark)
        parcel.writeString(location)
        parcel.writeString(status)
        parcel.writeString(user)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<requestData> {
        override fun createFromParcel(parcel: Parcel): requestData {
            return requestData(parcel)
        }

        override fun newArray(size: Int): Array<requestData?> {
            return arrayOfNulls(size)
        }
    }
}
