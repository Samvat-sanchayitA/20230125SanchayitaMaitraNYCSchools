package com.example.nycschools

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.nycschools.data.database.SATScoresDao
import com.example.nycschools.data.database.SchoolDatabase
import com.example.nycschools.model.SATScores
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SATScoresDaoTest {

    private lateinit var scoreDao: SATScoresDao
    private lateinit var db: SchoolDatabase
    private val score1 = SATScores("id_school_1", "nyc", "345", "567", "345")
    private val scoreList: MutableList<SATScores> = ArrayList()

    @Before
    fun create() {
        val context: Context = ApplicationProvider.getApplicationContext()
        // Using an in-memory database because the information stored here disappears when the
        // process is killed.
        db = Room.inMemoryDatabaseBuilder(context, SchoolDatabase::class.java)
            // Allowing main thread queries, just for testing.
            .allowMainThreadQueries()
            .build()
        scoreDao = db.satScoreDao()
    }

    @Test
    @Throws(Exception::class)
    fun insertAndGetSATScores() = runBlocking {
        scoreList.add(score1)
        scoreDao.insertAll(scoreList)

        val scoreListFromDb = scoreDao.getAllScores().first()
        assertEquals(scoreListFromDb[0].name, score1.name)

        scoreDao.deleteAll()
        val allScores = scoreDao.getAllScores().first()
        assertTrue(allScores.isEmpty())
    }

    @Test
    @Throws(Exception::class)
    fun getSATScoresForSchool() = runBlocking {
        scoreList.add(score1)
        scoreDao.insertAll(scoreList)

        val satScores = scoreDao.getScore("id_school_1").first()
        assertEquals(satScores.name, score1.name)
    }

    @After
    fun cleanup() {
        db.close()
    }
}