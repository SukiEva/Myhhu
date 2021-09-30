package github.sukieva.hhu.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import github.sukieva.hhu.ui.activity.base.InitView


@Composable
fun BottomNav() {
    var selectedItem by remember { mutableStateOf(0) }
    val items = listOf("Links", "Inquiry", "Schedule")
    BottomNavigation(
        modifier = Modifier.padding(16.dp)
    ) {
        items.forEachIndexed { index, item ->
            BottomNavigationItem(
                icon = { Icon(Icons.Filled.Favorite, contentDescription = null) },
                label = { Text(item) },
                selected = selectedItem == index,
                onClick = { selectedItem = index }
            )
        }
    }
}

@Preview(
    //showBackground = true,
    //showSystemUi = true,
    name = "BottomNav"
)
@Composable
fun BottomNavPreview() {
    InitView {
        BottomNav()
    }
}