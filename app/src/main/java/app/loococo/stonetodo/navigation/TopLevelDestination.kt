package app.loococo.stonetodo.navigation

import androidx.compose.ui.graphics.vector.ImageVector
import app.loococo.presentation.utils.StoneToDoIcons

enum class TopLevelDestination(
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val iconText: String,
    val titleText: String,
) {
    HOME(
        selectedIcon = StoneToDoIcons.Home,
        unselectedIcon = StoneToDoIcons.HomeBorder,
        iconText = "home",
        titleText = "Home"
    ),
    CALENDAR(
        selectedIcon = StoneToDoIcons.Calendar,
        unselectedIcon = StoneToDoIcons.CalendarBorder,
        iconText = "calendar",
        titleText = "Calendar"
    ),
    SETTING(
        selectedIcon = StoneToDoIcons.Setting,
        unselectedIcon = StoneToDoIcons.SettingBorder,
        iconText = "setting",
        titleText = "Setting"
    )
}
