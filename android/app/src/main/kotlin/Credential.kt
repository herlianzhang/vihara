package pro.herlian.vihara

import android.content.Context
import android.content.SharedPreferences

object Credential {
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor

    private const val ID_TOKEN = "ID_TOKEN"
    private const val PREF_NAME = "CREDENTIAL_PREF"

    fun initSharedPref(context: Context) {
        sharedPreferences = context.applicationContext.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()
    }

    fun isSignedIn(): Boolean {
        return sharedPreferences.getString(ID_TOKEN, null) != null
    }

    fun storeIDToken(token: String) {
        editor.putString(ID_TOKEN, token)
        editor.commit()
    }

    fun clearAll() {
        editor.clear().commit()
    }
}