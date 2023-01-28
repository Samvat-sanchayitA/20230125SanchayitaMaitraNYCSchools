package com.example.nycschools

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.nycschools.data.database.SchoolDao
import com.example.nycschools.data.database.SchoolDatabase
import com.example.nycschools.model.School
import junit.framework.TestCase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    private  lateinit var  schoolDao: SchoolDao
    private lateinit var db: SchoolDatabase

    @Before
    fun create() {
        val context: Context = ApplicationProvider.getApplicationContext()
        // Using an in-memory database because the information stored here disappears when the
        // process is killed.
        db = Room.inMemoryDatabaseBuilder(context, SchoolDatabase::class.java)
            // Allowing main thread queries, just for testing.
            .allowMainThreadQueries()
            .build()
        schoolDao = db.schoolDao()
    }

    @Test
    @Throws(Exception::class)
    fun insertAndGetWord() = runBlocking {
        val school1 = School("id_school_1","nyc school","overview", "123","abc@xyz.com","www.nyc.com","123 ny city", "nyc", "456")
        // val school2 = School("id_school_2")
        val schools: MutableList<School> = ArrayList()
        schools.add(school1)
        //  schools.add(school2)
        schoolDao.insertAll(schools)
        val allWords = schoolDao.getAllSchools().first()
        TestCase.assertEquals(allWords[0].name, school1.name)
    }

    @After
    fun cleanup() {
        db.close()
    }
}