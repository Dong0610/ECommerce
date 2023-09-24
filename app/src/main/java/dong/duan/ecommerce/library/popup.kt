package dong.duan.ecommerce.library

import android.annotation.SuppressLint
import android.os.Build
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.PopupWindow
import androidx.annotation.RequiresApi
import androidx.viewbinding.ViewBinding


@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UseCompatLoadingForDrawables")
fun <VB : ViewBinding> show_popup_menu(
    anchorView: View,
    bindingInflater: (LayoutInflater) -> VB,
    width: Int,
    height: Int = WindowManager.LayoutParams.WRAP_CONTENT,
    popUpLocation: PopUpLocation = PopUpLocation.DEFAULT_TOP,
    callback: (VB,PopupWindow) -> Unit
) {
    lateinit var popupWindow: PopupWindow
    val inflater = LayoutInflater.from(anchorView.context)
    val popupView = bindingInflater(inflater)

    val rootView = popupView.root
    popupWindow = PopupWindow(rootView, width, height, true)
    anchorView.post {
        val location = IntArray(2)
        anchorView.getLocationOnScreen(location)
        var x = location[0]
        var y = location[1]

        when(popUpLocation){
            PopUpLocation.DEFAULT_BOTTOM ->{
                x=( (screen_width-width)/2 )
                y= anchorView.height+y+ height_percent(1f)
            }
            PopUpLocation.DEFAULT_TOP ->{
                x=( (screen_width-width)/2 )
                y=y+ height_percent(1f)
            }
            PopUpLocation.TOP ->{
                x=( (screen_width-width)/2 )
                y=0+ height_percent(1f)
            }
            PopUpLocation.BOTTOM ->{
                x=( (screen_width-width)/2 )
                y= screen_height - anchorView.width - height_percent(1f)
            }
        }
        popupWindow.showAtLocation(anchorView, Gravity.NO_GRAVITY, x, y)

    }
    callback(popupView,popupWindow)
}

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UseCompatLoadingForDrawables", "SuspiciousIndentation")
fun <VB : ViewBinding> show_popup_menu(
    anchorView: View,
    bindingInflater: (LayoutInflater) -> VB,
    width: Int,
    height: Int = WindowManager.LayoutParams.WRAP_CONTENT,
    locationX:Int,locationY: Int,
    callback: (VB) -> Unit
) {
    lateinit var popupWindow: PopupWindow
    val inflater = LayoutInflater.from(anchorView.context)
    val popupView = bindingInflater(inflater)
    callback(popupView)
    val rootView = popupView.root
    popupWindow = PopupWindow(rootView, width, height, true)
        popupWindow.showAtLocation(anchorView, Gravity.NO_GRAVITY, locationX, locationY)


}

enum class PopUpLocation {
    TOP,
    BOTTOM,
    DEFAULT_TOP,
    DEFAULT_BOTTOM
}
