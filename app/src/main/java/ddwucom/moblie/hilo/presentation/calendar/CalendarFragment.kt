package ddwucom.moblie.hilo.presentation.calendar

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.PopupWindow
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.ListPopupWindow
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import ddwucom.moblie.hilo.R
import ddwucom.moblie.hilo.base.BaseFragment
import ddwucom.moblie.hilo.databinding.FragmentCalendarBinding
import ddwucom.moblie.hilo.presentation.adapter.CalendarMonthAdapter
import java.time.LocalDate
import java.time.temporal.ChronoUnit

class CalendarFragment : BaseFragment<FragmentCalendarBinding>(R.layout.fragment_calendar) {
    private val TAG = "CalendarTotalFragment"

    lateinit var calendarMonthAdapter: CalendarMonthAdapter
    private var currentPosition = 0
    private var month = 0
    private var year = 0



    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        calendarMonthAdapter = CalendarMonthAdapter(requireActivity())
        binding.vp2CalendarTotal.adapter = calendarMonthAdapter
        binding.vp2CalendarTotal.setCurrentItem(CalendarMonthAdapter.START_POSITION, false)
        // 연과 월을 띄움
        setDateInfo()

        binding.vp2CalendarTotal.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                Log.d(TAG, "ViewPager2에서 변경감지")
                setDateInfo()
            }
        })
        binding.btnCalendarTotalBack.setOnClickListener {
            moveDate(-1, true)
        }
        binding.btnCalendarTotalNext.setOnClickListener {
            moveDate(1, true)
        }
    }

    private fun setDateInfo() {
        currentPosition = binding.vp2CalendarTotal.currentItem
        val itemId = calendarMonthAdapter.getItemId(currentPosition).toInt()
        Log.d(TAG, "START_POSITION = ${CalendarMonthAdapter.START_POSITION}, currentPosition = $currentPosition")
        month = itemId % 100
        year = itemId / 100
        binding.tvCalendarTotalMonth.text = month.toString() + "월"
        binding.tvCalendarTotalYear.text = year.toString() + "년"
    }

    private fun moveDate(move: Int, smoothScroll: Boolean) {
        currentPosition = binding.vp2CalendarTotal.currentItem
        binding.vp2CalendarTotal.setCurrentItem(currentPosition + move, smoothScroll)
    }
}
