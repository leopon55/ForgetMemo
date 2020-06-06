package app.koizumi.leopon.memoforget

import java.text.SimpleDateFormat
import java.util.*

class TimeManage {
//    //現在時刻の取得
//    //Dateを作成すると現在日時が入るし、CalenderをgetInstanceでも現在日時が入る
//    val date: Date = Date()
//    val calendar: Calendar = Calendar.getInstance(TimeZone.getTimeZone("Asia/Tokyo"), Locale.JAPAN);
//
//    //Dateに格納された時刻をフォーマットに従って出力
//    val test1: String = android.text.format.DateFormat.format("yyyy/MM/dd kk:mm:ss", date).toString()
//
//    //CalendarからDate取得する方法
//    val date1: Date = calendar.time
//
//    return (date1)

////CalendarにDateを設定する方法
//    calendar.time = date
//
////日付の作成　数字からの日付の作成 月は0～11なのに注意
//    calendar.set(2018, 2, 24, 13, 1, 1)
//
//    //日付のの作成　文字列から日付の作成 0の省略が可能
//    val date2: Date = SimpleDateFormat("yyyy/MM/dd kk:mm:ss", Locale.JAPAN).parse("2018/03/24 14:01:01")
//    val date3: Date = SimpleDateFormat("yyyy/MM/dd kk:mm:ss", Locale.JAPAN).parse("2018/3/24 14:1:1")
//
//    //文字列が日付に変換できるかどうか判断する
//    val isDate: Boolean =
//        try {
//            SimpleDateFormat("yyyy/MM/dd kk:mm:ss", Locale.JAPAN).also { it.isLenient = false }.parse("2019/40/24 14:01:01")
//            true
//        } catch (e: Exception) {
//            false
//        }
//
//    //文字列が日付に変換できるかどうか判断する 関数にしておくと便利
//    fun SimpleDateFormat.isDate(dateString: String): Boolean {
//        return try {
//            this.isLenient = false
//            this.parse(dateString)
//            true
//        } catch (e: Exception) {
//            false
//        }
//    }
//    val isDate2: Boolean = SimpleDateFormat("yyyy/MM/dd kk:mm:ss", Locale.JAPAN).isDate("2019/12/24 14:01:01")
//
//    //年の取得
//    val year: Int = calendar.get(Calendar.YEAR)
//
////月の取得 月は0～11なのに注意 1月は0
//    calendar.get(Calendar.MONTH) == Calendar.MARCH
//
//    //日の取得
//    val dat: Int = calendar.get(Calendar.DAY_OF_MONTH)
//
////曜日の取得 日～土:1～7
//    calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY
//
////午前午後の取得 0:AM 1:PM
//    calendar.get(Calendar.AM_PM) == Calendar.AM
//
//    //時間の取得 12時間表示
//    val hour = calendar.get(Calendar.HOUR)
//
//    //時間の取得 24時間表示
//    val hour24 = calendar.get(Calendar.HOUR_OF_DAY)
//
//    //分の取得　
//    val min = calendar.get(Calendar.MINUTE)
//
//    //第2週の火曜日みたいな数値　日曜日を起点に7日周期の曜日数
//    val weekNum1: Int = calendar.get(Calendar.WEEK_OF_MONTH)
//
//    //第2火曜日みたいな数値　当月1日を起点に7日周期の曜日数
//    val weekNum2: Int = calendar.get(Calendar.DAY_OF_WEEK_IN_MONTH)
//
//    //一年で何日目か取得
//    val daysInYear: Int = calendar.get(Calendar.DAY_OF_YEAR)
//
//    //日付や時間の足し算引き算
//    val date4: Date = calendar.also { it.add(Calendar.DATE, 30) }.time
//    val date5: Date = calendar.also {
//        it.add(Calendar.HOUR, 10)
//        it.add(Calendar.MINUTE, 10)
//        it.add(Calendar.SECOND, -1)
//    }.time
//
////日時の差分の計算方法
////日付計算をするときはミリセカンドまで比較するのでClearしといたほうがいい
////差分は分だけの差分か秒も含めた差分かで答えが違ってくるのでしっかり実装したほうがいいかも
//
//    val calendar1: Calendar = Calendar.getInstance(TimeZone.getTimeZone("Asia/Tokyo"), Locale.JAPAN).also { it.clear() }
//    val calendar2: Calendar = Calendar.getInstance(TimeZone.getTimeZone("Asia/Tokyo"), Locale.JAPAN).also { it.clear() }
//
////秒の差分
//    calendar1.set(2001, 0, 1, 1, 1, 2)
//    calendar2.set(2001, 0, 1, 1, 1, 1)
//    val diff1: Int = ((calendar1.timeInMillis - calendar2.timeInMillis) / 1000).toInt()
//
////分の差分
//    calendar1.set(2001, 0, 1, 1, 6, 1)
//    calendar2.set(2001, 0, 1, 1, 1, 1)
//    val diff2: Int = ((calendar1.timeInMillis - calendar2.timeInMillis) / (1000 * 60)).toInt()
//
////時間の差分
//    calendar1.set(2001, 0, 1, 4, 1, 1)
//    calendar2.set(2001, 0, 1, 1, 1, 1)
//    val diff3: Int = ((calendar1.timeInMillis - calendar2.timeInMillis) / (1000 * 60 * 60)).toInt()
//
////日　日の差分は自作の関数を作るらしい
//    calendar1.set(2001, 0, 5, 1, 1, 1)
//    calendar2.set(2001, 0, 1, 1, 1, 1)
//    val diff4: Int = ((calendar1.timeInMillis - calendar2.timeInMillis) / (1000 * 60 * 60 * 24)).toInt()
//
////月　月の場合は自作の関数を作るらしい
//    calendar1.set(2001, 10, 5, 1, 1, 1)
//    calendar2.set(2011, 6, 1, 1, 1, 1)
//    fun calcMonthDiff(calendar1: Calendar, calendar2: Calendar): Int {
//        val myCalendar1: Calendar = Calendar.getInstance().also {
//            it.clear()
//            it.set(Calendar.YEAR, calendar1.get(Calendar.YEAR))
//            it.set(Calendar.MONTH, calendar1.get(Calendar.MONTH))
//        }
//        val myCalendar2: Calendar = Calendar.getInstance().also {
//            it.clear()
//            it.set(Calendar.YEAR, calendar2.get(Calendar.YEAR))
//            it.set(Calendar.MONTH, calendar2.get(Calendar.MONTH))
//        }
//        var count: Int = 0
//        while (myCalendar1.compareTo(myCalendar2) != 0) {
//            myCalendar1.add(Calendar.MONTH, myCalendar1.compareTo(myCalendar2) * -1)
//            count++
//        }
//        return count
//    }
}