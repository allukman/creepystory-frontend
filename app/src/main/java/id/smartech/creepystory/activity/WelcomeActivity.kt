package id.smartech.creepystory.activity

import android.os.Bundle
import id.smartech.creepystory.R
import id.smartech.creepystory.activity.login.LoginActivity
import id.smartech.creepystory.activity.register.RegisterActivity
import id.smartech.creepystory.base.BaseActivity
import id.smartech.creepystory.databinding.ActivityWelcomeBinding

class WelcomeActivity : BaseActivity<ActivityWelcomeBinding>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        setLayout = R.layout.activity_welcome
        super.onCreate(savedInstanceState)

        bind.btnRegister.setOnClickListener {
            intents<RegisterActivity>(this)
            this.finish()
        }

        bind.tvLogin.setOnClickListener {
            intents<LoginActivity>(this)
            this.finish()
        }
    }
}