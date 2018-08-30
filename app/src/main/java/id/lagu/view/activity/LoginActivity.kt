package id.lagu.view.activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.facebook.*
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import id.lagu.R
import id.lagu.model.FacebookUserInfo
import id.lagu.service.LaguSession
import kotlinx.android.synthetic.main.activity_login.*
import org.json.JSONObject
import java.util.*

class LoginActivity : BaseActivity(), View.OnClickListener {
    private var mGoogleSignInClient: GoogleSignInClient? = null
    private val RC_SIGN_IN = 17
    private var callbackManager: CallbackManager? = null
    private var accessTokenTracker: AccessTokenTracker? = null
    private var accessToken: AccessToken? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_login)

        btn_login_fb.setOnClickListener(this)
        btn_login_google.setOnClickListener(this)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build()

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

        callbackManager = CallbackManager.Factory.create()
        btn_login_fb_1.setReadPermissions(Arrays.asList("email"))
        btn_login_fb_1.registerCallback(callbackManager,
                object : FacebookCallback<LoginResult> {
                    override fun onSuccess(loginResult: LoginResult) {
                        getFacebookUserInfo()
                        session?.saveSession(LaguSession.KEY_IS_AUTHENTICATED, true)
                        session?.saveSession(LaguSession.KEY_IS_LOGIN_VIA_FACEBOOK, true)
                        finish()
                        val i = Intent(applicationContext, MainActivity::class.java)
                        startActivity(i)
                    }

                    override fun onCancel() {}

                    override fun onError(exception: FacebookException) {
                        Toast.makeText(this@LoginActivity, "Error login via Facebook", Toast.LENGTH_SHORT).show()
                    }
                })
    }

    private fun getFacebookUserInfo() {
        accessTokenTracker = object : AccessTokenTracker() {
            override fun onCurrentAccessTokenChanged(
                    oldAccessToken: AccessToken,
                    currentAccessToken: AccessToken) {
            }
        }
        accessToken = AccessToken.getCurrentAccessToken()
        val request = GraphRequest.newMeRequest(accessToken) { `object`, response ->
            try {
                Log.d("FacebookUserInfo", "Result " + response.jsonObject.toString())
                val data = response.jsonObject
                val userInfo = FacebookUserInfo(data.getString("name"), data.getJSONObject("picture").getJSONObject("data").getString("url"),
                        data.getString("id"), data.getString("email"))
                Toast.makeText(this@LoginActivity, "Hello " + userInfo.name, Toast.LENGTH_SHORT).show()

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        val parameters = Bundle()
        parameters.putString("fields", "id,name,email,picture.type(large)")
        request.parameters = parameters
        request.executeAsync()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        callbackManager!!.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)
            Toast.makeText(this@LoginActivity, "Hello " + account.displayName, Toast.LENGTH_SHORT).show()
            session?.signInGoogle(account.displayName!!, account.email!!, account.photoUrl.toString())
            session?.saveSession(LaguSession.KEY_IS_AUTHENTICATED, true)
            session?.saveSession(LaguSession.KEY_IS_LOGIN_VIA_Google, true)
            val i = Intent(this, MainActivity::class.java)
            startActivity(i)
        } catch (e: ApiException) {
            Toast.makeText(this@LoginActivity, "Error login via Google " + e.cause, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onClick(view: View?) {
        val id = view!!.id
        if (id == R.id.btn_login_google) {
            val signInIntent = mGoogleSignInClient!!.getSignInIntent()
            startActivityForResult(signInIntent, RC_SIGN_IN)
        } else if (id == R.id.btn_login_fb) {
            btn_login_fb_1.performClick()
        }
    }

}
