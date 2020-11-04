package com.pram.book.data.model

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "books")
data class BookModel(
    @PrimaryKey
    @NonNull
    @SerializedName("id")
    var bookId: String,
    var title: String,
    var author: String,
    var pages: String,
) : Parcelable {

    constructor(
        title: String,
        author: String,
        pages: String
    ) : this("", title, author, pages)
}