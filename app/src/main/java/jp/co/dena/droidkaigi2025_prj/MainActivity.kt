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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHost
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import jp.co.dena.droidkaigi2025_prj.data.entity.Session
import jp.co.dena.droidkaigi2025_prj.ui.theme.DroidKaigi2025PrjTheme
import jp.co.dena.droidkaigi2025_prj.ui.timetable.TimeTableScreen
import kotlinx.serialization.ExperimentalSerializationApi

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
                    startDestination = "time_table"
                ) {
                    composable("time_table") {
                        TimeTableScreen(
                            navController = navController
                        )
                    }
                    composable("session_detail") {
                        Text("TODO")
                    }
                }
            }
        }
    }

}

@Composable
fun TableItem(
    session: Session,
    onClick: () -> Unit,
) {
    Card(
        onClick = onClick
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 100.dp)
                .background(Color.Cyan, RoundedCornerShape(10))
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
                session.title.ja,
                style = TextStyle.Default.copy(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                ),
            )
            if(session.speakers.isNotEmpty()) {
                Text(
                    // name?
                    text = session.speakers[0]
                )
            }
        }
    }
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