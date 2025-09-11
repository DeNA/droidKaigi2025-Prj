package jp.co.dena.droidkaigi2025_prj.ui.timetable

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController

@Composable
fun TimetableDetailScreen (
    viewModel: TimetableViewModel = hiltViewModel(),
    navController: NavController
){
    Text("Todo")
}