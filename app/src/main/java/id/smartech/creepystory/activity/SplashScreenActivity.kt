package id.smartech.creepystory.activity

import android.os.Bundle
import android.os.Handler
import id.smartech.creepystory.R
import id.smartech.creepystory.activity.onboard.OnBoardActivity
import id.smartech.creepystory.base.BaseActivity
import id.smartech.creepystory.databinding.ActivitySplashScreenBinding

class SplashScreenActivity : BaseActivity<ActivitySplashScreenBinding>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        setLayout = R.layout.activity_splash_screen
        super.onCreate(savedInstanceState)

        Handler().postDelayed({
            intents<OnBoardActivity>(this)
            this.finish()
        }, 5000)
    }

}