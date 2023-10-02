package dong.duan.ecommerce.library.base

import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.PopupWindow
import androidx.viewbinding.ViewBinding
import dong.duan.ecommerce.library.PopUpLocation


class BasePopup<T : ViewBinding>(
    private val context: Context,
    private val anchorView: View,
    private val bindingInflater: (LayoutInflater) -> T,
    private val width: Int,
    private val height: Int = WindowManager.LayoutParams.WRAP_CONTENT,
    private val popUpLocation: PopUpLocation = PopUpLocation.DEFAULT_TOP,
    var callback: (T) -> Unit
) : PopupWindow(context) {
    private var x = 0
    private var y = 0
    private lateinit var popupWindow: PopupWindow

    init {
        init()
    }

    private fun init() {
        val inflater = LayoutInflater.from(context)
        val itemBinding = bindingInflater(inflater)

        val rootView = itemBinding.root
        popupWindow = PopupWindow(rootView, width, height, true)
        anchorView.post {
            x = anchorView.x.toInt()
            y = anchorView.y.toInt()

        }
        callback(itemBinding)
    }

    fun show() {
        popupWindow.showAtLocation(anchorView, Gravity.NO_GRAVITY, x, y)
    }

    fun close() {
        popupWindow.dismiss()
    }
}


class BasePopupLocation<T : ViewBinding>(
    private val context: Context,
    private val anchorView: View,
    private val bindingInflater: (LayoutInflater) -> T,
    private val x: Int,
    private val y: Int,
    private val width: Int = ViewGroup.LayoutParams.WRAP_CONTENT,
    private val height: Int = ViewGroup.LayoutParams.WRAP_CONTENT,
    var callback: (T, PopupWindow) -> Unit
) : PopupWindow(context) {

    private lateinit var popupWindow: PopupWindow

    init {
        init()
    }

    private fun init() {
        val inflater = LayoutInflater.from(context)
        val itemBinding = bindingInflater(inflater)

        val rootView = itemBinding.root

        popupWindow = PopupWindow(
            rootView,
            width,
            height,
            true
        )
        callback(itemBinding, popupWindow)
    }

    fun show() {
        popupWindow.showAtLocation(anchorView, Gravity.NO_GRAVITY, x, y)
    }
}
