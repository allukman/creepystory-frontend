package id.smartech.creepystory.activity.onboard

import android.os.Bundle
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.core.view.get
import androidx.viewpager2.widget.ViewPager2
import id.smartech.creepystory.R
import id.smartech.creepystory.activity.WelcomeActivity
import id.smartech.creepystory.activity.login.LoginActivity
import id.smartech.creepystory.activity.register.RegisterActivity
import id.smartech.creepystory.base.BaseActivity
import id.smartech.creepystory.databinding.ActivityOnBoardBinding

class OnBoardActivity : BaseActivity<ActivityOnBoardBinding>() {
    private val introAdapter = IntroAdapter(
        listOf(
            IntroModel("Bloody Painter", "Don't be excited for tomorrow because there is no tomorrow", R.drawable.ic_devil_slide),
            IntroModel("SilentC", "Why must you try to hurt me, now look at what you made me do", R.drawable.ic_ghost),
            IntroModel("Puppetteer", "Every single second of loneliness brought something out of me", R.drawable.ic_jason),
            IntroModel("Bootleg Spongebob", "Some committed suicide, some just disappeared", R.drawable.ic_werewolf)
    )
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        setLayout = R.layout.activity_on_board
        super.onCreate(savedInstanceState)

        bind.onboardViewpager.adapter = introAdapter
        setupIndicators()
        setCurrentIndicator(0)
        bind.onboardViewpager.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {

            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                setCurrentIndicator(position)
            }
        })

        bind.btnStart.setOnClickListener {
            intents<WelcomeActivity>(this)
        }
    }

    private fun setupIndicators(){
        val indicators = arrayOfNulls<ImageView>(introAdapter.itemCount)
        val layoutParams: LinearLayout.LayoutParams =
            LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
        layoutParams.setMargins(8,0,8,0)
        for (i in indicators.indices) {
            indicators[i] = ImageView(applicationContext)
            indicators[i].apply {
                this?.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.indicator_inactive
                    )
                )
                this?.layoutParams = layoutParams
            }
            bind.container.addView(indicators[i])
        }
    }

    private fun setCurrentIndicator(index: Int){
        val childCount = bind.container.childCount
        for (i in 0 until childCount) {
            val imageView = bind.container[i] as ImageView
            if (i == index) {
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.indicator_active
                    )
                )
            } else {
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.indicator_inactive
                    )
                )
            }
        }
    }
}