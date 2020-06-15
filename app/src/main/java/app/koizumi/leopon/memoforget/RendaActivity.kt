package app.koizumi.leopon.memoforget

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Toast
import androidx.core.view.isVisible
import kotlinx.android.synthetic.main.activity_renda.*

class RendaActivity : AppCompatActivity() {

    var tapCount = 0
    var checkFlag = 0
    var second = 10

    var timer: CountDownTimer = object : CountDownTimer(10000,1000){
        //タイマーが終了した時に呼ばれる
        override fun onFinish() {
            startButton.text = "コンティニュー"
            startButton.isVisible = true
            endButton.isVisible = true

            rightTapButton.setBackgroundResource(R.drawable.background_rounded_corners_gray)
            leftTapButton.setBackgroundResource(R.drawable.background_rounded_corners_gray)
//            残り時間をリセット
            second = 10
//            tapCount = 0//継続カウントでもよい気もする
            timeText.text = second.toString()
        }
        //      1秒ごとに呼ばれて動く
        override fun onTick(millisUntilFinished: Long) {
            rightTapButton.setBackgroundResource(R.drawable.background_rounded_corners)
            leftTapButton.setBackgroundResource(R.drawable.background_rounded_corners)
            second -= 1
            timeText.text = second.toString()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_renda)

        val content = intent.getStringExtra("naiyo")//naiyoというタグのcontentを受け取る
//        val content = intent.extras.get("naiyo")
        contentText.text = content

        endButton.isVisible = false

        startButton.setOnClickListener {
            countText.text = tapCount.toString()
            startButton.isVisible = false

//            タイマーを開始する
            timer.start()
        }

        leftTapButton.setOnClickListener {
            if (second < 10){
                checkFlag = 1
            }
        }

        rightTapButton.setOnClickListener {
            if (checkFlag == 1){
                tapCount += 1
                checkFlag = 0
            }
            countText.text = tapCount.toString()
        }

        endButton.setOnClickListener {
            if (tapCount==0){
                Toast.makeText(applicationContext, "1回は忘れましょう？", Toast.LENGTH_SHORT).show()
            }else{
//                val mainPage = Intent(this,MainActivity::class.java)
                val mainPage = Intent()
                mainPage.putExtra("tapped",tapCount)//naiyoというタグで、contentを送る
                mainPage.putExtra("naiyo",content)//naiyoというタグで、contentを送る

//                startActivity(mainPage)→メインアクティビティをfinishしていないから、スタートじゃない
                setResult(Activity.RESULT_OK, mainPage)

                finish()
            }
        }
    }
}
