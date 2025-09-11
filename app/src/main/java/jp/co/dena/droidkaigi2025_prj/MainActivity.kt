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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import jp.co.dena.droidkaigi2025_prj.data.entity.Session
import jp.co.dena.droidkaigi2025_prj.data.entity.TimeTable
import jp.co.dena.droidkaigi2025_prj.ui.theme.DroidKaigi2025PrjTheme
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalSerializationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val timetable = assets.open("timetable.json").buffered()
        val decodedTimetable = Json.decodeFromStream<TimeTable>(timetable)


        setContent {
            DroidKaigi2025PrjTheme {
                Scaffold(
                    topBar = {
                        Box(
                            modifier = Modifier.padding(32.dp)
                        ) {
                            Text(
                                "Time Table",
                                fontSize = 40.sp,
                                fontWeight = FontWeight.Black,
                                color = Color.Blue
                            )
                        }
                    },
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    Box(
                        modifier = Modifier
                            .padding(innerPadding)
                            .padding(12.dp)
                    ) {

                        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            items(
                                decodedTimetable.sessions
                            ) {
                                TableItem(it)
                            }
                        }
                    }

                }
            }
        }
    }
}

@Composable
fun TableItem(session: Session) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Cyan, RoundedCornerShape(10)),
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
    }
}


@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

//@Preview(showBackground = true)
//@Composable
//fun GreetingPreview() {
//    DroidKaigi2025PrjTheme {
//        Greeting("Android")
//    }
//}