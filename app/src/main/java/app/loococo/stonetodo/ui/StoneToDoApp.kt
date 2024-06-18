package app.loococo.stonetodo.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import app.loococo.presentation.component.StoneToDoLabelText
import app.loococo.presentation.component.StoneToDoNavigationBar
import app.loococo.presentation.component.StoneToDoNavigationBarItem
import app.loococo.presentation.theme.StoneToDoTheme
import app.loococo.presentation.theme.White
import app.loococo.stonetodo.navigation.StoneToDoNavHost

@Composable
fun StoneToDoApp(
    appState: StoneToDoAppState = rememberStoneToDoAppState()
) {
    StoneToDoTheme {
        Scaffold(
            bottomBar = {
                BottomAppBar(
                    modifier = Modifier,
                    containerColor = White
                ) {
                    StoneToDoNavigationBar {
                        appState.topLevelDestinations.forEach { destination ->
                            StoneToDoNavigationBarItem(
                                selected = destination == appState.currentTopLevelDestination,
                                onClick = { appState.navigateToTopLevelDestination(destination) },
                                icon = {
                                    Icon(
                                        imageVector = destination.unselectedIcon,
                                        contentDescription = null,
                                    )
                                },
                                selectedIcon = {
                                    Icon(
                                        imageVector = destination.selectedIcon,
                                        contentDescription = null,
                                    )
                                },
                                label = {
                                    StoneToDoLabelText(text = destination.titleText)
                                }
                            )
                        }
                    }
                }
            },
            modifier = Modifier.fillMaxSize(),
            containerColor = White
        ) { padding ->
            Column(
                Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                StoneToDoNavHost(appState)
            }
        }
    }
}