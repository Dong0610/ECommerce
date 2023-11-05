package dong.duan.ecommerce.fragment.main

import android.content.Intent
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.egame.backgrounderaser.aigenerator.base.BaseFragment
import dong.duan.ecommerce.activity.AddDataActivity
import dong.duan.ecommerce.activity.SplashActivity
import dong.duan.ecommerce.databinding.FragmentAccountBinding
import dong.duan.ecommerce.fragment.other.FragmentAddress
import dong.duan.ecommerce.fragment.other.FragmentCreditCard
import dong.duan.ecommerce.fragment.other.FragmentOrder
import dong.duan.ecommerce.fragment.other.UpdateProfileFragment
import dong.duan.ecommerce.fragment.other.UpdateType
import dong.duan.ecommerce.library.sharedPreferences
import dong.duan.ecommerce.library.show_toast
import dong.duan.ecommerce.utility.Constant

class FragmentAccount : BaseFragment<FragmentAccountBinding>() {
    override fun getBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentAccountBinding.inflate(layoutInflater)
    var isProfileDetail = true
    override fun initView() {
        binding.llProfile.setOnClickListener {
            if (isProfileDetail) {
                binding.llProfileDetail.visibility = View.VISIBLE
                binding.icLlprofileDetail.rotation = 90f
                isProfileDetail = false
            } else {
                binding.llProfileDetail.visibility = View.GONE
                binding.icLlprofileDetail.rotation = 0f
                isProfileDetail = true
            }
        }
        initProfile()
        setProfileDetail()
    }
    private fun initProfile() {
        binding.txtName.text = sharedPreferences.getString(Constant.USER_NAME)
        binding.txtGender.text = sharedPreferences.getString(Constant.USER_GENDER).toString()
        binding.txtBirthday.text = sharedPreferences.getString(Constant.USER_BIRTHDAY).toString()
        binding.txtEmail.text = sharedPreferences.getString(Constant.USER_EMAIL).toString()
        binding.txtPhoneNum.text = sharedPreferences.getString(Constant.USER_PHONE).toString()
        binding.txtPassW.text = "******"
        binding.txtIdUsers.setOnClickListener {
            show_toast("Đăng xuất thành công")
            android.os.Handler(Looper.myLooper()!!)
                .postDelayed({
                    sharedPreferences.putBollean(Constant.KEY_IS_SIGN_IN, false)
                    sharedPreferences.putBollean(Constant.KEY_SHOP_INIT, false)
                    sharedPreferences.clear()
                    startActivity(Intent(this.requireContext(), SplashActivity::class.java))
                }, 1500L)
        }
    }
    private fun setProfileDetail() {
        binding.imgUsers.setOnClickListener {
            startActivity(Intent(this.requireContext(),AddDataActivity::class.java))
        }
        binding.llgender.setOnClickListener {
            replaceFullViewFragment(UpdateProfileFragment(UpdateType.UPDATE_GENDER), true)
        }
        binding.txtName.setOnClickListener {
            replaceFullViewFragment(UpdateProfileFragment(UpdateType.UPDATE_NAME), true)
        }
        binding.llPhoneNum.setOnClickListener {
            replaceFullViewFragment(UpdateProfileFragment(UpdateType.UPDATE_PHONE), true)
        }
        binding.llPass.setOnClickListener {
            replaceFullViewFragment(UpdateProfileFragment(UpdateType.UPDATE_PASS), true)
        }
        binding.llEmail.setOnClickListener {
            replaceFullViewFragment(UpdateProfileFragment(UpdateType.UPDATE_EMAIL), true)
        }
        binding.llbirthday.setOnClickListener {
            replaceFullViewFragment(UpdateProfileFragment(UpdateType.UPDATE_BIRTHDAY), true)
        }
        binding.llOder.setOnClickListener {
            replaceFullViewFragment(FragmentOrder(), true)
        }
        binding.llAdress.setOnClickListener {
            replaceFullViewFragment(FragmentAddress(), true)
        }
        binding.llPayment.setOnClickListener {
            replaceFullViewFragment(FragmentCreditCard(), true)
        }
    }
}