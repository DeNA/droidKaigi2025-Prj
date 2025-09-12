package jp.co.dena.droidkaigi2025_prj

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import coil3.compose.AsyncImage
import dagger.hilt.android.AndroidEntryPoint
import jp.co.dena.droidkaigi2025_prj.RoomColor.Companion.toRoomColor
import jp.co.dena.droidkaigi2025_prj.data.Languages
import jp.co.dena.droidkaigi2025_prj.data.entity.Room
import jp.co.dena.droidkaigi2025_prj.data.entity.Session
import jp.co.dena.droidkaigi2025_prj.data.entity.Speaker
import jp.co.dena.droidkaigi2025_prj.ui.theme.DroidKaigi2025PrjTheme
import jp.co.dena.droidkaigi2025_prj.ui.timetable.screens.timetable.TimeTableScreen
import jp.co.dena.droidkaigi2025_prj.ui.timetable.screens.timetabledetail.TimetableDetailScreen
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalSerializationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            DroidKaigi2025PrjTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = Route.TimeTable
                ) {
                    composable<Route.TimeTable> {
                        TimeTableScreen(
                            onSessionClick = {
                                navController.navigate(Route.SessionDetail(it.id))
                            },
                        )
                    }
                    composable<Route.SessionDetail> {
                        val route = it.toRoute<Route.SessionDetail>()
                        TimetableDetailScreen(
                            sessionId = route.sessionId,
                            onBackPress = {
                                navController.popBackStack()
                            }
                        )
                    }
                }
            }
        }
    }

}

enum class RoomColor(
    val id: Int,
    val colorRes: Int,
) {
    Narwhal(
        id = 64803,
        colorRes = R.color.narwhal
    ),
    Meerkat(
        id = 64801,
        colorRes = R.color.meerkat
    ),
    Jellyfish(
        id = 64799,
        colorRes = R.color.jellyfish
    ),
    Koala(
        id = 64802,
        colorRes = R.color.koala
    ),
    Ladybug(
        id = 64800,
        colorRes = R.color.ladybug
    ),
    Unknown(
        id = -1,
        colorRes = R.color.unknown
    );

    companion object {
        fun Room.toRoomColor(): RoomColor {
            return entries.find { it.id == id } ?: Unknown
        }
    }
}

@Composable
fun TableItem(
    session: Session,
    room: Room,
    selectedLanguage: Languages,
    speakers: List<Speaker>,
    onClick: () -> Unit,
) {
    Card(
        modifier = Modifier
            .background(
                color = Color(0xFFDDDDDD),
                RoundedCornerShape(10)
            ),
        onClick = onClick
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 12.dp,
                    vertical = 6.dp
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                speakers.forEach { speaker ->
                    AsyncImage(
                        model = speaker.profilePicture,
                        contentDescription = null,
                        modifier = Modifier
                            .size(48.dp)
                            .clip(
                                shape = RoundedCornerShape(8.dp)
                            )
                    )
                }
            }
            Column(
                modifier = Modifier
                    .weight(1f)
                    .heightIn(min = 100.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                val formattedDate =
                    OffsetDateTime.parse(session.startsAt, DateTimeFormatter.ISO_OFFSET_DATE_TIME)
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Text(
                        "${formattedDate.format(DateTimeFormatter.ofPattern("HH:mm"))}"
                    )
                    // TODO room.name.en/jpを一つの拡張関数で扱えるようにする
                    Box(
                        modifier = Modifier
                            .background(
                                color = colorResource(room.toRoomColor().colorRes),
                                shape = RoundedCornerShape(8.dp)
                            )
                            .padding(8.dp)
                    ) {
                        Text(
                            text = if (selectedLanguage == Languages.JAPANESE) {
                                room.name.ja
                            } else {
                                room.name.en
                            },
                        )
                    }
                }
                Text(
                    text = if (selectedLanguage == Languages.JAPANESE) {
                        session.title.ja
                    } else {
                        session.title.en
                    },
                    style = TextStyle.Default.copy(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    ),
                )
                if (speakers.isNotEmpty()) {
                    Column {
                        speakers.forEach { speaker ->
                            Text(text = speaker.fullName)
                        }
                    }
                }
            }
        }
    }
}

sealed interface Route {
    @Serializable
    data object TimeTable : Route

    @Serializable
    data class SessionDetail(
        val sessionId: String,
    ) : Route
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    DroidKaigi2025PrjTheme {
        Greeting("Android")
    }
}