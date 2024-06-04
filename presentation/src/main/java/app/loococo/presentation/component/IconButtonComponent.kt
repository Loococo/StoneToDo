package app.loococo.presentation.component

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import app.loococo.presentation.theme.Black

@Composable
fun DoItIconButton(
    size: Dp,
    icon: ImageVector,
    description: String,
    color: Color = Black,
    onClick: () -> Unit
) {
    IconButton(
        modifier = Modifier.size(size),
        onClick = onClick
    ) {
        Icon(icon, contentDescription = description, tint = color)
    }
}