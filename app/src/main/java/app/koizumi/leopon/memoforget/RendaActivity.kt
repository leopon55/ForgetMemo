package app.koizumi.leopon.memoforget

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.media.SoundPool
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.provider.MediaStore
import android.util.Log
import android.util.TypedValue
import android.view.View
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
            endButton.isVisible = true

            if (opaque<2){
                startButton.isVisible = false
            }else{
                startButton.isVisible = false
            }

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

    private lateinit var mSoundPool: SoundPool //SoundPoolの変数を宣言
    //    10秒までの短い音楽を扱うなら、SoundPool。
    private lateinit var mSoundID: Array<Int?>
    private val mSoundResource = arrayOf(
        R.raw.puyon,
        R.raw.tear_papers
    )

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
//                checkFlag = 1
                tapAction()
            }
//            効果音を巻き戻す、再度タップされても最初から流せる
//            tapSound.seekTo(0)
//            tapSound.start()
        }

        rightTapButton.setOnClickListener {
//            if (checkFlag == 1){
//                tapCount += 1
//                checkFlag = 0
//            }
//            countText.text = tapCount.toString()
            if (second < 10){
              tapAction()
            }
        }

        endButton.setOnClickListener {
            if (tapCount==0){
                Toast.makeText(applicationContext, "1回は忘れましょう？", Toast.LENGTH_SHORT).show()
            }else{
                paper()
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



    override fun onResume() {
        super.onResume()

        val audioAttributes = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_MEDIA)
            .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
//            .setMaxStreams(mSoundResource.size)
            .build()

        mSoundPool = SoundPool.Builder()
            .setAudioAttributes(audioAttributes)
            .setMaxStreams(mSoundResource.size)
            .build()

        mSoundID = arrayOfNulls(mSoundResource.size)

        for (i in 0 until mSoundResource.size) {
            mSoundID[i] = mSoundPool.load(applicationContext, mSoundResource[i], 0)
        }
    }

    override fun onDestroy(){
        super.onDestroy()
        mSoundPool.release()
    }


    private var opaque:Int = 195//C2000000,76%の不透明度のはず
    private var color:Int = 0//C2000000,76%の不透明度のはず

    fun tapAction(){
        puyon()
        tapCount += 1
        countText.text = tapCount.toString()

        if (opaque>=2) {
            opaque -= 2
            color += 2
            contentText.setTextColor(Color.argb(opaque, color, color, color))
            Log.d("opaque",opaque.toString())
            Log.d("color",color.toString())
        }

////        文字のサイズ
////        端末によって変わる（spの計算しないといけない）ので、あとで
//        Log.d("文字のsize",contentText.textSize.toString())
//        var size = contentText.textSize
//        Log.d("size",size.toString())
//        Log.d("文字のsize",contentText.textSize.toString())
//
//        size /= 2//多分spの計算で、2倍になるけど、2倍になっちゃうから、とりま割る2
//        Log.d("size",size.toString())
//
//        size -= 1
////        size = (size * 0.9).toFloat()
//        Log.d("size",size.toString())
//
////        contentText.textSize = "a"//(contentText.textSize -1 )
//        contentText.setTextSize(TypedValue.COMPLEX_UNIT_DIP, size)
//        Log.d("size",size.toString())
//        Log.d("文字のsize",contentText.textSize.toString())
    }

    fun puyon(){
        if(mSoundID[0] != null) {
            mSoundPool.play(mSoundID[0] as Int, 1.0F,1.0F,0,0,1.0F)
        }
    }

    fun paper(){
        if(mSoundID[1] != null) {
            mSoundPool.play(mSoundID[1] as Int, 1.0F,1.0F,0,0,1.0F)
        }
    }
}
