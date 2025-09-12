package jp.co.dena.droidkaigi2025_prj.ui.timetable.screens.timetabledetail

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import jp.co.dena.droidkaigi2025_prj.data.entity.Session
import jp.co.dena.droidkaigi2025_prj.ui.components.TimetableAppBar
import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

@Composable
fun TimetableDetailScreen(
    viewModel: TimetableDetailViewModel = hiltViewModel(),
    sessionId: String,
    onBackPress: () -> Unit,
) {
    val screenState by viewModel.screenState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.initSession(sessionId)
    }

    BackHandler {
        onBackPress()
    }

    Scaffold(
        topBar = {
            TimetableAppBar(
                title = if (screenState is TimetableDetailState.Success) {
                    (screenState as TimetableDetailState.Success).session.title.ja
                } else {
                    ""
                },
                onClickBackButton = onBackPress,
            )
        }
    ) { innerPadding ->


        when (val state = screenState) {
            is TimetableDetailState.Loading -> {

                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    CircularProgressIndicator()
                }
            }

            is TimetableDetailState.Success -> {
                val session = state.session
                val speakers = state.speakers
                val time by remember {
                    derivedStateOf {
                        val dateTime = OffsetDateTime.parse(session.startsAt)
                        "${dateTime.year}/${dateTime.month.value}/${dateTime.dayOfMonth} ${dateTime.hour}:${dateTime.minute}"
                    }
                }
                Column(
                    modifier = Modifier
                        .padding(innerPadding)
                        .padding(horizontal = 24.dp)
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {

                    Text(
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        text = session.title.ja
                    )

                    Column(horizontalAlignment = Alignment.Start, modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        //time
                        Text(text = time)

                        // speaker
                        speakers.forEach {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                AsyncImage(it.profilePicture, contentDescription = "Speaker ${it.firstName} Image", modifier = Modifier.size(48.dp).clip(
                                    RoundedCornerShape(4.dp)
                                ))
                                Spacer(modifier = Modifier.size(8.dp))
                                Text(text = it.fullName )
                            }
                        }
                        // Room
                        Text(text = state.room.room.name.ja, modifier = Modifier.background(
                            colorResource(state.room.roomColor.colorRes)
                        ).padding(4.dp).clip(RoundedCornerShape(4.dp)))
                    }


                    session.description?.let {
                        Text(
                            modifier = Modifier.padding(top = 8.dp),
                            text = it,fontSize = 20.sp,
                        )
                    }
                }

            }

            is TimetableDetailState.Failed -> {

                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {

                    Text(state.throwable.message ?: "Error!")
                }
            }
        }
    }
}