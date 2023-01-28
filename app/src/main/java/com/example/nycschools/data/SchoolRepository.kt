package com.example.nycschools.data

import androidx.room.withTransaction
import com.example.nycschools.data.database.SchoolDatabase
import com.example.nycschools.data.remote.SchoolApiService
import com.example.nycschools.model.SATScores
import com.example.nycschools.util.NetworkHelper
import com.example.nycschools.util.networkBoundResource
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class SchoolRepository @Inject constructor(
    private val api: SchoolApiService,
    private val db: SchoolDatabase,
    private val networkHelper: NetworkHelper
) {

    private val schoolDao = db.schoolDao()
    private val satScoresDao = db.satScoreDao()

    fun getSchoolList() = networkBoundResource(
        query = {
            schoolDao.getAllSchools()
        },
        fetch = {
            delay(2000)
            api.getAllSchools()
        },
        saveFetchResult = { schoolList ->
            db.withTransaction {
                schoolDao.deleteAll()
                schoolDao.insertAll(schoolList)
            }
        },
        shouldFetch = {
            networkHelper.isNetworkAvailable()
        }
    )

    fun getSATScore() = networkBoundResource(
        query = {
            satScoresDao.getAllScores()
        },
        fetch = {
            delay(2000)
            api.getAllSATScores()
        },
        saveFetchResult = { scoreList ->
            db.withTransaction {
                satScoresDao.deleteAll()
                satScoresDao.insertAll(scoreList)
            }
        },
        shouldFetch = {
            networkHelper.isNetworkAvailable()
        }
    )

    fun getSATScoresForSchool(schoolDBN: String): Flow<SATScores> {
        return satScoresDao.getScore(schoolDBN)
    }
}