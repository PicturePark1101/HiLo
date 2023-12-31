package ddwucom.moblie.hilo.presentation.calendar

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.fragment.app.activityViewModels
import ddwucom.moblie.hilo.R
import ddwucom.moblie.hilo.base.BaseFragment
import ddwucom.moblie.hilo.databinding.FragmentCalendarMonthBinding
import ddwucom.moblie.hilo.presentation.viewmodel.FitnessRecordViewModel
import java.time.LocalDate

class CalendarMonth : BaseFragment<FragmentCalendarMonthBinding>(R.layout.fragment_calendar_month) {

    var dateList: MutableList<LocalDate> = mutableListOf()
    private val fitnessRecordViewModel: FitnessRecordViewModel by activityViewModels()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onResume() {
        super.onResume()
        val year = arguments?.getInt("year")
        val month = arguments?.getInt("month")
        val id = arguments?.getString("id")

        getDate(year!!, month!!)
        // Log.d("날짜확인확인", "$year $month")
        var recordList = fitnessRecordViewModel.allLocation.value

        binding.ctCalendarCustom.initCalendar(year!!, month!!, dateList, recordList)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getDate(year: Int, month: Int) {
        val firstDay = LocalDate.of(year, month, 1)
        // 이번 달 첫날의 요일 (월요일: 1, 화요일: 2, ..., 일요일: 7)
        val dayOfWeekValue = firstDay.dayOfWeek.value
        var n = 0

        if (dayOfWeekValue != 7) {
            n = dayOfWeekValue
        }

        var startDate = firstDay.minusDays(n.toLong())
        for (i in 1..42) {
            // Log.d("날짜확인확인", "${startDate.year} ${startDate.monthValue}, ${startDate.dayOfMonth}")
            dateList.add(startDate)
            startDate = startDate.plusDays(1)
        }
    }
}
