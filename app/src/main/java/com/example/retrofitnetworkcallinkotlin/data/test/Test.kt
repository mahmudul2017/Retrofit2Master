package com.example.retrofitnetworkcallinkotlin.data.test

import android.os.Parcel
import android.os.Parcelable

data class Test(val firstName: String?, val lastName: String?) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(firstName)
        parcel.writeString(lastName)
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
