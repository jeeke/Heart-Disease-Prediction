package me.gyanesh.hdp

import android.app.Application
import androidx.core.content.res.ResourcesCompat
import es.dmoral.toasty.Toasty
import me.gyanesh.hdp.data.db.HDPDb
import me.gyanesh.hdp.data.network.HDPApi
import me.gyanesh.hdp.data.network.HDPRepo
import me.gyanesh.hdp.data.network.NetworkConnectionInterceptor
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.singleton
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance


class HDPApp : Application(), KodeinAware {

    override fun onCreate() {
        super.onCreate()
        mInstance = this

        configureToasty()
    }

    override val kodein = Kodein.lazy {
        import(androidXModule(this@HDPApp))
        bind() from singleton { HDPDb.create(this@HDPApp) }
        bind() from singleton { NetworkConnectionInterceptor(applicationContext) }
        bind() from singleton { HDPApi(instance()) }
        bind() from singleton { HDPRepo(instance(), instance()) }

    //        bind() from singleton { NetworkConnectionInterceptor(applicationContext) }

//        bind() from provider { DiscussionRepository(instance()) }
    }

    private fun configureToasty() {
        ResourcesCompat.getFont(this, R.font.nunito)?.let {
            Toasty.Config.getInstance()
                .setToastTypeface(it)
                .allowQueue(false) // optional (prevents several Toastys from queuing)
                .apply()
        } // required
    }

    companion object {
        private lateinit var mInstance: HDPApp

        @Synchronized
        fun getInstance(): HDPApp {
            return mInstance
        }
    }

}