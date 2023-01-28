package com.example.nycschools.data.remote

import com.example.nycschools.model.SATScores
import com.example.nycschools.model.School
import retrofit2.http.GET

interface SchoolApiService {
    companion object {
        const val NYC_SCHOOLS_URL = "https://data.cityofnewyork.us/resource/"
    }
    @GET("s3k6-pzi2.json")
    suspend fun getAllSchools() : List<School>

    @GET("f9bf-2cp4.json")
    suspend fun getAllSATScores() : List<SATScores>
}