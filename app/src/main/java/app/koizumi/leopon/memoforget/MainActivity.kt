package app.koizumi.leopon.memoforget

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    val realm: Realm = Realm.getDefaultInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val memo: Memo? = read() //既に保存されているメモのデータを取得してmemoという変数に代入


        forgetButton.setOnClickListener {
            val date = dateEditText.text.toString()
            val content = contentEditText.text.toString()
//            val combine = date + content
            save(date,content)
        }

        rememberButton.setOnClickListener {
            val listPage = Intent(this,ListViewActivity::class.java)

//            val listUpMemo = read()
//                読み込んだメモをセット?→putExtraは型の指定が厳しい
//            listPage.putExtra("memo",listUpMemo)

//                判定を表示する画面を起動
            startActivity((listPage))
            finish()

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }

//    今回はいらない？
    fun read(): Memo? { //?→nullもok
        //一つのデータを扱うからfirst(最初の1つを取り出す）, リストを取得するfindAllっていうのもある
        return realm.where(Memo::class.java).findFirst()
    }

    //        保存する処理
//    fun save(createdAt:Date, content: String){
//    fun save(combine: String){
    fun save(createdAt:String, content: String){

        val memo = read()//保存されているメモを取得する

//        この中でDB操作
        realm.executeTransaction{
            if (memo!=null){
//                メモを更新
                memo.createdAt = createdAt
                memo.content = content
            } else {
//                メモの新規作成
                val newMemo = it.createObject(Memo::class.java)//it=Realmなので、realmにobjectをcreate
                newMemo.createdAt = createdAt
                newMemo.content = content
            }

            // 表示するtext, 表示する時間
            Snackbar.make(container,"保存しました！", Snackbar.LENGTH_SHORT).show()
        }
    }
}
