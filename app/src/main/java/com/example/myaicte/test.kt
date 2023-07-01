package com.example.myaicte

import android.os.Parcel
import android.os.Parcelable

data class Test(
    val instituteName : String?=null,
    val instituteState : String?=null,
    val instituteType : String?=null,
    val instituteCity : String?=null,
    val instituteTime : String?=null,
    val instituteShift : String?=null,
    val instituteID : String?=null,
    val instituteCourse : String?=null,
    val instituteAddress : String?=null,
    val instituteFaculty : String?=null

): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(instituteName)
        parcel.writeString(instituteState)
        parcel.writeString(instituteType)
        parcel.writeString(instituteCity)
        parcel.writeString(instituteTime)
        parcel.writeString(instituteShift)
        parcel.writeString(instituteID)
        parcel.writeString(instituteCourse)
        parcel.writeString(instituteAddress)
        parcel.writeString(instituteFaculty)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Test> {
        override fun createFromParcel(parcel: Parcel): Test {
            return Test(parcel)
        }

        override fun newArray(size: Int): Array<Test?> {
            return arrayOfNulls(size)
        }
    }
}