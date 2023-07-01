package com.example.myaicte

import android.os.Parcel
import android.os.Parcelable

data class CourseRVModal(val institutename : String, val instituteid: String, val instituteaddress: String, val institutecity: String, val institutestate: String, val institutetype: String, val institutestaff: String, val institutetime: String, val institutecategory: String, val instituteshift: String) : Parcelable {
    val intaketext: CharSequence? = " hello"
    val coursetext: CharSequence? = " hello"
    val streamtext: CharSequence? = " hello"

    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(institutename)
        parcel.writeString(instituteid)
        parcel.writeString(instituteaddress)
        parcel.writeString(institutecity)
        parcel.writeString(institutestate)
        parcel.writeString(institutetype)
        parcel.writeString(institutestaff)
        parcel.writeString(institutetime)
        parcel.writeString(institutecategory)
        parcel.writeString(instituteshift)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CourseRVModal> {
        override fun createFromParcel(parcel: Parcel): CourseRVModal {
            return CourseRVModal(parcel)
        }

        override fun newArray(size: Int): Array<CourseRVModal?> {
            return arrayOfNulls(size)
        }
    }


}