package com.example.polokwanevetclinic.ui.components

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.polokwanevetclinic.navigation.NavRoute
import com.example.polokwanevetclinic.navigation.bottomNavItems

@Composable
fun AppBottomBar(
    currentRoute: String?,
    onNavigate: (NavRoute) -> Unit
) {
    NavigationBar {
        bottomNavItems.forEach { item ->
            val selected = currentRoute == item.route
            NavigationBarItem(
                selected = selected,
                onClick = { onNavigate(item) },
                icon = {
                    item.icon?.let { iconVector ->
                        Icon(imageVector = iconVector, contentDescription = item.title)
                    }
                },
                label = { Text(text = item.title) }
            )
        }
    }
}
