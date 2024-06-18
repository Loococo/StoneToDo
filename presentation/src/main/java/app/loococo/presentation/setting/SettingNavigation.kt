package app.loococo.presentation.setting

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import app.loococo.presentation.component.animatedComposable


const val settingRoute = "setting_route"

fun NavGraphBuilder.settingScreen() {
    animatedComposable(route = settingRoute) {
        SettingRoute()
    }
}

fun NavController.navigateToSetting(navOptions: NavOptions) {
    this.navigate(settingRoute, navOptions)
}