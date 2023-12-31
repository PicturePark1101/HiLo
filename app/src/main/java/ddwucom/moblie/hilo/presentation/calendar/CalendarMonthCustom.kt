package ddwucom.moblie.hilo.presentation.calendar

import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.AttributeSet
import android.util.Log
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.view.children
import ddwucom.moblie.hilo.data.model.entity.FitnessRecord
import ddwucom.moblie.hilo.utils.CalendarUtils.Companion.DAYS_PER_WEEK
import ddwucom.moblie.hilo.utils.CalendarUtils.Companion.WEEKS_PER_MONTH
import ddwucom.moblie.hilo.utils.CalendarUtils.Companion.dpToPx
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class CalendarMonthCustom @JvmOverloads constructor(
    context: Context,
    val attrs: AttributeSet? = null,
    val defStyleAttr: Int = 0,
) :
    ViewGroup(context, attrs, defStyleAttr) {

//    lateinit var weatherData: List<MonthWeatherResponse>

//    private val onDateClickListener = object : CalendarDateCustom.OnDateClickListener {
//        @RequiresApi(Build.VERSION_CODES.O)
//        override fun onDateClick(date: LocalDate, weatherId: Int?) {
//            Log.d("캘린더 클릭", "Clicked date: $date, 여기는 CalendarMonth")
//            Log.d("calendar weatherId total view", "weather Id : $weatherId")
//
//            val mIntent = Intent(context, CalendarDetailActivity::class.java)
//            mIntent.putExtra("weatherId", weatherId)
//            mIntent.putExtra("date", date.toString())
//            context.startActivity(mIntent)
//        }
//    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        // Log.d("캘린더 순서", "onMeasure")
    }

    override fun onLayout(p0: Boolean, p1: Int, p2: Int, p3: Int, p4: Int) {
        val marginHorizontal = dpToPx(context, 16) // 좌우 마진
        val marginVertical = dpToPx(context, 12) // 상하 마진 크기 (픽셀 단위)
        var cWidth = (width - (DAYS_PER_WEEK - 1) * marginHorizontal) / DAYS_PER_WEEK
        var cHeight = (height - (WEEKS_PER_MONTH - 1) * marginVertical) / WEEKS_PER_MONTH

        children.forEachIndexed { index, view ->
            val left = (index % DAYS_PER_WEEK) * (cWidth + marginHorizontal)
            val top = (index / DAYS_PER_WEEK) * (cHeight + marginVertical)
            // if(index)
            view.layout(left, top, left + cWidth, top + cHeight)
        }
        // Log.d("캘린더 순서", "onLayout")
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun initCalendar(year: Int, month: Int, list: MutableList<LocalDate>, fitnessRecords: List<FitnessRecord>?) {
        val formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd")

        removeAllViews()

        list.forEach { localdata ->
            var thisDate = fitnessRecords?.filter { LocalDate.parse(it.fitnessDate, formatter) == localdata }
                ?.take(2)

            Log.d("캘린더에 날짜 넣음 ", "$thisDate}")

            val calendarDateView =
                CalendarDateCustom(
                    context = context,
                    thisDate = localdata,
                    thisMonth = month,
                    dataList = thisDate,
                )
//            // thisDate.weatherId
//            calendarDateView.setOnDateClickListener(onDateClickListener)
            addView(calendarDateView)
            calendarDateView.invalidate()
        }
        // Log.d("캘린더 순서", "initCalendar")
    }
}