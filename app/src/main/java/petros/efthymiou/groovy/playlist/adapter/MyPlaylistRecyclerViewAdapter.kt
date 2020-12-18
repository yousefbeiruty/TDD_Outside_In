package petros.efthymiou.groovy.playlist.adapter

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import petros.efthymiou.groovy.R
import petros.efthymiou.groovy.playlist.models.Playlist


class MyPlaylistRecyclerViewAdapter(
    private val values: List<Playlist>,
    private val listner:(String)->Unit
) : RecyclerView.Adapter<MyPlaylistRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.playlist_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.playlistName.text=item.name
        holder.playlist_category.text=item.category
        holder.playlist_image.setImageResource(item.image)
        holder.root.setOnClickListener {listner(item.id)}
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val playlistName: TextView = view.findViewById(R.id.playlist_name)
        val playlist_category: TextView = view.findViewById(R.id.playlist_category)
        val playlist_image: ImageView = view.findViewById(R.id.playlist_image)
        val root:View=view.findViewById(R.id.playlist_item_root)
        //val contentView: TextView = view.findViewById(R.id.content)
    }
}