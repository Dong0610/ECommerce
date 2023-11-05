package dong.duan.ecommerce.dialog

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.widget.ImageView
import dong.duan.ecommerce.R
import dong.duan.ecommerce.library.base.BaseDialog

@SuppressLint("MissingInflatedId")
class DialogLoadding (context: Context): BaseDialog(context) {

    init {
        val view = LayoutInflater.from(context).inflate(R.layout.dialog_app_loadding, null)
        setContentView(view)
        val imgae = view.findViewById<ImageView>(R.id.img_loading)
        val rotation = ObjectAnimator.ofFloat(imgae, "rotation", 360f, 0f)
        rotation.duration = 800
        rotation.repeatCount = ObjectAnimator.INFINITE
        rotation.start()

        setCancelable(false)
    }
}
