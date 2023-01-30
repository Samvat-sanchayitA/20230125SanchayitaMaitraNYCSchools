package com.example.nycschools.viewModel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nycschools.data.SchoolRepository
import com.example.nycschools.model.SATScores
import com.example.nycschools.model.School
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SchoolListViewModel @Inject constructor(
    private val repository: SchoolRepository
) : ViewModel() {

    private val _schoolDirectory = MutableStateFlow<Map<Char, List<School>>>(hashMapOf())
    val schoolDirectory: StateFlow<Map<Char, List<School>>> = _schoolDirectory

    private val _selectedSchool = MutableStateFlow<School?>(null)
    val selectedSchool: StateFlow<School?> = _selectedSchool

    private val _satScores = MutableStateFlow<SATScores?>(null)
    val satScores: StateFlow<SATScores?> = _satScores

    private val _satList = MutableStateFlow<List<SATScores>>(emptyList())

    private val query = mutableStateOf("")

    init {
        getSchoolList()
        getScoreList()

    }

    fun getSchoolList() = viewModelScope.launch {
        val schoolList = repository.getSchoolList()
        schoolList.collect { uiState ->
            uiState.data?.let { list ->
                val grouped = list.groupBy { it.name[0] }
                _schoolDirectory.emit(grouped)
            }
        }
    }

    private fun getScoreList() = viewModelScope.launch {
        val scoreList = repository.getSATScore()
        scoreList.collect { uiState ->
            uiState.data?.let { list ->
                _satList.emit(list)
            }
        }
    }

    fun onSchoolSelectedEvent(school: School) = viewModelScope.launch {
        _selectedSchool.emit(school)
    }

    fun getSATScores() = viewModelScope.launch {
        val schoolDbn = _selectedSchool.value?.dbn
        schoolDbn?.let { dbn ->
            val scores = repository.getSATScoresForSchool(dbn)
            scores.collect { score ->
                _satScores.emit(score)
            }
        }
    }

    private fun getFilteredSchools(search: String) = viewModelScope.launch {
        val newText = "%${search.lowercase()}%"
        val filteredSchool = repository.getFilteredSchools(newText)
        filteredSchool.collect { list ->
            val grouped = list.groupBy { it.name[0] }
            _schoolDirectory.emit(grouped)
        }
    }

    fun onQueryChanged(query: String) {
        this.query.value = query
        getFilteredSchools(query)
    }
}