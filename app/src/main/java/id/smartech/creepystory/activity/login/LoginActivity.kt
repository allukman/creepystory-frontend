package id.smartech.creepystory.activity.login

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import id.smartech.creepystory.R
import id.smartech.creepystory.activity.maincontent.MainActivity
import id.smartech.creepystory.base.BaseActivity
import id.smartech.creepystory.databinding.ActivityLoginBinding
import id.smartech.creepystory.util.ValidateAccount.Companion.valEmail
import id.smartech.creepystory.util.ValidateAccount.Companion.valPassword

class LoginActivity : BaseActivity<ActivityLoginBinding>() {
    private lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        setLayout = R.layout.activity_login
        super.onCreate(savedInstanceState)

        setViewModel()
        subscribeLiveData()
        initTextWatcher()
        googleLogin()

        bind.btnLogin.setOnClickListener {
            val email = bind.etEmail.text.toString()
            val password = bind.etPassword.text.toString()

            when {
                !valEmail(bind.etInputEmail, bind.etEmail) -> {
                }
                !valPassword(bind.etInputPassword, bind.etPassword) -> {
                }
                else -> {
                    viewModel.loginRequest(
                            email = email,
                            password = password
                    )
                }
            }
        }



    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)

            Toast.makeText(this, account?.displayName.toString(), Toast.LENGTH_SHORT ).show()
        } catch (e: ApiException) {

        }
    }

    private fun setViewModel() {
        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        viewModel.setService(createApi(this))
        viewModel.setSharedPref(sharedPref)
    }

    private fun subscribeLiveData() {
        viewModel.isLoadingLiveData.observe(this@LoginActivity) {
            bind.btnLogin.visibility = View.GONE
            bind.progressBar.visibility = View.VISIBLE
            bind.tvOr.visibility = View.GONE
            bind.signInButton.visibility = View.GONE
        }

        viewModel.onSuccessLiveData.observe(this@LoginActivity) {
            if (it) {
                bind.progressBar.visibility = View.GONE
                bind.btnLogin.visibility = View.VISIBLE
                bind.tvOr.visibility = View.VISIBLE
                bind.signInButton.visibility = View.VISIBLE

                intents<MainActivity>(this@LoginActivity)
                this@LoginActivity.finish()
            } else {
                bind.progressBar.visibility = View.GONE
                bind.btnLogin.visibility = View.VISIBLE
                bind.tvOr.visibility = View.VISIBLE
                bind.signInButton.visibility = View.VISIBLE
            }
        }

        viewModel.onFailLiveData.observe(this@LoginActivity) {
            noticeToast(it)
        }
    }

    private fun googleLogin() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build()

        val mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        bind.signInButton.setSize(SignInButton.SIZE_STANDARD)
        bind.signInButton.setOnClickListener {
            val signInIntent = mGoogleSignInClient.signInIntent
            startActivityForResult(signInIntent, RC_SIGN_IN)

        }

        val acct = GoogleSignIn.getLastSignedInAccount(this)
        if (acct != null) {
            val personName = acct.displayName
            val personGivenName = acct.givenName
            val personFamilyName = acct.familyName
            val personEmail = acct.email
            val personId = acct.id

            noticeToast(personEmail.toString())
        }
    }

    private fun initTextWatcher() {
        bind.etEmail.addTextChangedListener(MyTextWatcher(bind.etEmail))
        bind.etPassword.addTextChangedListener(MyTextWatcher(bind.etPassword))
    }

    inner class MyTextWatcher(private val view: View) : TextWatcher {
        override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
        override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
        override fun afterTextChanged(editable: Editable) {
            when (view.id) {
                R.id.et_email -> valEmail(bind.etInputEmail, bind.etEmail)
                R.id.et_password -> valPassword(bind.etInputPassword, bind.etPassword)
            }
        }
    }

}