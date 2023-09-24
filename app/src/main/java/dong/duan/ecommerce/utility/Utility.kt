package dong.duan.ecommerce.utility

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.widget.LinearLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import dong.duan.ecommerce.R

enum class OrderStatus {
    PROCESSING,
    WAIT_PROCESS,
    REJECT,
    RECEVIED,
    TRANSPORT
}


fun statusByType(type: String): String {
    when (type) {
        "PROCESSING" -> return "Đang xử lí"
        "WAIT_PROCESS" -> return "Chờ xử lí"
        "REJECT" -> return "Hủy đơn"
        "RECEVIED" -> return "Đã nhận"
        "TRANSPORT" -> return "Vận chuyển"
        else -> return "Xảy ra lỗi"
    }
}


fun colorByStatus(type: String): Int {
    return when (type) {
        "PROCESSING" -> Color.parseColor("#FF5733") // Replace with the desired color code
        "WAIT_PROCESS" -> Color.parseColor("#33FF57") // Replace with the desired color code
        "REJECT" -> Color.parseColor("#5733FF") // Replace with the desired color code
        "RECEIVED" -> Color.parseColor("#33FF99") // Replace with the desired color code
        "TRANSPORT" -> Color.parseColor("#FF3399") // Replace with the desired color code
        else -> Color.parseColor("#000000") // Default color if status doesn't match
    }
}


class CustomLinearLayout @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private var cornerRadius: Float = resources.getDimensionPixelSize(com.intuit.sdp.R.dimen._5sdp).toFloat()
    private var strokeColor: Int = ContextCompat.getColor(context, R.color.textcolor2)
    private var strokeWidth: Int = resources.getDimensionPixelSize(com.intuit.sdp.R.dimen._1sdp)
    private var gradientColors: IntArray? = null

    init {
        attrs?.let {
            val typedArray = context.obtainStyledAttributes(it, R.styleable.CustomLinearLayout)
            cornerRadius =
                typedArray.getDimension(R.styleable.CustomLinearLayout_cornerRadius, cornerRadius)
            strokeColor =
                typedArray.getColor(R.styleable.CustomLinearLayout_strokeColor, strokeColor)
            strokeWidth =
                typedArray.getDimensionPixelSize(R.styleable.CustomLinearLayout_strokeWidth, strokeWidth)
            gradientColors = typedArray.getResourceId(
                R.styleable.CustomLinearLayout_gradientColors,
                0
            ).let { resourceId ->
                if (resourceId != 0) {
                    resources.getIntArray(resourceId)
                } else {
                    null
                }
            }
            typedArray.recycle()
        }
        val backgroundDrawable = GradientDrawable()
        backgroundDrawable.setColor(ContextCompat.getColor(context, R.color.white))
        backgroundDrawable.cornerRadius = cornerRadius
        backgroundDrawable.setStroke(strokeWidth, strokeColor)

        if (gradientColors != null && gradientColors!!.isNotEmpty()) {
            backgroundDrawable.gradientType = GradientDrawable.LINEAR_GRADIENT
            backgroundDrawable.colors = gradientColors
        }

        background = backgroundDrawable
    }

    fun setCornerRadius(radius: Float) {
        cornerRadius = radius
        val backgroundDrawable = background as? GradientDrawable
        backgroundDrawable?.cornerRadius = radius
    }

    fun setStrokeColor(color: Int) {
        strokeColor = color
        val backgroundDrawable = background as? GradientDrawable
        backgroundDrawable?.setStroke(
            resources.getDimensionPixelSize(com.intuit.sdp.R.dimen._1sdp),
            strokeColor
        )
    }

    fun setStrokeWidth(width: Int) {
        strokeWidth = width
        val backgroundDrawable = background as? GradientDrawable
        backgroundDrawable?.setStroke(width, strokeColor)
    }

    fun setGradientColors(colors: IntArray) {
        gradientColors = colors
        val backgroundDrawable = background as? GradientDrawable
        if (gradientColors != null && gradientColors!!.isNotEmpty()) {
            backgroundDrawable?.gradientType = GradientDrawable.LINEAR_GRADIENT
            backgroundDrawable?.colors = gradientColors
        }
    }
}


class CustomConstrainLayout @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private var cornerRadius: Float = resources.getDimensionPixelSize(com.intuit.sdp.R.dimen._5sdp).toFloat()
    private var strokeColor: Int = ContextCompat.getColor(context, R.color.textcolor2)
    private var strokeWidth: Int = resources.getDimensionPixelSize(com.intuit.sdp.R.dimen._1sdp)
    private var gradientColors: IntArray? = null

    init {
        attrs?.let {
            val typedArray = context.obtainStyledAttributes(it, R.styleable.CustomLinearLayout)
            cornerRadius =
                typedArray.getDimension(R.styleable.CustomLinearLayout_cornerRadius, cornerRadius)
            strokeColor =
                typedArray.getColor(R.styleable.CustomLinearLayout_strokeColor, strokeColor)
            strokeWidth =
                typedArray.getDimensionPixelSize(R.styleable.CustomLinearLayout_strokeWidth, strokeWidth)
            gradientColors = typedArray.getResourceId(
                R.styleable.CustomLinearLayout_gradientColors,
                0
            ).let { resourceId ->
                if (resourceId != 0) {
                    resources.getIntArray(resourceId)
                } else {
                    null
                }
            }
            typedArray.recycle()
        }
        val backgroundDrawable = GradientDrawable()
        backgroundDrawable.setColor(ContextCompat.getColor(context, R.color.white))
        backgroundDrawable.cornerRadius = cornerRadius
        backgroundDrawable.setStroke(strokeWidth, strokeColor)

        if (gradientColors != null && gradientColors!!.isNotEmpty()) {
            backgroundDrawable.gradientType = GradientDrawable.LINEAR_GRADIENT
            backgroundDrawable.colors = gradientColors
        }

        background = backgroundDrawable
    }

    fun setCornerRadius(radius: Float) {
        cornerRadius = radius
        val backgroundDrawable = background as? GradientDrawable
        backgroundDrawable?.cornerRadius = radius
    }

    fun setStrokeColor(color: Int) {
        strokeColor = color
        val backgroundDrawable = background as? GradientDrawable
        backgroundDrawable?.setStroke(
            resources.getDimensionPixelSize(com.intuit.sdp.R.dimen._1sdp),
            strokeColor
        )
    }

    fun setStrokeWidth(width: Int) {
        strokeWidth = width
        val backgroundDrawable = background as? GradientDrawable
        backgroundDrawable?.setStroke(width, strokeColor)
    }

    fun setGradientColors(colors: IntArray) {
        gradientColors = colors
        val backgroundDrawable = background as? GradientDrawable
        if (gradientColors != null && gradientColors!!.isNotEmpty()) {
            backgroundDrawable?.gradientType = GradientDrawable.LINEAR_GRADIENT
            backgroundDrawable?.colors = gradientColors
        }
    }
}
