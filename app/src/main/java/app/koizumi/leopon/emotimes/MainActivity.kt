package app.koizumi.leopon.emotimes

import android.app.Activity
import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

const val MY_REQUEST_CODE = 0

class MainActivity : AppCompatActivity() {

    val realm: Realm = Realm.getDefaultInstance()

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        val memo: Memo? = read() //既に保存されているメモのデータを取得してmemoという変数に代入→ここでは表示しないし更新もしないから、取得しなくていい


        forgetButton.setOnClickListener {
            val content = contentEditText.text.toString()
//            save(content)

            val rendaPage = Intent(this,RendaActivity::class.java)
            rendaPage.putExtra("naiyo",content)//naiyoというタグで、contentを送る

//            startActivity(rendaPage)

            startActivityForResult(rendaPage,MY_REQUEST_CODE)
//            finish()
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

//    今回はいらない？（ここではreadしない、読み込まないから）
//    fun read(): Memo? { //?→nullもok
//        //一つのデータを扱うからfirst(最初の1つを取り出す）, リストを取得するfindAllっていうのもある
//        return realm.where(Memo::class.java).findFirst()
//    }

    //        保存する処理
//    fun save(createdAt:Date, content: String){
    @RequiresApi(Build.VERSION_CODES.N)
    fun save(content: String, tapnum: Int){
//
//        val memo = read()//保存されているメモを取得する→しない
//        この中でDB操作
        realm.executeTransaction{
//            if (memo!=null){
////                メモを更新
////                memo.createdAt = createdAt
//                memo.content = content
//            } else {
//             メモの新規作成
//            val newMemo = it.createObject(Memo::class.java)//it=Realmなので、realmにobjectをcreate
            val newMemo = it.createObject(Memo::class.java, UUID.randomUUID().toString())//1つ目の引数にモデル、2つ目の引数に@PrimaryKeyで設定したプロパティの値を代入して生成します。(これがないと落ちます)

//               newMemo.createdAt = createdAt
            newMemo.content = content
            //    contentの0-10文字目
            if (newMemo.content.length > 10) {
                newMemo.shortContent = content.substring(0, 10) + "..."
            }else{
                newMemo.shortContent = newMemo.content
            }

//            val contentLen = newMemo.content.length
            val contentLen: Int = contentEditText.lineCount
//            val contentLen: Int = content.lineCount

            Log.d("GYOUSUU",contentLen.toString())

            var displayAt: Calendar = Calendar.getInstance()
            val df = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.JAPANESE)

//            var displayAt :Date = Date(System.currentTimeMillis())
            displayAt.time = Date()//自動で現在時刻がはいる
            Log.d("KEISAN MAE",df.format(displayAt.time))

//            displayAt.add(Calendar.DATE,2)
            val addDays = contentLen + tapnum/2
//            displayAt.add(Calendar.MINUTE,addDays)//文字数(分)を現在時刻に追加
            displayAt.add(Calendar.DATE,addDays)//文字数(分)を現在時刻に追加

            Log.d("KEISAN GO",df.format(displayAt.time))
            Log.d("KEISAN GOGO",displayAt.toString())

            newMemo.displayAt = displayAt.time

            contentEditText.text.clear()

//             本番動作時はコメントアウト(?)
//            Snackbar.make(container,"わすれました！", Snackbar.LENGTH_SHORT).show()
//            Toast.makeText(this, "${newMemo.shortContent}を, ${addDays}日(分)間忘れるよ", Toast.LENGTH_LONG).show()

        }
    }

//  SecondActivity を閉じた際に情報を受け取るためのメソッドです。変数 data には、返された値が格納されています。
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == MY_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            // リクエストコードが一致して、かつ、アクティビティが正常に終了していた場合、受け取った値を表示
            val received = data!!
//            Toast.makeText(this, "${received.extras?.get("tapped")}, ${received.extras?.get("naiyo")}", Toast.LENGTH_LONG).show()

            val content = received.getStringExtra("naiyo")
            val tapnum = received.getIntExtra("tapped",0)
//            intent.extras.get("number")???でもいけるっぽい

            save(content,tapnum)
        }
    }
}
