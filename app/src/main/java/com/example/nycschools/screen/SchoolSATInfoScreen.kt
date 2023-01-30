package com.example.nycschools.screen

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.nycschools.R
import com.example.nycschools.model.SATScores
import com.example.nycschools.viewModel.SchoolListViewModel

@Composable
fun SchoolInfoScreen(viewModel: SchoolListViewModel, navController: NavHostController) {
    LaunchedEffect(key1 = true) {
        viewModel.getSATScores()
    }
    val selectedSchool by viewModel.selectedSchool.collectAsState()
    Scaffold(
        topBar = {
            TopAppBar(
                title = { selectedSchool?.let { Text(text = it.name) } },
                backgroundColor = MaterialTheme.colorScheme.primary,
                navigationIcon = {
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = stringResource(
                                R.string.go_back
                            )
                        )
                    }
                },
                contentColor = Color.White
            )
        },
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues = paddingValues)) {

            val satScores by viewModel.satScores.collectAsState()
            val context = LocalContext.current
            selectedSchool?.let { school ->
                satScores?.let { ScoreCard(title = school.name, score = it) }

                ExpandableCard(
                    title = stringResource(R.string.overview_title),
                    description = school.overview
                )

                InfoCard(
                    title = stringResource(R.string.address_title),
                    info = "${school.address}, ${school.city} ${school.zip}",
                    imageVector = Icons.Default.Home,
                    onclick = {}
                )
                InfoCard(
                    title = stringResource(R.string.phone_title),
                    info = school.phone,
                    imageVector = Icons.Filled.Phone,
                    onclick = { onClickPhone(school.phone, context) }
                )
                InfoCard(
                    title = stringResource(R.string.website_title),
                    info = school.website,
                    imageVector = Icons.Default.Info,
                    onclick = {
                        onClickWebUrl(school.website, context)
                    }
                )

                InfoCard(
                    title = stringResource(R.string.email_title),
                    info = school.email,
                    imageVector = Icons.Filled.Email,
                    onclick = {
                        onClickEmail(school.email, context)
                    }
                )
            }
        }
    }
}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun InfoCard(title: String, info: String, imageVector: ImageVector? = null, onclick: () -> Unit) {

    Card(modifier = Modifier
        .padding(8.dp)
        .fillMaxWidth(), elevation = 6.dp, onClick = { onclick() }) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Row {
                imageVector?.let {
                    Icon(
                        imageVector = imageVector,
                        contentDescription = null,
                        modifier = Modifier.padding(8.dp)
                    )
                }
                Text(
                    text = title,
                    modifier = Modifier
                        .padding(6.dp),

                    style = typography.subtitle1
                )
            }
            Text(
                text = info, style = typography.body1
            )
        }
    }
}

@Composable
fun ScoreCard(title: String, score: SATScores) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(), elevation = 6.dp
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = title,
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                style = typography.h6
            )
            Row(modifier = Modifier.padding(8.dp)) {
                Text(
                    text = stringResource(R.string.sat_reading_score_title),
                    modifier = Modifier.padding(end = 8.dp), style = typography.subtitle1
                )
                Text(text = score.readingSATScores, style = typography.body1)

            }
            Row(modifier = Modifier.padding(8.dp)) {
                Text(
                    text = stringResource(R.string.sat_math_score_title),
                    modifier = Modifier.padding(end = 8.dp), style = typography.subtitle1
                )
                Text(text = score.mathSATScores, style = typography.body1)

            }

            Row(modifier = Modifier.padding(8.dp)) {
                Text(
                    text = stringResource(R.string.sat_writing_score_title),
                    modifier = Modifier.padding(end = 8.dp),
                    style = typography . subtitle1
                )
                Text(text = score.writingSATScores, style = typography.body1)

            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ExpandableCard(title: String, description: String) {
    var expandedState by remember {
        mutableStateOf(false)
    }
    val rotationState by animateFloatAsState(
        targetValue = if (expandedState) 180f else 0f
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = 8.dp,
        onClick = {
            expandedState = !expandedState
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()

        ) {
            Row {
                Text(
                    modifier = Modifier
                        .weight(6f)
                        .padding(8.dp),
                    text = title,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = typography.subtitle1
                )
                IconButton(
                    modifier = Modifier
                        .alpha(ContentAlpha.medium)
                        .weight(1f)
                        .rotate(rotationState),
                    onClick = {
                        expandedState = !expandedState
                    },
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = stringResource(R.string.click_to_see_more)
                    )
                }
            }
            if (expandedState) {
                Spacer(modifier = Modifier.padding(8.dp))
                Text(
                    text = description,
                    style = typography.body1,
                    modifier = Modifier.padding(8.dp)
                )
            }
        }
    }
}


fun onClickWebUrl(website: String, context: Context) {
    val websiteIntentUri = Uri.parse(website)
    val websiteIntent = Intent(Intent.ACTION_VIEW, websiteIntentUri)
    context.startActivity(Intent.createChooser(websiteIntent, "Browse with"))

}

fun onClickPhone(phone: String, context: Context) {
    val intent = Intent(Intent.ACTION_DIAL)
    intent.data = Uri.parse("tel:$phone")
    context.startActivity(intent)

}

fun onClickEmail(email: String, context: Context) {
    val intent = Intent(Intent.ACTION_SEND)
    intent.data = Uri.parse("mailto:$email")
    context.startActivity(Intent.createChooser(intent, "Email School "))
}








