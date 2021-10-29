package com.example.mynotes.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class NoteDataKotlin(
    var id: String? = null,
    var title: String? = null,
    var description: String? = null,
    var isExecuted: Boolean = false,
    var date: Date? = null
) : Parcelable {

    constructor(title: String?, description: String?, executed: Boolean, date: Date?) : this() {
        this.title = title
        this.description = description
        isExecuted = executed
        this.date = date
    }
}