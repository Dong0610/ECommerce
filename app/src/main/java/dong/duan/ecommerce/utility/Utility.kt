package dong.duan.ecommerce.utility


import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import dong.duan.ecommerce.R
import java.text.NumberFormat
import java.util.Locale


enum class OrderStatus {
    PROCESSING,
    WAIT_PROCESS,
    REJECT,
    RECEIVED,
    TRANSPORT
}


fun statusByType(type: String): String {
    when (type) {
        "PROCESSING" -> return "Đang xử lí"
        "WAIT_PROCESS" -> return "Chờ xử lí"
        "REJECT" -> return "Hủy đơn"
        "RECEIVED" -> return "Đã nhận"
        "TRANSPORT" -> return "Vận chuyển"
        else -> return "Xảy ra lỗi"
    }
}


fun colorByStatus(type: String): Int {
    return when (type) {
        "PROCESSING" -> Color.parseColor("#03A9F4") // Replace with the desired color code
        "WAIT_PROCESS" -> Color.parseColor("#FF9800") // Replace with the desired color code
        "REJECT" -> Color.parseColor("#FF0000") // Replace with the desired color code
        "RECEIVED" -> Color.parseColor("#FF01D301") // Replace with the desired color code
        "TRANSPORT" -> Color.parseColor("#8A2BE2") // Replace with the desired color code
        else -> Color.parseColor("#d5d5d5") // Default color if status doesn't match
    }
}


class CustomLinearLayout @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private var cornerRadius: Float =
        resources.getDimensionPixelSize(com.intuit.sdp.R.dimen._5sdp).toFloat()
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
                typedArray.getDimensionPixelSize(
                    R.styleable.CustomLinearLayout_strokeWidth,
                    strokeWidth
                )
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

    private var cornerRadius: Float =
        resources.getDimensionPixelSize(com.intuit.sdp.R.dimen._5sdp).toFloat()
    private var strokeColor: Int = ContextCompat.getColor(context, R.color.textcolor2)
    private var strokeWidth: Int = resources.getDimensionPixelSize(com.intuit.sdp.R.dimen._1sdp)
    private var gradientColors: IntArray? = null
    private var backgroundColor: Int = ContextCompat.getColor(context, R.color.white)

    init {
        attrs?.let {
            val typedArray = context.obtainStyledAttributes(it, R.styleable.CustomLinearLayout)
            cornerRadius =
                typedArray.getDimension(R.styleable.CustomLinearLayout_cornerRadius, cornerRadius)
            strokeColor =
                typedArray.getColor(R.styleable.CustomLinearLayout_strokeColor, strokeColor)
            strokeWidth =
                typedArray.getDimensionPixelSize(
                    R.styleable.CustomLinearLayout_strokeWidth,
                    strokeWidth
                )
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
            backgroundColor =
                typedArray.getColor(R.styleable.CustomLinearLayout_backgroundColor, backgroundColor)
            typedArray.recycle()
        }
        val backgroundDrawable = GradientDrawable()
        backgroundDrawable.setColor(backgroundColor)
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

    fun setBackground(color: Int) {
        backgroundColor = color
        val backgroundDrawable = GradientDrawable()
        backgroundDrawable.setColor(backgroundColor)
        backgroundDrawable.cornerRadius = cornerRadius
        backgroundDrawable.setStroke(strokeWidth, strokeColor)

        if (gradientColors != null && gradientColors!!.isNotEmpty()) {
            backgroundDrawable.gradientType = GradientDrawable.LINEAR_GRADIENT
            backgroundDrawable.colors = gradientColors
        }

        background = backgroundDrawable
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


class CheckBoxImageView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatImageView(context, attrs, defStyleAttr) {

    private var checked = false
    private var defImageRes = 0
    private var checkedImageRes = 0
    private var onCheckedChangeListener: OnCheckedChangeListener? = null

    init {
        init(attrs, defStyleAttr)
        this.setOnClickListener {
            onClick()
        }
    }

    fun isChecked(): Boolean {
        return checked
    }

    fun setChecked(checked: Boolean) {
        this.checked = checked // Use "=" instead of "!="
        setImageResource(if (isChecked()) checkedImageRes else defImageRes)
    }

    @SuppressLint("ResourceType")
    private fun init(attributeSet: AttributeSet?, defStyle: Int) {
        val a = context.obtainStyledAttributes(
            attributeSet,
            R.styleable.CheckBoxImageView,
            defStyle,
            0
        )
        defImageRes = a.getResourceId(R.styleable.CheckBoxImageView_default_img, 0)
        checkedImageRes = a.getResourceId(R.styleable.CheckBoxImageView_checked_img, 0)
        checked = a.getBoolean(R.styleable.CheckBoxImageView_checked, false)
        setImageResource(if (isChecked()) checkedImageRes else defImageRes)
        a.recycle()
    }

    private fun onClick() {
        checked = !checked
        setChecked(checked)
        onCheckedChangeListener?.onCheckedChanged(this, checked)
    }

    fun setOnCheckedChangeListener(onCheckedChangeListener: OnCheckedChangeListener?) {
        this.onCheckedChangeListener = onCheckedChangeListener
    }

    interface OnCheckedChangeListener {
        fun onCheckedChanged(buttonView: View?, isChecked: Boolean)
    }
}

fun formatToCurrency(value: Float): String {
    val locale = Locale("vi", "VN") // Set the Vietnamese locale
    val currencyFormat = NumberFormat.getCurrencyInstance(locale)
    return currencyFormat.format(value)
}