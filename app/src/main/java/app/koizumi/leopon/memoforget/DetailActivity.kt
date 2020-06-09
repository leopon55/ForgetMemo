package app.koizumi.leopon.memoforget

import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import io.realm.Realm
import io.realm.RealmResults
import io.realm.Sort
import kotlinx.android.synthetic.main.activity_detail.*
import java.nio.file.Files.delete
import java.util.*

class DetailActivity : AppCompatActivity() {

    private val realm: Realm by lazy {//lazyという委譲プロパティを使って遅延初期化,この変数が最初に参照されたときに{}内の値をsetして初期化します。
        Realm.getDefaultInstance()
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val memoId = intent.getStringExtra("memoId")

        val memoDetail = realm.where(Memo::class.java).equalTo("id", memoId).findFirst()

        if (memoDetail != null) {
            detailTextView.text = memoDetail.content
            dateTextView.text = SimpleDateFormat("yyyy/MM/dd HH:mm", Locale.JAPANESE).format(memoDetail.createdAt)
        }

        val listPage = Intent(this, ListViewActivity::class.java)
        backListButton.setOnClickListener {
            startActivity(listPage)
            finish()
        }

//        すぐに更新するならアダプターらしい
//        val adapter =
//            MemoAdapter(this, memoDetail, object:MemoAdapter.OnItemClickListener {
//                override fun onItemClick(item: Memo) {
//                    // クリック時の処理
//                    Toast.makeText(applicationContext, item.content + "を削除しました", Toast.LENGTH_SHORT).show()
//                    delete(item.id)
//                }
//            }, true)

//      クリック時の処理
        deleteButton.setOnClickListener {
            if (memoDetail != null) {
                Toast.makeText(applicationContext, memoDetail.shortContent + "を削除しました", Toast.LENGTH_LONG).show()
                delete(memoDetail)
                startActivity(listPage)
                finish()//画面閉じる???閉じないほうがいいか？
            }
        }

    }

//    fun read(memoId:String): RealmResults<Memo> {
//        return realm.where(Memo::class.java).equalTo("id", memoId).findFirst()
//    }


    fun delete(memo: Memo) {
        realm.executeTransaction {
//            val memo = realm.where(Memo::class.java).equalTo("id", id).findFirst()
//                ?: return@executeTransaction
            memo.deleteFromRealm()
        }
    }
}
