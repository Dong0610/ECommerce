package dong.duan.ecommerce.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import dong.duan.ecommerce.activity.account.SignUpActivity
import dong.duan.ecommerce.activity.home.MainActivity
import dong.duan.ecommerce.admin.AdminActivity
import dong.duan.ecommerce.databinding.ActivitySplashBinding
import dong.duan.ecommerce.library.no_titlebar
import dong.duan.ecommerce.library.sharedPreferences
import dong.duan.ecommerce.utility.Constant

class SplashActivity : AppCompatActivity() {

    val binding by lazy {
        ActivitySplashBinding.inflate(layoutInflater)
    }

    private val progressBarMax = 100
    private val updateInterval = 50L
    private var currentProgress = 0
    private lateinit var progressHandler: Handler
    private lateinit var progressRunnable: Runnable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        no_titlebar(this@SplashActivity)
        setContentView(binding.root)
        binding.loading.max = progressBarMax
        binding.loading.progress = currentProgress
        progressHandler = Handler(Looper.getMainLooper())
        progressRunnable = Runnable {
            if (currentProgress < progressBarMax) {
                currentProgress += 5
                binding.loading.progress = currentProgress
                progressHandler.postDelayed(progressRunnable, updateInterval)
            }
            if (currentProgress == 100) {
                if (sharedPreferences.getBollean(Constant.KEY_IS_SIGN_IN) == true) {
                    val acctype=sharedPreferences.getString(Constant.ACCOUNT_TYPE)
                    if (acctype.equals("0")) {
                        startActivity(Intent(this, AccountTypeActivity::class.java))
                        finish()
                    } else if(acctype.equals("1")) {
                        startActivity(Intent(this, AdminActivity::class.java))
                        finish()
                    }
                    else{
                        startActivity(Intent(this, MainActivity::class.java))
                        finish()
                    }
                } else {
                    startActivity(Intent(this, SignUpActivity::class.java))
                    finish()
                }

            }
        }
        startProgressAnimation()


    }

    private fun startProgressAnimation() {
        currentProgress = 0
        progressHandler.postDelayed(progressRunnable, updateInterval)
    }
}