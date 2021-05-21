package me.gyanesh.hdp.ui

import android.app.ProgressDialog
import android.os.Bundle
import com.akexorcist.localizationactivity.ui.LocalizationActivity
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import java.util.*

abstract class BaseActivity : LocalizationActivity(), KodeinAware {
    override val kodein by kodein()
    lateinit var progressBar: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        progressBar = ProgressDialog(this)
        currentLanguage = getCurrentLanguage().language
    }

    fun changeLanguage(lgCode: String?) {
        if (lgCode == "hi") setLanguage(lgCode)
        else setLanguage(Locale.ENGLISH)
    }

    //
    override fun onAfterLocaleChanged() {
        super.onAfterLocaleChanged()
        currentLanguage = getCurrentLanguage().language
    }

    companion object {
        lateinit var currentLanguage: String
    }
}