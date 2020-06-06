package app.koizumi.leopon.memoforget

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.Required
import java.util.*

//Realmを使うときはopen class
open class Memo(
    @PrimaryKey
    open var id: String = UUID.randomUUID().toString(),
// @PrimaryKeyというアノテーションをつけると一意なプロパティとして定義することができ、その値をUUID.randomUUID().toString()とすると一意なIDを生成してくれます。(他にもAutoIncrementする方法もあります。)
    @Required
    open var content:String ="",
    @Required
//    open var createdAt: String =""
    open var createdAt: Date = Date(System.currentTimeMillis())//引数がない場合、現在時刻がデフォルト値になる
) : RealmObject() //Realmで保存したい方にはこれを書く決まり
