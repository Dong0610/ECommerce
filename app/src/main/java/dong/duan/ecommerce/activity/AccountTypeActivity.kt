package dong.duan.ecommerce.activity

import android.content.Intent
import dong.duan.ecommerce.activity.home.MainActivity
import dong.duan.ecommerce.admin.AdminActivity
import dong.duan.ecommerce.admin.InitShopDataActivity
import dong.duan.ecommerce.databinding.ActivityChososeAccountTypeBinding
import dong.duan.ecommerce.library.base.BaseActivity
import dong.duan.ecommerce.library.sharedPreferences
import dong.duan.ecommerce.utility.Constant

class AccountTypeActivity : BaseActivity<ActivityChososeAccountTypeBinding>() {

    override fun getViewBinding() = ActivityChososeAccountTypeBinding.inflate(layoutInflater)

    override fun createView() {
        binding.btnSeller.setOnClickListener {
            sharedPreferences.putString(Constant.ACCOUNT_TYPE, "1")
            updateAccountType("1")
            if (sharedPreferences.getBollean(Constant.KEY_SHOP_INIT, false) == false) {
                startActivity(Intent(this, InitShopDataActivity::class.java))
                finish()
            } else {
                startActivity(Intent(this, AdminActivity::class.java))
                finish()
            }
        }

        binding.btnUsers.setOnClickListener {
            sharedPreferences.putString(Constant.ACCOUNT_TYPE, "2")
            updateAccountType("2")
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

    fun updateAccountType(type: String) {
        firestore.collection(Constant.KEY_USER)
            .document(sharedPreferences.getString(Constant.USER_ID).toString())
            .update(Constant.ACCOUNT_TYPE, type)
    }
}