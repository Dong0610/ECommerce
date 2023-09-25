package dong.duan.ecommerce.fragment.other

import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.ViewGroup
import com.egame.backgrounderaser.aigenerator.base.BaseFragment
import dong.duan.ecommerce.databinding.FragmentEditAddressBinding
import dong.duan.ecommerce.library.sharedPreferences
import dong.duan.ecommerce.library.show_toast
import dong.duan.ecommerce.model.Address
import dong.duan.ecommerce.utility.Constant

class FragmentEditAddress(var address: Address?) : BaseFragment<FragmentEditAddressBinding>() {
    override fun getBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentEditAddressBinding.inflate(layoutInflater)

    override fun initView() {

        binding.icBack2.setOnClickListener {
            closeFragment(this)
        }
        focustEvent()
        if (address != null) {
            initData()
        }
        binding.btnSaveAddress.setOnClickListener {
            if (checkData()) {
                saveAddress()
            } else {
                show_toast("Các giá trị không được trống")
            }
        }
    }

    private fun saveAddress() {
        val hasmap = HashMap<String, Any>()
        hasmap[Constant.ADR_REMIND_NAME] = remindName
        hasmap[Constant.ADR_US_NAME] = reciveName
        hasmap[Constant.ADR_ADDRESS] = detailLocation
        hasmap[Constant.ADR_F_PHONE] = firstNum
        hasmap[Constant.ADR_S_PHONE] = secondNum

        if(address!=null){
            database.getReference(Constant.KEY_ADDRESS)
                .child(sharedPreferences.getString(Constant.USER_ID).toString())
                .child(address!!.idAddress)
                .setValue(hasmap)
                .addOnCompleteListener {
                    show_toast("Địa chỉ đã được lưu lại")
                    Handler(Looper.myLooper()!!)
                        .postDelayed({
                            closeFragment(this@FragmentEditAddress)
                        }, 1500L)
                }.addOnFailureListener { e->
                    show_toast(e.message.toString())
                    Handler(Looper.myLooper()!!)
                        .postDelayed({
                            closeFragment(this@FragmentEditAddress)
                        }, 1500L)
                }
        }
        else{
            database.getReference(Constant.KEY_ADDRESS)
                .child(sharedPreferences.getString(Constant.USER_ID).toString())
                .push()
                .setValue(hasmap)
                .addOnCompleteListener {
                    show_toast("Địa chỉ đã được lưu lại")
                    Handler(Looper.myLooper()!!)
                        .postDelayed({
                            closeFragment(this@FragmentEditAddress)
                        }, 1500L)
                }.addOnFailureListener { e->
                    show_toast(e.message.toString())
                    Handler(Looper.myLooper()!!)
                        .postDelayed({
                            closeFragment(this@FragmentEditAddress)
                        }, 1500L)
                }
        }
    }

    var firstNum = ""
    var secondNum = ""
    var remindName = ""
    var detailLocation = ""
    var reciveName = ""

    private fun checkData(): Boolean {
        firstNum = binding.edtFirstNum.text.toString()
        secondNum = binding.edtSecondNum.text.toString()
        remindName = binding.edtRemindName.text.toString()
        detailLocation = binding.edtDetailLocation.text.toString()
        reciveName = binding.edtReciveName.text.toString()
        if (firstNum.isEmpty() || remindName.isEmpty() ||
            detailLocation.isEmpty() || reciveName.isEmpty()
        ) {

            return false
        }
        return true
    }


    private fun initData() {
        binding.edtFirstNum.setText(address!!.phoneNumber)
        binding.edtSecondNum.setText(address!!.phoneNumber2)
        binding.edtRemindName.setText(address!!.remindName)
        binding.edtDetailLocation.setText(address!!.location)
        binding.edtReciveName.setText(address!!.receiverName)
    }

    private fun focustEvent() {
        binding.edtRemindName.setOnFocusChangeListener { view, b ->
            txtFocus(b, null, binding.llRemindName)
        }
        binding.edtReciveName.setOnFocusChangeListener { view, b ->
            txtFocus(b, null, binding.llReciveName)
        }
        binding.edtDetailLocation.setOnFocusChangeListener { view, b ->
            txtFocus(b, null, binding.llDetailLocate)
        }
        binding.edtFirstNum.setOnFocusChangeListener { view, b ->
            txtFocus(b, null, binding.llFirstNum)
        }
        binding.edtSecondNum.setOnFocusChangeListener { view, b ->
            txtFocus(b, null, binding.llSecondNum)
        }
    }
}