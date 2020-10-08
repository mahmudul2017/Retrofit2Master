package com.example.retrofitnetworkcallinkotlin.postData

import android.os.Parcel
import android.os.Parcelable

data class PostEmployeeData(
    val designation: String?,
    val employeeId: Int,
    val enrollNumber: String?,
    val fullName: String?,
    val ssn: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(designation)
        parcel.writeInt(employeeId)
        parcel.writeString(enrollNumber)
        parcel.writeString(fullName)
        parcel.writeString(ssn)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<PostEmployeeData> {
        override fun createFromParcel(parcel: Parcel): PostEmployeeData {
            return PostEmployeeData(parcel)
        }

        override fun newArray(size: Int): Array<PostEmployeeData?> {
            return arrayOfNulls(size)
        }
    }
}