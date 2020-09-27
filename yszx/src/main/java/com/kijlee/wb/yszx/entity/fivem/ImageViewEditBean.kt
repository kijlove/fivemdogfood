package com.kijlee.wb.yszx.entity.fivem

import android.os.Parcel
import android.os.Parcelable

/**
 * 本机上传图片编辑类
 */
class ImageViewEditBean() :Parcelable{
    var imagePath: String? = ""//本地图片地址
    var allImageSize:Int? = 0//最多显示多少图片编辑的数量
    var isLocal:Boolean = true//本地
    var isAddIcon:Boolean = false//是否是添加按钮
    var isShowDelIcon = false//是否显示删除按钮

    constructor(parcel: Parcel) : this() {
        imagePath = parcel.readString()
        allImageSize = parcel.readValue(Int::class.java.classLoader) as? Int
        isLocal = parcel.readByte() != 0.toByte()
        isAddIcon = parcel.readByte() != 0.toByte()
        isShowDelIcon = parcel.readByte() != 0.toByte()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(imagePath)
        parcel.writeValue(allImageSize)
        parcel.writeByte(if (isLocal) 1 else 0)
        parcel.writeByte(if (isAddIcon) 1 else 0)
        parcel.writeByte(if (isShowDelIcon) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ImageViewEditBean> {
        override fun createFromParcel(parcel: Parcel): ImageViewEditBean {
            return ImageViewEditBean(parcel)
        }

        override fun newArray(size: Int): Array<ImageViewEditBean?> {
            return arrayOfNulls(size)
        }
    }


}