package id.lagu.service

import android.content.Context
import android.content.SharedPreferences

class LaguSession : LaguManager {
    companion object {
        val KEY_IS_LOGIN_VIA_Google = "isLoginViaGoogle"
        val KEY_IS_LOGIN_VIA_FACEBOOK = "isLoginViaFacebook"
        val KEY_IS_AUTHENTICATED = "isAuthenticated"
        val KEY_IS_NOT_FIRST_INSTALLED = "isNotFirstInstalled"
        val KEY_IS_GOOGLE_USER_INFO_NAME = "googleUserInfoName";
        val KEY_IS_GOOGLE_USER_INFO_EMAIL = "googleUserInfoEmail";
        val KEY_IS_GOOGLE_USER_INFO_PHOTO = "googleUserInfoPhoto";
    }

    var mPref: SharedPreferences? = null
    var mEditor: SharedPreferences.Editor? = null
    private var mContext: Context? = null
    val PRIVATE_MODE = 0
    val PREF_NAME = "Pantau"
    val KEY_AUTHORIZATION = "auth"

    constructor(context: Context) {
        this.mContext = context
        this.mPref = this.mContext!!.getSharedPreferences(PREF_NAME, PRIVATE_MODE)
        this.mEditor = this.mPref!!.edit()
    }

    private fun setAuthorization(s: String) {
        this.mEditor?.putString(KEY_AUTHORIZATION, s)
        this.mEditor?.commit()
    }

    fun getAutorization(): String {
        return this.mPref!!.getString(KEY_AUTHORIZATION, "")
    }

    override fun clear() {
        this.mEditor?.clear()
        this.mEditor?.commit()
    }

    fun getSessionBoolean(key: String): Boolean? {
        return mPref?.getBoolean(key, false)
    }

    fun getSessionString(key: String): String? {
        return mPref?.getString(key, key)
    }

    fun getSessionInt(key: String): Int? {
        return mPref?.getInt(key, 0)
    }

    fun saveSession(key: String, value: Boolean) {
        mPref?.edit()?.putBoolean(key, value)?.apply()
    }

    fun saveSession(key: String, value: Int) {
        mPref?.edit()?.putInt(key, value)?.apply()
    }

    fun saveSession(key: String, value: String) {
        mPref?.edit()?.putString(key, value)?.apply()
    }


    fun isLoginViaGoogle(): Boolean? {
        return getSessionBoolean(KEY_IS_LOGIN_VIA_Google)
    }

    fun isLoginViaFacebook(): Boolean? {
        return getSessionBoolean(KEY_IS_LOGIN_VIA_FACEBOOK)
    }

    override fun storeToken(access_token: String) {
        this.setAuthorization(access_token)
    }

    override fun getToken() {
        this.getAutorization()
    }

    fun logout() {
        mPref?.edit()?.clear()?.apply()
        saveSession(KEY_IS_NOT_FIRST_INSTALLED, true)
        saveSession(KEY_IS_AUTHENTICATED, false)
    }

    fun isAuthenticated(): Boolean? {
        return getSessionBoolean(KEY_IS_AUTHENTICATED)
    }

    fun isFirstInstalled(): Boolean? {
        return !getSessionBoolean(KEY_IS_NOT_FIRST_INSTALLED)!!
    }

    fun signInGoogle(name: String, email: String, photo: String) {
        mPref?.edit()?.putString(KEY_IS_GOOGLE_USER_INFO_NAME, name)?.apply()
        mPref?.edit()?.putString(KEY_IS_GOOGLE_USER_INFO_EMAIL, email)?.apply()
        mPref?.edit()?.putString(KEY_IS_GOOGLE_USER_INFO_PHOTO, photo)?.apply()
    }

}