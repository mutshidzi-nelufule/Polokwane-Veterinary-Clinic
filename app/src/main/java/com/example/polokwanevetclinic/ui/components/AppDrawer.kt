package com.example.polokwanevetclinic.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.polokwanevetclinic.navigation.NavRoute
import com.example.polokwanevetclinic.navigation.drawerNavItems

@Composable
fun AppDrawerContent(
    currentRoute: String?,
    onNavigate: (NavRoute) -> Unit
) {
    ModalDrawerSheet {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "Polokwane Vet Clinic",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = "Management System",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        HorizontalDivider()

        Spacer(modifier = Modifier.height(8.dp))

        drawerNavItems.forEach { item ->
            val selected = currentRoute == item.route
            NavigationDrawerItem(
                label = { Text(text = item.title) },
                selected = selected,
                onClick = { onNavigate(item) },
                icon = {
                    item.icon?.let { iconVector ->
                        Icon(imageVector = iconVector, contentDescription = item.title)
                    }
                },
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 2.dp)
            )
        }
    }
}
