package app.koizumi.leopon.memoforget

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.realm.Realm
import io.realm.RealmResults
import io.realm.Sort
import kotlinx.android.synthetic.main.activity_list_view.*
import java.util.*

class ListViewActivity : AppCompatActivity() {

    private val realm: Realm by lazy {
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

        val adapter = MemoAdapter(this, memoList, true)

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
        backButton.setOnClickListener {
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
//            memo.createdAt = createdAt
        }
    }

    fun readAll(): RealmResults<Memo> {
        return realm.where(Memo::class.java).findAll().sort("createdAt", Sort.ASCENDING)
    }

}