package app.koizumi.leopon.memoforget

import android.app.Application
import io.realm.Realm
import io.realm.RealmConfiguration

class RealmMemoApplication : Application() {
    override fun onCreate(){
        super.onCreate()

//        Realmの初期化
        Realm.init(this)
        val realmConfig = RealmConfiguration.Builder()
            .deleteRealmIfMigrationNeeded()
            .build()
        Realm.setDefaultConfiguration(realmConfig)
    }
}