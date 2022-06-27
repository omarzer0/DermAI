package graduation.fcm.dermai.common

import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SharedPreferenceManger @Inject constructor(
    @ApplicationContext context: Context
) {

    private var sharedPreferences: SharedPreferences =
        context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)
    private var editor: SharedPreferences.Editor = sharedPreferences.edit()

    var hasLoggedIn: Boolean
        get() = getBooleanValue(LOGGED_IN)
        set(value) = setValue(LOGGED_IN, value)

    var openedTheAppBefore: Boolean
        get() = getBooleanValue(OPENED_THE_APP_BEFORE)
        set(value) = setValue(OPENED_THE_APP_BEFORE, value)

    var imageUri: String
        get() = getStringValue(IMAGE_URI) ?: ""
        set(value) = setValue(IMAGE_URI, value)

    var token: String
        get() = "Bearer ${getStringValue(TOKEN)}"
        //        get() = "Bearer 33|rhOdHiV77I1obOSKI1xBgHJZCjg7uYRXGBW5CcDX"
        set(value) = setValue(TOKEN, value)

    fun setValue(key: String, value: String) {
        editor.putString(key, value)
        editor.apply()
    }

    fun setValue(key: String, value: Int) {
        editor.putInt(key, value)
        editor.apply()
    }

    fun setValue(key: String, value: Float) {
        editor.putFloat(key, value)
        editor.apply()
    }

    fun setValue(key: String, value: Boolean) {
        editor.putBoolean(key, value)
        editor.apply()
    }

    fun getStringValue(key: String): String? {
        return sharedPreferences.getString(key, EMPTY)
    }

    fun getIntegerValue(key: String): Int {
        return sharedPreferences.getInt(key, 0)
    }

    fun getFloatValue(key: String): Float {
        return sharedPreferences.getFloat(key, 0F)
    }

    fun getBooleanValue(key: String): Boolean {
        return sharedPreferences.getBoolean(key, false)
    }

    fun remove(key: String) {
        editor.remove(key)
        editor.apply()
    }
}