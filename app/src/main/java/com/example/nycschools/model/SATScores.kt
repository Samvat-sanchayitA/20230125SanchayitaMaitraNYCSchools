package com.example.nycschools.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Entity(tableName = "sat_table")
@Parcelize
data class SATScores(
    @PrimaryKey
    @ColumnInfo(name = "dbn")
    val dbn: String,

    @ColumnInfo(name = "school_name")
    @SerializedName("school_name")
    val name: String,

    @ColumnInfo(name = "sat_critical_reading_avg_score")
    @SerializedName("sat_critical_reading_avg_score")
    val readingSATScores: String,

    @ColumnInfo(name = "sat_math_avg_score")
    @SerializedName("sat_math_avg_score")
    val mathSATScores: String,

    @ColumnInfo(name = "sat_writing_avg_score")
    @SerializedName("sat_writing_avg_score")
    val writingSATScores: String,
):Parcelable
