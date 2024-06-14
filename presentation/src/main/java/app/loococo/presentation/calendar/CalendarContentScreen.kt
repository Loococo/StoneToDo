package app.loococo.presentation.calendar

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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import app.loococo.domain.model.CalendarNavigation
import app.loococo.domain.model.CalendarType
import app.loococo.domain.model.Todo
import app.loococo.presentation.component.StoneToDoBodyText
import app.loococo.presentation.component.StoneToDoIconButton
import app.loococo.presentation.component.StoneToDoLabelText
import app.loococo.presentation.theme.Black
import app.loococo.presentation.theme.Gray1
import app.loococo.presentation.theme.Gray2
import app.loococo.presentation.theme.Gray4
import app.loococo.presentation.theme.White
import app.loococo.presentation.utils.StoneToDoIcons
import java.text.DateFormatSymbols
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.temporal.TemporalAdjusters

@Composable
fun CalendarContentScreen(
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
            selectedDate,
            calendarType,
            onCalendarNavigation,
            onCalendarTypeChange
        )
        Spacer(modifier = Modifier.height(10.dp))
        DayScreen(
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
    selectedDate: LocalDate,
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
            StoneToDoIconButton(
                size = 30.dp,
                icon = StoneToDoIcons.ArrowLeft,
                description = "Previous period",
                onClick = { onCalendarNavigation(CalendarNavigation.NavigateToPreviousPeriod) }
            )
            StoneToDoBodyText(
                text = "${selectedDate.year}-${
                    selectedDate.monthValue.toString().padStart(2, '0')
                }",
                fontWeight = FontWeight.Bold
            )
            StoneToDoIconButton(
                size = 30.dp,
                icon = StoneToDoIcons.ArrowRight,
                description = "Next period",
                onClick = { onCalendarNavigation(CalendarNavigation.NavigateToNextPeriod) }
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
            StoneToDoLabelText(
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
            today,
            selectedDate,
            todoListMap,
            onDateSelected,
            onDateRange
        )

        CalendarType.DayOfMonth -> DaysOfMonth(
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
            StoneToDoLabelText(
                text = day,
                modifier = Modifier.weight(1f),
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
private fun DaysOfWeek(
    today: LocalDate,
    selectedDate: LocalDate,
    todoListMap: Map<LocalDate, List<Todo>>,
    onDateSelected: (LocalDate) -> Unit,
    onDateRange: (LocalDate, LocalDate) -> Unit
) {
    val startOfWeek = selectedDate.with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY))
    val weekDates = generateSequence(startOfWeek) { it.plusDays(1) }
        .take(7)
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
    today: LocalDate,
    selectedDate: LocalDate,
    todoListMap: Map<LocalDate, List<Todo>>,
    onDateSelected: (LocalDate) -> Unit,
    onDateRange: (LocalDate, LocalDate) -> Unit
) {
    val firstDayOfMonth = selectedDate.withDayOfMonth(1)
    val daysInMonth = selectedDate.lengthOfMonth()
    val daysInPreviousMonth = firstDayOfMonth.dayOfWeek.value % 7
    val lastDayOfPreviousMonth = firstDayOfMonth.minusDays(1).dayOfMonth
    val totalDays = buildList {
        addAll((lastDayOfPreviousMonth - daysInPreviousMonth + 1..lastDayOfPreviousMonth).toList())
        addAll((1..daysInMonth).toList())
        addAll((1..(42 - (daysInPreviousMonth + daysInMonth))).toList())
    }

    onDateRange.invoke(firstDayOfMonth, selectedDate.withDayOfMonth(daysInMonth))

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
                    val date = LocalDate.of(selectedDate.year, selectedDate.monthValue, day)
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
    val todos = todoListMap[date] ?: emptyList()
    val hasTodos = todos.isNotEmpty()
    val allCheckTodo = todos.all { it.status }

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
                .size(25.dp)
                .background(
                    color = when {
                        isSelected -> Gray4
                        isToday -> Gray1
                        else -> Color.Transparent
                    },
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            StoneToDoLabelText(
                text = "${date.dayOfMonth}",
                fontWeight = if (isToday || isSelected) FontWeight.Bold else FontWeight.Normal,
                color = if (isSelected) White else Black,
            )
        }
        Spacer(modifier = Modifier.height(5.dp))
        Box(
            modifier = Modifier
                .size(5.dp)
                .clip(CircleShape)
                .drawBehind {
                    drawCircle(
                        if (hasTodos) {
                            if (allCheckTodo) Gray4 else Gray2
                        } else {
                            Color.Transparent
                        }
                    )
                }
        )
        Spacer(modifier = Modifier.height(5.dp))
    }
}