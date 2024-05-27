package app.loococo.presentation.home

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import app.loococo.domain.model.CalendarNavigation
import app.loococo.domain.model.CalendarType
import app.loococo.presentation.utils.DoItIcons
import java.text.DateFormatSymbols
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.temporal.TemporalAdjusters


@Composable
internal fun HomeRoute() {
    HomeScreen()
}

@Composable
fun HomeScreen() {
    val vm: HomeViewModel = hiltViewModel()
    val currentMonth: LocalDate by vm.currentMonth.collectAsStateWithLifecycle()
    val calendarType: CalendarType by vm.calendarType.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
    ) {
        CalendarScreen(
            currentMonth,
            calendarType,
            vm::onCalendarNavigation,
            vm::onCalendarTypeChange
        )
        Spacer(modifier = Modifier.height(5.dp))
        DayScreen(currentMonth, calendarType)
    }
}

@Composable
fun CalendarScreen(
    currentMonth: LocalDate,
    calendarType: CalendarType,
    onCalendarNavigation: (navigation: CalendarNavigation) -> Unit,
    onCalendarTypeChange: () -> Unit
) {
    Log.e("do_it_log", "$currentMonth")

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        CalendarHeaderDate(currentMonth)
        CalendarHeaderMonth(calendarType, onCalendarNavigation, onCalendarTypeChange)
    }
}

@Composable
fun DayScreen(
    currentMonth: LocalDate,
    calendarType: CalendarType
) {
    DaysOfWeekdays()
    Spacer(modifier = Modifier.height(3.dp))
    when (calendarType) {
        CalendarType.DayOfWeek -> DaysOfWeek(currentMonth)
        CalendarType.DayOfMonth -> DaysOfMonth(currentMonth)
    }
}

@Composable
fun CalendarHeaderDate(currentMonth: LocalDate) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = "${currentMonth.year}-${currentMonth.monthValue.toString().padStart(2, '0')}",
            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
        )
        Spacer(modifier = Modifier.width(7.dp))

        Box(
            modifier = Modifier
                .size(13.dp)
                .background(Color.LightGray, shape = RoundedCornerShape(4.dp)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = DoItIcons.Check,
                contentDescription = "check"
            )
        }

        Spacer(modifier = Modifier.width(3.dp))

        Text(
            text = "0",
            style = MaterialTheme.typography.titleSmall
        )
    }
}

@Composable
fun CalendarHeaderMonth(
    calendarType: CalendarType,
    onCalendarNavigation: (navigation: CalendarNavigation) -> Unit,
    onCalendarTypeChange: () -> Unit
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        IconButton(onClick = { onCalendarNavigation.invoke(CalendarNavigation.NavigateToPreviousPeriod) }) {
            Icon(DoItIcons.ArrowLeft, contentDescription = "left")
        }
        IconButton(onClick = { onCalendarNavigation.invoke(CalendarNavigation.NavigateToNextPeriod) }) {
            Icon(DoItIcons.ArrowRight, contentDescription = "right")
        }

        Box(
            modifier = Modifier
                .background(Color.LightGray, shape = RoundedCornerShape(10.dp))
                .padding(10.dp, 2.dp)
                .clickable { onCalendarTypeChange.invoke() },
            contentAlignment = Alignment.Center
        ) {
            val text = when(calendarType) {
                CalendarType.DayOfMonth -> "월"
                CalendarType.DayOfWeek -> "주"
            }
            Text(
                text = text,
                style = MaterialTheme.typography.titleSmall
            )
        }
    }
}

@Composable
fun DaysOfWeekdays() {
    val daysOfWeek = DateFormatSymbols().shortWeekdays
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        daysOfWeek.drop(1).forEach { day ->
            Text(
                text = day,
                modifier = Modifier.weight(1f),
                style = MaterialTheme.typography.titleSmall,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun DaysOfWeek(currentMonth: LocalDate) {
    val startOfWeek = currentMonth.with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY))
    val endOfWeek = currentMonth.with(TemporalAdjusters.nextOrSame(DayOfWeek.SATURDAY))
    val weekDates = mutableListOf<Int>()
    var date = startOfWeek

    while (date.isBefore(endOfWeek) || date == endOfWeek) {
        weekDates.add(date.dayOfMonth)
        date = date.plusDays(1)
    }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        weekDates.forEach { day ->
            Box(
                modifier = Modifier.weight(1f),
                contentAlignment = Alignment.Center
            ) {
                DaysOfMonthItem(day)
            }
        }
    }
}

@Composable
fun DaysOfMonth(currentMonth: LocalDate) {

    val firstDayOfWeek = currentMonth.withDayOfMonth(1).dayOfWeek.value % 7
    val daysInMonth = currentMonth.lengthOfMonth()
    val daysList = (1..daysInMonth).toList()

    val lastDayOfPreviousMonth =
        currentMonth.minusMonths(1).with(TemporalAdjusters.lastDayOfMonth()).dayOfMonth

    val daysInPreviousMonth =
        (lastDayOfPreviousMonth - firstDayOfWeek + 1)..lastDayOfPreviousMonth
    val daysListWithPreviousMonth = daysInPreviousMonth.toList()

    val lastDayOfNextMonth = 7 - (daysList.size + daysListWithPreviousMonth.size) % 7
    val daysInNextMonth = 1..lastDayOfNextMonth
    val daysListWithNextMonth = daysInNextMonth.toList()

    val totalDays = daysListWithPreviousMonth + daysList + daysListWithNextMonth

    LazyVerticalGrid(
        modifier = Modifier.fillMaxWidth(),
        columns = GridCells.Fixed(7)
    ) {
        items(totalDays.size) { index ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                val day = totalDays[index]
                if (index < daysListWithPreviousMonth.size || index >= daysListWithPreviousMonth.size + daysList.size) {
                    Box(
                        modifier = Modifier
                            .aspectRatio(1f)
                            .padding(4.dp)
                            .background(Color.Transparent)
                    )
                } else {
                    val date = LocalDate.of(currentMonth.year, currentMonth.monthValue, day)
                    DaysOfMonthItem(date.dayOfMonth)
                }
            }
        }
    }
}

@Composable
fun DaysOfMonthItem(day: Int) {
    Column {
        Box(
            modifier = Modifier
                .size(20.dp)
                .background(Color.LightGray, shape = RoundedCornerShape(5.dp)),
        )

        Spacer(modifier = Modifier.height(5.dp))

        Box(
            modifier = Modifier
                .size(20.dp)
//                .background(
//                    color = if (day == currentDay) Color.LightGray else Color.Transparent,
//                    shape = CircleShape
//                )
            ,
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "$day",
                modifier = Modifier,
//                style = MaterialTheme.typography.titleSmall.copy(fontWeight = if (day == currentDay) FontWeight.Bold else FontWeight.Normal),
                style = MaterialTheme.typography.titleSmall,
                textAlign = TextAlign.Center
            )
        }
        Spacer(modifier = Modifier.height(5.dp))
    }
}