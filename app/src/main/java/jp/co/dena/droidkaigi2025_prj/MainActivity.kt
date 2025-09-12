package jp.co.dena.droidkaigi2025_prj

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import dagger.hilt.android.AndroidEntryPoint
import jp.co.dena.droidkaigi2025_prj.data.Languages
import jp.co.dena.droidkaigi2025_prj.data.entity.Session
import jp.co.dena.droidkaigi2025_prj.ui.theme.DroidKaigi2025PrjTheme
import jp.co.dena.droidkaigi2025_prj.ui.timetable.screens.timetable.TimeTableScreen
import jp.co.dena.droidkaigi2025_prj.ui.timetable.screens.timetabledetail.TimetableDetailScreen
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable

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
                        )
                    }
                }
            }
        }
    }

}

@Composable
fun TableItem(
    session: Session,
    selectedLanguage: Languages,
    onClick: () -> Unit,
) {
    Card(
        modifier = Modifier.run {
            dropShadow(
                shape = RoundedCornerShape(4.dp),
                shadow = androidx.compose.ui.graphics.shadow.Shadow(
                    radius = 8.dp,
                    color = Color.Black.copy(alpha = 0.1f),
                    offset = DpOffset(x = 4.dp, y = 4.dp)
                )
            )
        },
        onClick = onClick
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 100.dp)
                .background(color = Color(0xFFF6D77E), RoundedCornerShape(10))
                .padding(
                    horizontal = 12.dp,
                    vertical = 6.dp
                ),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                session.startsAt
            )
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
            if (session.speakers.isNotEmpty()) {
                Text(
                    // name?
                    text = session.speakers[0]
                )
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