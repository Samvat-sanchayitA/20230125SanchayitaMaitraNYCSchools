package com.example.nycschools.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.nycschools.model.SATScores
import com.example.nycschools.model.School


@Database(entities = [School::class, SATScores::class], version = 1)
abstract class SchoolDatabase : RoomDatabase(){
    abstract fun schoolDao(): SchoolDao
    abstract fun satScoreDao(): SATScoresDao
}