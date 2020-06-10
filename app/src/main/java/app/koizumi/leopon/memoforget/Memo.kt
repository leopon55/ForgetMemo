package app.koizumi.leopon.memoforget

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.Required
import java.util.*

//Realmを使うときはopen class
open class Memo(
    @PrimaryKey
    open var id:String = UUID.randomUUID().toString(),
// @PrimaryKeyというアノテーションをつけると一意なプロパティとして定義することができ、その値をUUID.randomUUID().toString()とすると一意なIDを生成してくれます。(他にもAutoIncrementする方法もあります。)
//    @Required
    open var content:String ="No Content",

    open var shortContent:String = "short...",//ここでcontent.substring(0,10)すると、contentの初期値が使われちゃう
//    @Required
//    open var createdAt: String =""
    open var createdAt: Date = Date(System.currentTimeMillis()),//引数がない場合、現在時刻がデフォルト値になる
    open var displayAt: Date = Date(System.currentTimeMillis())//再表示するとき
//    open var displayAt: String = Date(System.currentTimeMillis()).toString()//再表示するとき

) : RealmObject() //Realmで保存したい方にはこれを書く決まり
