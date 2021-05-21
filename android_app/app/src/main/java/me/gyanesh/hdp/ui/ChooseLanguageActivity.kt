package me.gyanesh.hdp.ui

import android.content.Intent
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_choose_language.*
import me.gyanesh.hdp.R
import me.gyanesh.hdp.data.PreferenceProvider
import me.gyanesh.hdp.util.hide
import me.gyanesh.hdp.util.show

class ChooseLanguageActivity : BaseActivity() {

    companion object {
        var fromMainActivity = false
        var lang = ""
    }

    private var isFirstTime = true
    override fun onCreate(savedInstanceState: Bundle?) {
        lang = getCurrentLanguage().language
        isFirstTime = PreferenceProvider.isFirstTime()
        navigateAwayConditional()
        setTheme(R.style.AppTheme_Light)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_language)
        initButtons()
    }

    private fun navigateAwayConditional() {
        if (!isFirstTime && !fromMainActivity) {
            val i = Intent(this, HomeActivity::class.java)
            startActivity(i)
            finish()
        }
    }

    private fun initButtons() {
        btn_english?.setOnClickListener {
            lang = "en"
            ic_check_hindi.hide()
            ic_check_english.show()
        }
        btn_hindi?.setOnClickListener {
            lang = "hi"
            ic_check_hindi.show()
            ic_check_english.hide()
        }
        button_continue?.setOnClickListener {
            if (isFirstTime) {
                Intent(this, HomeActivity::class.java).also {
                    startActivity(it)
                    finish()
                }
                PreferenceProvider.onOnboardingComplete()
            } else finish()
            changeLanguage(lang)
        }
        if (getCurrentLanguage().language == "hi") btn_hindi.callOnClick()
    }
}