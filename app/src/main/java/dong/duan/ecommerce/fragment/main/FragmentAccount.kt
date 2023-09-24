package dong.duan.ecommerce.fragment.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.egame.backgrounderaser.aigenerator.base.BaseFragment
import dong.duan.ecommerce.databinding.FragmentAccountBinding
import dong.duan.ecommerce.fragment.other.FragmentAddress
import dong.duan.ecommerce.fragment.other.FragmentCreditCard
import dong.duan.ecommerce.fragment.other.FragmentOrder
import dong.duan.ecommerce.fragment.other.UpdateProfileFragment
import dong.duan.ecommerce.fragment.other.UpdateType

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

        setProfileDetail()
    }

    private fun setProfileDetail() {
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