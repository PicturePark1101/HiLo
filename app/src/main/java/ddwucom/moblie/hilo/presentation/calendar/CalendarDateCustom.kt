package ddwucom.moblie.hilo.presentation.calendar

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.os.Build
import android.text.TextPaint
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import androidx.core.content.withStyledAttributes
import ddwucom.moblie.hilo.R
import ddwucom.moblie.hilo.data.model.entity.FitnessRecord
import ddwucom.moblie.hilo.utils.CalendarUtils.Companion.checkTextSize
import ddwucom.moblie.hilo.utils.CalendarUtils.Companion.dpToPx
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
class CalendarDateCustom
@JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    val thisMonth: Int,
    val thisDate: LocalDate,
    val dataList: List<FitnessRecord>?,
) : View(context, attrs, defStyleAttr) {
    val TAG = "CalendarDateCustom"
    lateinit var datePaint: Paint
    lateinit var temPaint: Paint

    var customDrawable: Drawable? = null

//    //**삭제예정!!!!!
//    private val borderPaint = Paint().apply {
//        color = Color.BLACK // 테두리 색상
//        style = Paint.Style.STROKE // 선 스타일
//        strokeWidth = 5f // 선 두께
//    }

    interface OnDateClickListener {
        fun onDateClick(date: LocalDate)
    }

    private var onDateClickListener: OnDateClickListener? = null

    fun setOnDateClickListener(listener: OnDateClickListener) {
        this.onDateClickListener = listener
    }

    init {
        Log.d("date 클래스 ", "$thisMonth, ${thisDate.monthValue}")
        Log.d("date 클래스 ", "$dataList")
        if (thisMonth == thisDate.monthValue) {
            context.withStyledAttributes(
                attrs,
                R.styleable.Calendar,
                defStyleAttr,
            ) {

                datePaint = TextPaint().apply {
                    textSize = dpToPx(context, 12).toFloat()
                    isAntiAlias = true
                    textAlign = Paint.Align.CENTER
                }

                if (isLaterDay()) {
                    Log.d(TAG, "미래면 회색 글씨로")
                    datePaint.color = Color.parseColor("#D1CAC6")
                } else {
                    datePaint.color = Color.parseColor("#2B2B2B")

                    if (dataList != null) {
                        // Log.d("isLaterDay dataList널 아님", "${dataList}")
                        temPaint = TextPaint().apply {
                            textSize = dpToPx(context, 12).toFloat()
                            isAntiAlias = true
                            textAlign = Paint.Align.CENTER
                        }
//                        customDrawable = setWeatherIc(context, dataList.lastStatus)
                    }
                    setOnClickListener {
                        // 클릭 이벤트가 발생했을 때 콜백으로 해당 날짜 전달
                        onDateClickListener?.onDateClick(thisDate)
                    }
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        if (dataList != null) {
            Log.d("캘린더 날짜별로 들어가는 thisMonth thisDate", "${thisMonth}월 ${thisDate}일")
        }

        if (thisMonth == thisDate.monthValue) {
            val dateText = thisDate.dayOfMonth.toString()

            if (LocalDate.now() == thisDate) {
                circlePaint(dateText, canvas)
                datePaint.color = Color.WHITE
            }

            canvas?.drawText(
                dateText,
                (width / 2).toFloat(),
                dpToPx(context, 16).toFloat(),
                datePaint,
            )
            if (dataList != null && !isLaterDay()) {
                val drawableleft = (width / 2) - dpToPx(context, 24)
                val drawableright = drawableleft + dpToPx(context, 48)
                val drawableTop = dpToPx(context, 18).toInt()
                val drawableBoottom = drawableTop + dpToPx(context, 48)

//                customDrawable?.let {
//                    it.setBounds(drawableleft, drawableTop, drawableright, drawableBoottom)
//                    it.draw(canvas!!)
//                }
                val firstColor = Color.parseColor("#D1A8E0")
                val secondColor = Color.parseColor("#4287B2")
                var colorArr = arrayOf(firstColor, secondColor)

                var linePaint = Paint()

                var lineMargin = 0f
                for ((index, value) in dataList.withIndex()) {
                    println("Index: $index, Value: $value")
                    linePaint.color = colorArr.get(index)
                    linePaint.strokeWidth = 30f // 선 두께 설정

                    val screenWidth = width.toFloat()
                    val screenHeight = height.toFloat()

                    // 가로선 그리기 (가운데를 가로지르는)
                    val startX = 0f
                    val startY = screenHeight / 2
                    val endX = screenWidth
                    val endY = screenHeight / 2

                    canvas?.drawLine(startX, startY + lineMargin, endX, endY+ lineMargin, linePaint)
                    lineMargin += 60f
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun isLaterDay(): Boolean {
        val today = LocalDate.now()
        return today.isBefore(thisDate)
    }

    fun circlePaint(text: String, canvas: Canvas?) {
        val textBounds = checkTextSize(datePaint, text)
        val roundPaint = Paint()

        val x = (width / 2).toFloat()
        val y = dpToPx(context, 16).toFloat() - (textBounds.height() / 2)

        val radius = y

        roundPaint.color = Color.parseColor("#5E412F")

        // 원 그리기
        canvas?.drawCircle(x, y, radius, roundPaint)
    }
}
