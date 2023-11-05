package dong.duan.ecommerce.activity.account

import android.content.Intent
import dong.duan.ecommerce.activity.AccountTypeActivity
import dong.duan.ecommerce.databinding.ActivitySignUpBinding
import dong.duan.ecommerce.library.base.BaseActivity
import dong.duan.ecommerce.library.check_email
import dong.duan.ecommerce.library.sharedPreferences
import dong.duan.ecommerce.library.show_toast
import dong.duan.ecommerce.utility.Constant

@Suppress("UNREACHABLE_CODE")
class SignUpActivity : BaseActivity<ActivitySignUpBinding>() {
    override fun getViewBinding() = ActivitySignUpBinding.inflate(layoutInflater)
    override fun createView() {
        clickEvent()
        inputEvent()
    }

    private lateinit var name: String
    private lateinit var email: String
    private lateinit var pass: String
    private fun inputEvent() {
        binding.edtusname.setOnFocusChangeListener { view, b ->
            txtFocus(b, binding.icUser, binding.llUsname)
        }
        binding.edtemail.setOnFocusChangeListener { view, b ->
            txtFocus(b, binding.icMail, binding.llEmail)
        }
        binding.edtpass.setOnFocusChangeListener { view, b ->
            txtFocus(b, binding.icPasss, binding.llPass)
        }
        binding.edtpassagain.setOnFocusChangeListener { view, b ->
            txtFocus(b, binding.icPasssAgain, binding.llpass2)
        }
    }

    private fun clickEvent() {
        binding.txtSignin.setOnClickListener {
            startActivity(Intent(this@SignUpActivity, SignInActivity::class.java))
        }
        binding.btnSignup.setOnClickListener {
            signUp()
        }
    }


    private fun checkvaluex(): Boolean {
        name = binding.edtusname.text.toString()
        email = binding.edtemail.text.toString()
        pass = binding.edtpass.text.toString()
        if (name.length < 4) {
            binding.txtErrorFulname.text = "Tên đầy đủ quá ngắn"
            return false
        } else {
            binding.txtErrorFulname.text = ""
            return true
        }
        if (!check_email(email)) {
            binding.txtErrorEmail.text = "Email không đúng!"
            return false
        } else {
            binding.txtErrorEmail.text = ""
            return true
        }
        if (pass.length < 5) {
            binding.txtErrorPass.text = "Mật khẩu dài hơn 6 kí tự"
            return false
        } else {
            binding.txtErrorPass.text = ""
            return true
        }
        if (binding.edtpassagain.text.toString() != pass) {
            binding.txtErrorPassAgain.text = "Nhập lại mật khẩu không đúng"
            return false
        } else {
            binding.txtErrorPassAgain.text = ""
            return true
        }
        return true
    }

    private fun signUp() {
        if (checkvaluex()) {
            loadding.show()
            val hasmap = HashMap<String, String>()
           
            hasmap[Constant.USER_NAME] = binding.edtusname.text.toString()
            hasmap[Constant.USER_EMAIL] = email
            hasmap[Constant.USER_PASS] = pass
            hasmap[Constant.USER_BIRTHDAY] = ""
            hasmap[Constant.USER_IMG] = ""
            hasmap[Constant.USER_GENDER] = ""
            hasmap[Constant.USER_ADDRESS] = ""
            hasmap[Constant.USER_PHONE] = ""
            hasmap[Constant.ACCOUNT_TYPE] = "0"
            firestore.collection(Constant.KEY_USER).add(hasmap)
                .addOnSuccessListener { task ->
                    loadding.dismiss()
                    val userID= task.id
                    sharedPreferences.edit {
                        putBoolean(Constant.KEY_IS_SIGN_IN,true)
                        putString(Constant.USER_ID,userID)
                        putString(Constant.USER_NAME, binding.edtusname.text.toString())
                        putString(Constant.USER_EMAIL, binding.edtemail.text.toString())
                        putString(Constant.USER_PASS, pass)
                        putString(Constant.USER_BIRTHDAY, "")
                        putString(Constant.USER_IMG, "")
                        putString(Constant.USER_GENDER, "")
                        putString(Constant.USER_ADDRESS, "")
                        putString(Constant.USER_PHONE, "")
                        putString(Constant.ACCOUNT_TYPE,"0")
                    }
                    if(userID!=null&&userID.length>0){
                        startActivity(Intent(this, AccountTypeActivity::class.java))
                        finish()
                    }
                }
                .addOnFailureListener {
                    show_toast(it.message.toString())
                }
        }
    }
}

