package dong.duan.ecommerce.activity.account

import android.content.Intent
import android.os.Handler
import android.os.Looper
import dong.duan.ecommerce.R
import dong.duan.ecommerce.activity.home.MainActivity
import dong.duan.ecommerce.admin.AdminActivity
import dong.duan.ecommerce.library.base.BaseActivity
import dong.duan.ecommerce.databinding.ActivitySignInBinding
import dong.duan.ecommerce.library.log
import dong.duan.ecommerce.library.sharedPreferences
import dong.duan.ecommerce.utility.Constant


class SignInActivity : BaseActivity<ActivitySignInBinding>() {
    override fun getViewBinding() = ActivitySignInBinding.inflate(layoutInflater)
    override fun createView() {
        onClick()
        inputEvent()
    }

    private  var email=""
    private var pass=""

    private fun inputEvent() {
        binding.edtMail.setOnFocusChangeListener { view, b ->
            txtFocus(b, binding.icMail, binding.llemail)
        }

        binding.edtPass.setOnFocusChangeListener { view, b ->
            txtFocus(b, binding.icPasss, binding.llpass)
        }

        binding.btnSingIn.setOnClickListener {
            getValue()
            signIntoApp()
        }
    }

    private fun onClick() {
        binding.txtSignup.setOnClickListener {
            var intent = Intent(this, SignUpActivity::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            }
            startActivity(intent)
        }
    }

    private fun signIntoApp() {
        loadding.show()
        firestore.collection(Constant.KEY_USER)
            .whereEqualTo(Constant.USER_EMAIL,email)
            .whereEqualTo(Constant.USER_PASS,pass)
            .get()
            .addOnCompleteListener { taskId->
                if(taskId.isSuccessful&&taskId.result.size()>0){
                    var remoteUser = taskId.result.documents[0]
                    sharedPreferences.edit {
                        putBoolean(Constant.KEY_IS_SIGN_IN,true)
                        putString(Constant.USER_ID,remoteUser.id)
                        putString(Constant.USER_NAME, remoteUser.getString(Constant.USER_NAME))
                        putString(Constant.USER_EMAIL, remoteUser.getString(Constant.USER_EMAIL))
                        putString(Constant.USER_PASS, remoteUser.getString(Constant.USER_PASS))
                        putString(Constant.USER_BIRTHDAY,remoteUser.getString(Constant.USER_BIRTHDAY))
                        putString(Constant.USER_IMG,remoteUser.getString(Constant.USER_IMG) )
                        putString(Constant.USER_GENDER,remoteUser.getString(Constant.USER_GENDER) )
                        putString(Constant.USER_ADDRESS,remoteUser.getString(Constant.USER_ADDRESS) )
                        putString(Constant.USER_PHONE,remoteUser.getString(Constant.USER_PHONE) )
                        putString(Constant.ACCOUNT_TYPE,remoteUser.getString(Constant.ACCOUNT_TYPE))
                    }


                    log(remoteUser)

                    if(remoteUser.getString(Constant.ACCOUNT_TYPE)=="1"){
                        firestore.collection(Constant.KEY_SHOP)
                            .whereEqualTo(Constant.SHOP_US_ID,remoteUser.id)
                            .get()
                            .addOnCompleteListener { task ->
                                val remoteShop = task.result.documents[0]
                                sharedPreferences.edit {
                                    putBoolean(Constant.KEY_SHOP_INIT, true)
                                    putString(Constant.SHOP_ID, remoteShop.id)
                                    putString(Constant.SHOP_NAME,remoteShop.getString(Constant.SHOP_NAME))
                                    putString(Constant.USER_ADDRESS,remoteShop.getString(Constant.USER_ADDRESS) )
                                    putString(Constant.USER_PHONE,remoteShop.getString(Constant.USER_PHONE) )
                                    putString(Constant.SHOP_IMG_URL,remoteShop.getString(Constant.SHOP_IMG_URL) )
                                    putString(Constant.SHOP_IMG,remoteShop.getString(Constant.SHOP_IMG))
                                    putString(Constant.SHOP_PHONE,remoteShop.getString(Constant.SHOP_PHONE) )
                                    putString(
                                        Constant.SHOP_US_ID,
                                       remoteUser.id
                                    )
                                    putString(Constant.SHOP_COUTN_PR, "0")

                                    Handler(Looper.myLooper()!!)
                                        .postDelayed({
                                            loadding.dismiss()
                                            startActivity(
                                                Intent(
                                                    this@SignInActivity,
                                                    AdminActivity::class.java
                                                ).apply {
                                                    Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                                                })
                                            finish()
                                        },1200)


                                }
                            }

                    }
                    else{
                        Handler(Looper.myLooper()!!)
                            .postDelayed({
                                loadding.dismiss()
                                startActivity(
                                    Intent(
                                        this@SignInActivity,
                                        MainActivity::class.java
                                    ).apply {
                                        Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                                    })
                                finish()
                            },1200)

                    }
                }
            }.addOnFailureListener { e->
                binding.txtErrorPass.text="Email hoặc mâth khẩu không đúng!"
            }
    }

    private fun getValue() {
        email= binding.edtMail.text.toString()
        pass= binding.edtPass.text.toString()
    }


}