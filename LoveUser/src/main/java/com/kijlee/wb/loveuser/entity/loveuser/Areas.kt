package com.kijlee.wb.loveuser.entity.loveuser

import android.os.Parcel
import android.os.Parcelable

/**
 * 地址类
 */
class Areas() : Parcelable{

    var cityList: MutableList<Areas>? = ArrayList()
    var id: String? = null
    var name: String? = null
    var pinYin: String? = null

    constructor(parcel: Parcel) : this() {
        id = parcel.readString()
        name = parcel.readString()
        pinYin = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(name)
        parcel.writeString(pinYin)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Areas> {
        override fun createFromParcel(parcel: Parcel): Areas {
            return Areas(parcel)
        }

        override fun newArray(size: Int): Array<Areas?> {
            return arrayOfNulls(size)
        }
    }
}