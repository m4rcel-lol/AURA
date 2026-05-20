package dev.m5rcel.aura.notification.reminder

import android.graphics.Color
import dev.m5rcel.aura.domain.model.ReminderColor

object ReminderColorMapper {
    fun mapToColorInt(color: ReminderColor): Int {
        return when (color) {
            ReminderColor.DEFAULT -> Color.parseColor("#6750A4") // Material components Default Purple
            ReminderColor.RED -> Color.parseColor("#BA1A1A")
            ReminderColor.GREEN -> Color.parseColor("#386A20")
            ReminderColor.BLUE -> Color.parseColor("#0061A4")
            ReminderColor.YELLOW -> Color.parseColor("#D0BCFF")
            ReminderColor.PURPLE -> Color.parseColor("#7F00FF")
        }
    }
}
