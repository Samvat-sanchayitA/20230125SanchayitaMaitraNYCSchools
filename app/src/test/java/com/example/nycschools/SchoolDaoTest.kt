package com.example.nycschools

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.nycschools.data.database.SchoolDao
import com.example.nycschools.data.database.SchoolDatabase
import com.example.nycschools.model.School
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
//import org.robolectric.RobolectricTestRunner

@RunWith(AndroidJUnit4::class)
//@RunWith(RobolectricTestRunner::class)
class SchoolDaoTest {

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
    fun insertAndGetSchools() = runBlocking {
        val school1 = School("id_school_1","nyc school","overview", "123","abc@xyz.com","www.nyc.com","123 ny city", "nyc", "456")
        val schools: MutableList<School> = ArrayList()
        schools.add(school1)

        schoolDao.insertAll(schools)
        val schoolList = schoolDao.getAllSchools().first()
        assertEquals(schoolList[0].name, school1.name)

        schoolDao.deleteAll()
        val allSchools = schoolDao.getAllSchools().first()
        assertTrue(allSchools.isEmpty())
    }

    @After
    fun cleanup() {
        db.close()
    }
}