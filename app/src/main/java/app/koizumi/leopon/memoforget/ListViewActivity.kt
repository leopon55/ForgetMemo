package app.koizumi.leopon.memoforget

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import io.realm.Realm
import io.realm.RealmResults
import io.realm.Sort
import kotlinx.android.synthetic.main.activity_list_view.*
import java.util.*

class ListViewActivity : AppCompatActivity() {

    private val realm: Realm by lazy {//lazyという委譲プロパティを使って遅延初期化,この変数が最初に参照されたときに{}内の値をsetして初期化します。
        Realm.getDefaultInstance()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_view)

        val memoList = readAll()

        // タスクリストが空だったときにダミーデータを生成する
        if (memoList.isEmpty()) {
            createDummyData()
        }

//        val adapter = MemoAdapter(this, memoList, true)
        val detailPage = Intent(this,DetailActivity::class.java)

        // クリック時の処理：　詳細画面へ遷移
        val adapter =
            MemoAdapter(this, memoList, object:MemoAdapter.OnItemClickListener {
                override fun onItemClick(item: Memo) {
//                    Toast.makeText(applicationContext, item.content + "を削除しました", Toast.LENGTH_SHORT).show()
//                    delete(item.id)
                    detailPage.putExtra("memoId", item.id.toString())//idを持たせる
                    startActivity(detailPage)
                    finish()
                }
            }, true)
//        var recyclerView = findViewById(R.id.recyclerView) as RecyclerView//https://stackoverflow.com/questions/48459010/illegalstateexception-recyclerview-is-null-inside-of-fragment-within-navigation

        if (recyclerView != null) {
            recyclerView.setHasFixedSize(true)
            recyclerView.layoutManager = LinearLayoutManager(this)
            recyclerView.adapter = adapter
        }
//        else{
//            recyclerView.setHasFixedSize(true)
//            recyclerView.layoutManager = LinearLayoutManager(this)
//            recyclerView.adapter = adapter
//        }
        backMainButton.setOnClickListener {
            val mainInputPage = Intent(this, MainActivity::class.java)
            startActivity(mainInputPage)
            finish()
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }

    fun createDummyData() {
        for (i in 1..2) {
            var m = i*2-1
            var n =i*2
            create("あんなこと $m")
            create("こんなこと $n")
        }
    }

    fun create(content: String) {
        realm.executeTransaction {
            val memo = it.createObject(Memo::class.java, UUID.randomUUID().toString())
//            memo.imageId = imageId
            memo.content = content
            //    contentの0-10文字目を表示

            if (memo.content.length > 10) {
                memo.shortContent = content.substring(0, 10) + "..."
            }else{
                memo.shortContent = memo.content
            }
        }
    }

    fun readAll(): RealmResults<Memo> {
        return realm.where(Memo::class.java).findAll().sort("createdAt", Sort.ASCENDING)
    }

    fun update(id: String, content: String) {
        realm.executeTransaction {
            val memo = realm.where(Memo::class.java).equalTo("id", id).findFirst()
                ?: return@executeTransaction
            memo.content = content
        }
    }

    fun update(memo: Memo, content: String) {
        realm.executeTransaction {
            memo.content = content
        }
    }

    fun delete(id: String) {
        realm.executeTransaction {
            val memo = realm.where(Memo::class.java).equalTo("id", id).findFirst()
                ?: return@executeTransaction
            memo.deleteFromRealm()
        }
    }

    fun delete(memo: Memo) {
        realm.executeTransaction {
            memo.deleteFromRealm()
        }
    }

    fun deleteAll() {
        realm.executeTransaction {
            realm.deleteAll()
        }
    }

}