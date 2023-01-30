package com.example.nycschools.screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Refresh
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.nycschools.R
import com.example.nycschools.TopAppBarActionButton
import com.example.nycschools.model.School
import com.example.nycschools.util.Screens
import com.example.nycschools.viewModel.SchoolListViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SchoolListScreen(viewModel: SchoolListViewModel, navController: NavHostController) {

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(R.string.nyc_school_list)) },
                backgroundColor = MaterialTheme.colorScheme.primary,
                actions = {
                    TopAppBarActionButton(
                        imageVector = Icons.Outlined.Refresh,
                        description = stringResource(R.string.refresh)
                    ) {
                        viewModel.getSchoolList()
                    }
                },
                contentColor = Color.White
            )
        },
    ) { paddingValues ->
        Column {
            val schoolList by viewModel.schoolDirectory.collectAsState()

            SearchBar(
                onSearch = {
                    viewModel.onQueryChanged(it)
                },
               onClear = {
                  viewModel.onQueryChanged("")
             }
            )

            LazyColumn(modifier = Modifier.padding(paddingValues)) {
                schoolList.forEach { (initial, contactsForInitial) ->
                    stickyHeader {
                            Text(
                                text = initial.toString(),
                                color = Color.White,
                                modifier = Modifier
                                    .background(MaterialTheme.colorScheme.primary)
                                    .padding(5.dp)
                                    .fillMaxWidth()
                            )
                    }

                    items(contactsForInitial) { school ->
                        SchoolCard(school = school) {
                            navController.navigate(Screens.Detail.route)
                            viewModel.onSchoolSelectedEvent(school)
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun SearchBar(
    onSearch: (String) -> Unit = {},
    onClear: () -> Unit = {}
) {
    var text by rememberSaveable {
        mutableStateOf("")
    }

    OutlinedTextField(
        value = text,
        onValueChange = {
            text = it
            onSearch(it)
        },
        leadingIcon = {
            Icon(
                Icons.Default.Search,
                contentDescription = stringResource(R.string.search_school),
                modifier = Modifier
                    .padding(15.dp)
                    .size(24.dp)
            )
        },
        trailingIcon = {
            if (text.isNotEmpty()) {
                IconButton(
                    onClick = {
                        text = ""     // Remove text from TextField when you press the 'X' icon
                        onClear()
                    }
                ) {
                    Icon(
                        Icons.Default.Close,
                        contentDescription = stringResource(R.string.click_to_clear),
                        modifier = Modifier
                            .padding(15.dp)
                            .size(24.dp)
                    )
                }
            }
        },
        maxLines = 1,
        singleLine = true,
        textStyle = TextStyle(color = Color.Black, fontSize = 22.sp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 8.dp)
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SchoolCard(school: School, onclick: () -> Unit) {
    Card(modifier = Modifier
        .padding(8.dp)
        .fillMaxWidth(), elevation = 10.dp, onClick = { onclick() }) {
        Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
            Text(
                text = school.name,
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(0.9f),
                style = androidx.compose.material.MaterialTheme.typography.h6
            )
            Image(
                painter = painterResource(id = R.drawable.ic_right_arrow),
                modifier = Modifier.align(Alignment.CenterVertically),
                contentDescription = stringResource(R.string.click_to_see_more)
            )
        }
    }
}