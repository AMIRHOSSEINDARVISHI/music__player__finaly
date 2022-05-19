package code.with.cal.music__player__finaly

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import code.with.cal.music__player__finaly.R
import code.with.cal.music__player__finaly.databinding.FavouriteViewBinding
import code.with.cal.music__player__finaly.databinding.MoreFeaturesBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
class FavouriteAdapter(private val context: Context, private var musicList: ArrayList<Music>,val playNext: Boolean = false) : RecyclerView.Adapter<FavouriteAdapter.MyHolder>() {

    class MyHolder(binding: FavouriteViewBinding) : RecyclerView.ViewHolder(binding.root) {
        val image = binding.songImgFV
        val name = binding.songNameFV
        val root = binding.root
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        return MyHolder(FavouriteViewBinding.inflate(LayoutInflater.from(context), parent, false))
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        holder.name.text = musicList[position].title
        Glide.with(context)
            .load(musicList[position].artUri)
            .apply(RequestOptions().placeholder(R.drawable.img).centerCrop())
            .into(holder.image)

        //زمان که دکمه بعدی رو میزنه چیکار کنه
        if(playNext){
            holder.root.setOnClickListener {
                val intent = Intent(context, PlayerActivity::class.java)
                intent.putExtra("index", position)
                intent.putExtra("class", "PlayNext")
                ContextCompat.startActivity(context, intent, null)
            }
            holder.root.setOnLongClickListener {
                val customDialog = LayoutInflater.from(context).inflate(R.layout.more_features, holder.root, false)
                val bindingMF = MoreFeaturesBinding.bind(customDialog)
                val dialog = MaterialAlertDialogBuilder(context).setView(customDialog)
                    .create()
                dialog.show()
                dialog.window?.setBackgroundDrawable(ColorDrawable(0x99000000.toInt()))
                bindingMF.AddToPNBtn.text = "Remove"
                bindingMF.AddToPNBtn.setOnClickListener {
                    if(position == PlayerActivity.songPosition)
                        Snackbar.make((context as Activity).findViewById(R.id.linearLayoutPN),
                            "Can't Remove Currently Playing Song.", Snackbar.LENGTH_SHORT).show()
                    else{
                        if(PlayerActivity.songPosition < position && PlayerActivity.songPosition != 0) --PlayerActivity.songPosition
                        PlayNext.playNextList.removeAt(position)
                        PlayerActivity.musicListPA.removeAt(position)
                        notifyItemRemoved(position)
                    }
                    dialog.dismiss()
                }
                return@setOnLongClickListener true
            }
        }else{
            holder.root.setOnClickListener {
                val intent = Intent(context, PlayerActivity::class.java)
                intent.putExtra("index", position)
                intent.putExtra("class", "FavouriteAdapter")
                ContextCompat.startActivity(context, intent, null)
            }
        }
    }

    override fun getItemCount(): Int {
        return musicList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateFavourites(newList: ArrayList<Music>){
        musicList = ArrayList()
        musicList.addAll(newList)
        notifyDataSetChanged()
    }

}