package dong.duan.ecommerce.library

import android.content.Context
import android.content.SharedPreferences

class SharedPreference {
    private val sharedPreferences: SharedPreferences =
        AppContext.context?.getSharedPreferences("SharePrfeChat", Context.MODE_PRIVATE)!!

    fun edit(block: SharedPreferences.Editor.() -> Unit) {
        with(sharedPreferences.edit()) {
            block()
            apply()
        }
    }


    fun getBollean(key: String, default: Boolean = false): Boolean {
        return sharedPreferences.getBoolean(key, default)
    }

    fun putBollean(key: String, value: Boolean) {
        edit {
            putBoolean(key, value)
        }
    }

    fun getString(key: String, default: String? = null): String? {
        return sharedPreferences.getString(key, default)
    }

    fun putString(key: String, value: String) {
        edit {
            putString(key, value)
        }
    }

    fun clear() {
        edit {
            clear()
        }
    }
}

val sharedPreferences: SharedPreference
    get() {
        return SharedPreference()
    }

