package app.koizumi.leopon.memoforget

import android.content.Context
import android.icu.text.SimpleDateFormat
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import io.realm.OrderedRealmCollection
import io.realm.RealmRecyclerViewAdapter
import kotlinx.android.synthetic.main.list_cell.view.*
import java.util.*

class MemoAdapter(
    private val context: Context,
    private var memoList: OrderedRealmCollection<Memo>?,
    private val autoUpdate: Boolean
) :
    RealmRecyclerViewAdapter<Memo, MemoAdapter.MemoViewHolder>(memoList, autoUpdate) {

    override fun getItemCount(): Int = memoList?.size ?: 0

    @RequiresApi(Build.VERSION_CODES.N)//いる？（自動入力）
    override fun onBindViewHolder(holder: MemoViewHolder, position: Int) {
        val memo: Memo = memoList?.get(position) ?: return

//        holder.container.setOnClickListener{
//            listener.onItemClick(task)
//        }
//        holder.imageView.setImageResource(memo.imageId)
        holder.contentTextView.text = memo.content
        holder.dateTextView.text =
            SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.JAPANESE).format(memo.createdAt)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): MemoViewHolder {
        val v = LayoutInflater.from(context).inflate(R.layout.list_cell, viewGroup, false)
        return MemoViewHolder(v)
    }

    class MemoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
//        val imageView: ImageView = view.imageView
        val contentTextView: TextView = view.contentTextView
        val dateTextView: TextView = view.dateTextView
    }

}