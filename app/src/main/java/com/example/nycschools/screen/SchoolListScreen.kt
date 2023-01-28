package com.example.nycschools.screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Refresh
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
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
            LazyColumn(modifier = Modifier.padding(paddingValues)) {
                schoolList.forEach { (initial, contactsForInitial) ->
                    stickyHeader {
                        Text(
                            text = initial.toString(),
                            color = Color.White,
                            modifier = Modifier
                                .background(Color.Gray)
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