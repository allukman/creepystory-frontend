package id.smartech.creepystory.activity.register

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import id.smartech.creepystory.R
import id.smartech.creepystory.activity.login.LoginActivity
import id.smartech.creepystory.activity.login.LoginViewModel
import id.smartech.creepystory.activity.maincontent.MainActivity
import id.smartech.creepystory.base.BaseActivity
import id.smartech.creepystory.databinding.ActivityRegisterBinding
import id.smartech.creepystory.util.ValidateAccount
import id.smartech.creepystory.util.ValidateAccount.Companion.valEmail
import id.smartech.creepystory.util.ValidateAccount.Companion.valName
import id.smartech.creepystory.util.ValidateAccount.Companion.valPassword
import id.smartech.creepystory.util.ValidateAccount.Companion.valPhoneNumber

class RegisterActivity : BaseActivity<ActivityRegisterBinding>() {
    private lateinit var viewModel: RegisterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        setLayout = R.layout.activity_register
        super.onCreate(savedInstanceState)

        setViewModel()
        subscribeLiveData()
        initTextWatcher()
        onClick()

    }

    private fun setViewModel() {
        viewModel = ViewModelProvider(this).get(RegisterViewModel::class.java)
        viewModel.setService(createApi(this))
        viewModel.setSharedPref(sharedPref)
    }

    private fun subscribeLiveData() {
        viewModel.isLoadingLiveData.observe(this) {
            bind.progressBar.visibility = View.VISIBLE
            bind.btnRegister.visibility = View.GONE
            bind.tvTextLogin.visibility = View.GONE
            bind.tvLogin.visibility = View.GONE
        }

        viewModel.onSuccessLiveData.observe(this) {
            if (it) {
                bind.progressBar.visibility = View.GONE
                bind.btnRegister.visibility = View.VISIBLE
                bind.tvTextLogin.visibility = View.VISIBLE
                bind.tvLogin.visibility = View.VISIBLE

                intents<LoginActivity>(this)
                noticeToast("Register Account Successfully")
                this.finish()
            } else {
                bind.progressBar.visibility = View.GONE
                bind.btnRegister.visibility = View.VISIBLE
                bind.tvTextLogin.visibility = View.VISIBLE
                bind.tvLogin.visibility = View.VISIBLE
            }
        }

        viewModel.onFailLiveData.observe(this) {
            noticeToast(it)
        }
    }

    private fun onClick() {
        bind.btnRegister.setOnClickListener {
            val email = bind.etEmail.text.toString()
            val password = bind.etPassword.text.toString()

            when {
                !valEmail(bind.etInputEmail, bind.etEmail) -> {
                }
                !valPassword(bind.etInputPassword, bind.etPassword) -> {
                }
                else -> {
                    viewModel.RegisterRequest(
                            email = email,
                            password = password
                    )
                }
            }
        }

        bind.tvLogin.setOnClickListener {
            intents<LoginActivity>(this)
            finish()
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