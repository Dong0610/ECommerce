package dong.duan.ecommerce.fragment.other

import android.os.Build
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import com.egame.backgrounderaser.aigenerator.base.BaseFragment
import dong.duan.ecommerce.databinding.FragmentUpdateProfileBinding
import dong.duan.ecommerce.databinding.PopupChangeGentelBinding
import dong.duan.ecommerce.library.PopUpLocation
import dong.duan.ecommerce.library.sharedPreferences
import dong.duan.ecommerce.library.show_popup_menu
import dong.duan.ecommerce.library.show_toast
import dong.duan.ecommerce.utility.Constant

enum class UpdateType {
    UPDATE_NAME,
    UPDATE_GENDER,
    UPDATE_BIRTHDAY,
    UPDATE_EMAIL,
    UPDATE_PHONE,
    UPDATE_PASS

}

class UpdateProfileFragment(var updateType: UpdateType) :
    BaseFragment<FragmentUpdateProfileBinding>() {
    override fun getBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentUpdateProfileBinding.inflate(layoutInflater)

    @RequiresApi(Build.VERSION_CODES.O)
    override fun initView() {
        when (updateType) {
            UpdateType.UPDATE_NAME -> {
                binding.llUpdateName.visibility = View.VISIBLE
                binding.txtUpdateType.text = "Name"
            }

            UpdateType.UPDATE_GENDER -> {
                binding.llUpdateGentel.visibility = View.VISIBLE
                binding.txtUpdateType.text = "Giới tính"
                binding.txtNewGentel.text = sharedPreferences.getString(Constant.USER_GENDER)
            }

            UpdateType.UPDATE_BIRTHDAY -> {
                binding.llUpdateBirthday.visibility = View.VISIBLE
                binding.txtUpdateType.text = "Birthday"
            }

            UpdateType.UPDATE_PHONE -> {
                binding.llUpdatePhone.visibility = View.VISIBLE
                binding.txtUpdateType.text = "Phone"
            }

            UpdateType.UPDATE_EMAIL -> {
                binding.llUpdateEmail.visibility = View.VISIBLE
                binding.txtUpdateType.text = "Email"
            }

            UpdateType.UPDATE_PASS -> {
                binding.llUpdatePass.visibility = View.VISIBLE
                binding.txtUpdateType.text = "Password"
            }
        }

        binding.icBack.setOnClickListener {
            closeFragment(this)
        }

        initUpdateName()
        initUpdateEmail()
        initUpdatePass()
        initUpdateGentel()
        initUpdatePhone()
        initUpdateBirthday()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun initUpdateBirthday() {
        binding.datePicker.setOnDateChangedListener { datePicker, year, monthOfYear, dayOfMonth ->
            val formattedDate = String.format("%02d/%02d/%04d", dayOfMonth, monthOfYear + 1, year)
            binding.edtBirthday.setText(formattedDate)
        }
        binding.btnUpdateBirthday.setOnClickListener {

            firestore.collection(Constant.KEY_USER)
                .document(sharedPreferences.getString(Constant.USER_ID).toString())
                .update(Constant.USER_BIRTHDAY, binding.edtBirthday.text.toString())
                .addOnCompleteListener {
                    sharedPreferences.putString(
                        Constant.USER_PHONE,
                        binding.edtBirthday.text.toString()
                    )
                    show_toast("Thành công")
                    Handler(Looper.myLooper()!!).postDelayed({
                        closeFragment(this)
                    }, 1500L)

                }
        }
    }

    private fun initUpdatePhone() {
        binding.edtNewPhone.setText(sharedPreferences.getString(Constant.USER_PHONE))
        binding.edtNewPhone.setOnFocusChangeListener { view, b ->
            txtFocus(b, null, binding.llNewPhone)
        }
        binding.btnUpdatePhone.setOnClickListener {
            firestore.collection(Constant.KEY_USER)
                .document(sharedPreferences.getString(Constant.USER_ID).toString())
                .update(Constant.USER_PHONE, binding.edtNewPhone.text.toString())
                .addOnCompleteListener {
                    sharedPreferences.putString(
                        Constant.USER_PHONE,
                        binding.edtNewPhone.text.toString()
                    )
                    show_toast("Thành công")
                    Handler(Looper.myLooper()!!).postDelayed({
                        closeFragment(this)
                    }, 1500L)

                }
        }
    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun initUpdateGentel() {
        binding.txtNewGentel.text= sharedPreferences.getString(Constant.USER_GENDER).toString()
        binding.llNewGentel.setOnClickListener {
            binding.llNewGentel.post {
                show_popup_menu(
                    it,
                    PopupChangeGentelBinding::inflate,
                    width = it.width,
                    popUpLocation = PopUpLocation.DEFAULT_BOTTOM
                ) { itembinding, popup ->
                    itembinding.female.setOnClickListener {
                        binding.txtNewGentel.text = "Female"
                        popup.dismiss()
                    }
                    itembinding.male.setOnClickListener {
                        binding.txtNewGentel.text = "Male"
                        popup.dismiss()
                    }
                    itembinding.other.setOnClickListener {
                        binding.txtNewGentel.text = "Other"
                        popup.dismiss()
                    }
                }
            }
        }
        binding.btnUpdateGentel.setOnClickListener {
            firestore.collection(Constant.KEY_USER)
                .document(sharedPreferences.getString(Constant.USER_ID).toString())
                .update(Constant.USER_GENDER, binding.txtNewGentel.text.toString())
                .addOnCompleteListener {
                    sharedPreferences.putString(
                        Constant.USER_GENDER,
                        binding.txtNewGentel.text.toString()
                    )
                    show_toast("Thành công")
                    Handler(Looper.myLooper()!!).postDelayed({
                        closeFragment(this)
                    }, 1500L)

                }
        }
    }

    private fun initUpdatePass() {
        binding.edtOldPass.setOnFocusChangeListener { view, b ->
            txtFocus(b, null, binding.llOldPass)
        }
        binding.edtNewPass.setOnFocusChangeListener { view, b ->
            txtFocus(b, null, binding.llNewPass)
        }
        binding.edtRenewPass.setOnFocusChangeListener { view, b ->
            txtFocus(b, null, binding.llRenewPass)
        }
        binding.btnUpdatePass.setOnClickListener {
            val oldpass = sharedPreferences.getString(Constant.USER_PASS)
            val passOldEnter = binding.edtOldPass.text.toString()
            if (!oldpass.equals(passOldEnter)) {
                show_toast("Mật khẩu cũ không đúng!")
            } else {
                firestore.collection(Constant.KEY_USER)
                    .document(sharedPreferences.getString(Constant.USER_ID).toString())
                    .update(Constant.USER_PASS, binding.edtNewPass.text.toString())
                    .addOnCompleteListener {
                        sharedPreferences.putString(
                            Constant.USER_PASS,
                            binding.edtNewPass.text.toString()
                        )
                        show_toast("Thành công")
                        Handler(Looper.myLooper()!!).postDelayed({
                            closeFragment(this)
                        }, 1500L)

                    }
            }
        }
    }

    private fun initUpdateEmail() {
        binding.edtNewEmail.text = sharedPreferences.getString(Constant.USER_EMAIL, "").toString()
    }

    private fun initUpdateName() {

        binding.edtNewFirstName.setOnFocusChangeListener { view, b ->
            txtFocus(b, null, binding.llnewFistName)
        }
        binding.edtNewLastName.setOnFocusChangeListener { view, b ->
            txtFocus(b, null, binding.llnewLastName)
        }
        binding.btnUpdateName.setOnClickListener {
            val name = binding.edtNewFirstName.text.toString()
            val firstName = binding.edtNewLastName.text.toString()
            if (name.equals("")) {
                show_toast("Tên không được để trống")
            } else {
                firestore.collection(Constant.KEY_USER)
                    .document(sharedPreferences.getString(Constant.USER_ID).toString())
                    .update(Constant.USER_NAME, "${firstName} ${name}")
                    .addOnCompleteListener {
                        sharedPreferences.putString(Constant.USER_NAME, "${firstName} ${name}")
                        show_toast("Sửa thông tin thành công")
                        Handler(Looper.myLooper()!!).postDelayed({
                            closeFragment(this)
                        }, 1500L)

                    }
            }
        }
    }
}