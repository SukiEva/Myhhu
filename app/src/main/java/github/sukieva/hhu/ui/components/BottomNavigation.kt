package github.sukieva.hhu.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material.icons.outlined.Assessment
import androidx.compose.material.icons.outlined.TrendingUp
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController


@Composable
fun BottomNav() {
    val navController = rememberNavController()
    NavHost(navController, startDestination = "grades") {
        composable("grades") { GradesScreen() }
        composable("rank") { RankScreen() }
    }
    var selectedItem by remember { mutableStateOf(0) }
    val items = listOf("成绩", "排名")
    BottomNavigation(
        modifier = Modifier.padding(16.dp),
        backgroundColor = MaterialTheme.colors.background,
        elevation = 3.dp
    ) {
        items.forEachIndexed { index, item ->
            BottomNavigationItem(
                icon = {
                    if (index == 1)
                        Icon(Icons.Outlined.TrendingUp, contentDescription = null)
                    else
                        Icon(Icons.Outlined.Assessment, contentDescription = null)
                },
                label = { Text(item) },
                selected = selectedItem == index,
                onClick = {
                    selectedItem = index
                    if (index == 1)
                        navController.navigate("rank")
                    else
                        navController.navigate("grades")
                }
            )
        }
    }
}

@Preview
@Composable
fun BottomNavPreview() {
    //BottomNav()
}