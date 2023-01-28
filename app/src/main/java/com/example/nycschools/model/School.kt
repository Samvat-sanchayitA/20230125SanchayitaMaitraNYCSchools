package com.example.nycschools.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Entity(tableName = "school_table")
@Parcelize
data class School(

    @PrimaryKey
    @ColumnInfo(name = "dbn")
    val dbn: String,

    @ColumnInfo(name = "school_name")
    @SerializedName("school_name")
    val name: String,

    @ColumnInfo(name = "overview_paragraph")
    @SerializedName("overview_paragraph")
    val overview: String,

    @ColumnInfo(name = "phone_number")
    @SerializedName("phone_number")
    val phone: String,

    @ColumnInfo(name = "school_email")
    @SerializedName("school_email")
    val email: String,

    @ColumnInfo(name = "website")
    val website: String,

    @ColumnInfo(name = "primary_address_line_1")
    @SerializedName("primary_address_line_1")
    val address: String,

    @ColumnInfo(name = "city")
    val city: String,

    @ColumnInfo(name = "zip")
    val zip: String
): Parcelable
