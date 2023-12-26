package ddwucom.moblie.hilo.presentation.adapter

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import ddwucom.moblie.hilo.presentation.calendar.CalendarMonth
import java.util.Calendar
import java.util.Date

class CalendarMonthAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {
    private val TAG = "CalendarMonthAdapter"
    companion object {
        const val START_POSITION = Int.MAX_VALUE / 2
    }
    override fun getItemId(position: Int): Long {
        Log.d(TAG, position.toString())
        var calendar = Calendar.getInstance()
        calendar.time = Date()
        calendar.set(Calendar.DAY_OF_MONTH, 1)
        calendar.add(Calendar.MONTH, position - START_POSITION)

        var currentYear = calendar.get(Calendar.YEAR)
        var currentMonth = calendar.get(Calendar.MONTH) + 1
        return (currentYear * 100L + currentMonth).toLong()
    }

    override fun getItemCount(): Int = Int.MAX_VALUE

    @RequiresApi(Build.VERSION_CODES.O)
    override fun createFragment(position: Int): CalendarMonth {
        val itemId = getItemId(position).toInt()
        return CalendarMonth().apply {
            arguments = Bundle().apply {
                putInt("year", itemId / 100)
                putInt("month", itemId % 100)
                putString("id", itemId.toString())
            }
        }
    }

    override fun containsItem(itemId: Long): Boolean {
        return 190000L <= itemId && itemId <= 220000L
    }
}