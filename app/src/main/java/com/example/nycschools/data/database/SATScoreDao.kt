package com.example.nycschools.data.database

import androidx.annotation.VisibleForTesting
import androidx.room.*
import com.example.nycschools.model.SATScores
import kotlinx.coroutines.flow.Flow


@Dao
interface SATScoresDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(scores: List<SATScores>)

    @Query("DELETE FROM sat_table")
    fun deleteAll()

    @Transaction
    @Query("SELECT * FROM sat_table where dbn = :schoolDBN")
    fun getScore(schoolDBN: String): Flow<SATScores>

    @Transaction
    @VisibleForTesting
    @Query("SELECT * FROM sat_table  ORDER BY school_name ASC")
    fun getAllScores(): Flow<List<SATScores>>
}