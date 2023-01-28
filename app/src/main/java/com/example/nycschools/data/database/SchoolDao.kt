package com.example.nycschools.data.database

import androidx.room.*
import com.example.nycschools.model.School
import kotlinx.coroutines.flow.Flow

@Dao
interface SchoolDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(school: List<School>)

    @Query("DELETE FROM school_table")
    suspend fun deleteAll()

    @Transaction
    @Query("SELECT * FROM school_table ORDER BY school_name ASC")
    fun getAllSchools(): Flow<List<School>>

    @Transaction
    @Query("SELECT * FROM school_table where school_name like :searchString ORDER BY school_name ASC")
    fun getSchoolsFiltered(searchString: String?): Flow<List<School>>
}