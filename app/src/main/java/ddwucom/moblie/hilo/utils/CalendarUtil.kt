package ddwucom.moblie.hilo.utils

import android.content.Context
import android.graphics.Paint
import android.graphics.Rect
import android.util.Log

class CalendarUtils {

    companion object {
        const val DAYS_PER_WEEK = 7
        const val WEEKS_PER_MONTH = 6

        fun dpToPx(context: Context, dp: Int): Int {
            return (dp * context.resources.displayMetrics.density + 0.5f).toInt()
        }

        fun checkTextSize(paint: Paint, textString: String): Rect {
            val bounds = Rect()

            // getTextBounds()가 반환하는 값이 매개변수인 bounds에 할당된다.
            paint.getTextBounds(textString, 0, textString.length, bounds)

            // bounds에서 너비와 높이를 체크한다.
            val textWidth = bounds.width()
            val textHeight = bounds.height()
            return bounds
            Log.d("텍스트크기", "$textWidth, $textHeight")
        }
    }
}