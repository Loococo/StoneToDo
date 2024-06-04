package app.loococo.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import app.loococo.domain.model.CalendarNavigation
import app.loococo.domain.model.CalendarType
import app.loococo.domain.model.Todo
import app.loococo.presentation.component.DoItBodyText
import app.loococo.presentation.component.DoItIconButton
import app.loococo.presentation.component.DoItLabelText
import app.loococo.presentation.theme.Black
import app.loococo.presentation.theme.Gray1
import app.loococo.presentation.theme.Gray4
import app.loococo.presentation.theme.White
import app.loococo.presentation.utils.DoItIcons
import java.text.DateFormatSymbols
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.temporal.TemporalAdjusters

@Composable
fun CalendarScreen(
    currentDay: LocalDate,
    calendarType: CalendarType,
    selectedDate: LocalDate,
    todoListMap: Map<LocalDate, List<Todo>>,
    onCalendarNavigation: (CalendarNavigation) -> Unit,
    onCalendarTypeChange: () -> Unit,
    onDateSelected: (LocalDate) -> Unit,
    onDateRange: (LocalDate, LocalDate) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp)
    ) {
        CalendarHeader(
            currentDay,
            calendarType,
            onCalendarNavigation,
            onCalendarTypeChange
        )
        Spacer(modifier = Modifier.height(10.dp))
        DayScreen(
            currentDay,
            calendarType,
            selectedDate,
            todoListMap,
            onDateSelected,
            onDateRange
        )
    }
}

@Composable
private fun CalendarHeader(
    currentDay: LocalDate,
    calendarType: CalendarType,
    onCalendarNavigation: (CalendarNavigation) -> Unit,
    onCalendarTypeChange: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            DoItIconButton(
                size = 30.dp,
                icon = DoItIcons.ArrowLeft,
                description = "Previous period",
                onClick = { onCalendarNavigation(CalendarNavigation.NavigateToPreviousPeriod) }
            )
            DoItBodyText(
                text = "${currentDay.year}-${currentDay.monthValue.toString().padStart(2, '0')}",
                fontWeight = FontWeight.Bold
            )
            DoItIconButton(
                size = 30.dp,
                icon = DoItIcons.ArrowRight,
                description = "Next period",
                onClick = { onCalendarNavigation(CalendarNavigation.NavigateToPreviousPeriod) }
            )
        }
        Box(
            modifier = Modifier
                .clickable { onCalendarTypeChange() }
                .background(Gray1, RoundedCornerShape(10.dp))
                .padding(horizontal = 10.dp, vertical = 2.dp)
                .wrapContentWidth(),
            contentAlignment = Alignment.Center
        ) {
            DoItLabelText(
                text = when (calendarType) {
                    CalendarType.DayOfMonth -> "월"
                    CalendarType.DayOfWeek -> "주"
                }
            )
        }
    }
}

@Composable
private fun DayScreen(
    currentDay: LocalDate,
    calendarType: CalendarType,
    selectedDate: LocalDate,
    todoListMap: Map<LocalDate, List<Todo>>,
    onDateSelected: (LocalDate) -> Unit,
    onDateRange: (LocalDate, LocalDate) -> Unit
) {
    val today = LocalDate.now()

    DaysOfWeekHeader()
    Spacer(modifier = Modifier.height(3.dp))

    when (calendarType) {
        CalendarType.DayOfWeek -> DaysOfWeek(
            currentDay,
            today,
            selectedDate,
            todoListMap,
            onDateSelected,
            onDateRange
        )

        CalendarType.DayOfMonth -> DaysOfMonth(
            currentDay,
            today,
            selectedDate,
            todoListMap,
            onDateSelected,
            onDateRange
        )
    }
}

@Composable
private fun DaysOfWeekHeader() {
    val daysOfWeek = DateFormatSymbols().shortWeekdays.drop(1)
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        daysOfWeek.forEach { day ->
            DoItLabelText(
                text = day,
                modifier = Modifier.weight(1f),
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
private fun DaysOfWeek(
    currentDay: LocalDate,
    today: LocalDate,
    selectedDate: LocalDate,
    todoListMap: Map<LocalDate, List<Todo>>,
    onDateSelected: (LocalDate) -> Unit,
    onDateRange: (LocalDate, LocalDate) -> Unit
) {
    val startOfWeek = currentDay.with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY))
    val weekDates = generateSequence(startOfWeek) { it.plusDays(1) }
        .takeWhile { it.dayOfWeek != DayOfWeek.SUNDAY || it == startOfWeek }
        .toList()

    onDateRange.invoke(weekDates.first(), weekDates.last())

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
                DaysOfMonthItem(day, today, selectedDate, todoListMap, onDateSelected)
            }
        }
    }
}

@Composable
private fun DaysOfMonth(
    currentDay: LocalDate,
    today: LocalDate,
    selectedDate: LocalDate,
    todoListMap: Map<LocalDate, List<Todo>>,
    onDateSelected: (LocalDate) -> Unit,
    onDateRange: (LocalDate, LocalDate) -> Unit
) {
    val firstDayOfMonth = currentDay.withDayOfMonth(1)
    val daysInMonth = currentDay.lengthOfMonth()
    val daysInPreviousMonth = firstDayOfMonth.dayOfWeek.value % 7
    val lastDayOfPreviousMonth = firstDayOfMonth.minusDays(1).dayOfMonth
    val totalDays = buildList {
        addAll((lastDayOfPreviousMonth - daysInPreviousMonth + 1..lastDayOfPreviousMonth).toList())
        addAll((1..daysInMonth).toList())
        addAll((1..(7 - (daysInPreviousMonth + daysInMonth) % 7)).toList())
    }

    onDateRange.invoke(firstDayOfMonth, currentDay.withDayOfMonth(daysInMonth))

    LazyVerticalGrid(
        columns = GridCells.Fixed(7),
        modifier = Modifier.fillMaxWidth()
    ) {
        items(totalDays.size) { index ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                val day = totalDays[index]
                if (index < daysInPreviousMonth || index >= daysInPreviousMonth + daysInMonth) {
                    Spacer(
                        modifier = Modifier
                            .height(20.dp)
                            .background(Color.Transparent)
                    )
                } else {
                    val date = LocalDate.of(currentDay.year, currentDay.monthValue, day)
                    DaysOfMonthItem(date, today, selectedDate, todoListMap, onDateSelected)
                }
            }
        }
    }
}

@Composable
private fun DaysOfMonthItem(
    date: LocalDate,
    today: LocalDate,
    selectedDate: LocalDate,
    todoListMap: Map<LocalDate, List<Todo>>,
    onDateSelected: (LocalDate) -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }

    val isToday = date == today
    val isSelected = date == selectedDate
    val hasTodos = todoListMap[date]?.isNotEmpty() == true

    Column(
        modifier = Modifier.clickable(
            interactionSource = interactionSource,
            indication = null,
            onClick = { onDateSelected.invoke(date) },
        ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(22.dp)
                .background(
                    color = if (isSelected) Gray4 else if (isToday) Gray1 else Color.Transparent,
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            DoItLabelText(
                text = "${date.dayOfMonth}",
                fontWeight = if (isToday || isSelected) FontWeight.Bold else FontWeight.Normal,
                color = if (isSelected) White else Black,
            )
        }
        Spacer(modifier = Modifier.height(5.dp))
        Box(
            modifier = Modifier
                .size(5.dp)
                .background(
                    if (hasTodos) Color.LightGray else Color.Transparent,
                    shape = CircleShape
                )
        )
        Spacer(modifier = Modifier.height(5.dp))
    }
}