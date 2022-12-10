package org.sopt.sample.presentation.my_page.gallery

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.sopt.sample.data.entity.Music
import org.sopt.sample.databinding.ItemMusicBinding
import org.sopt.sample.util.ItemDiffCallback

class MusicAdapter : ListAdapter<Music, MusicAdapter.MusicViewHolder>(musicDiffUtil) {
    private lateinit var inflater: LayoutInflater

    class MusicViewHolder(
        private val binding: ItemMusicBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(music: Music) {
            binding.data = music
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MusicViewHolder {
        if (!::inflater.isInitialized) inflater = LayoutInflater.from(parent.context)
        return MusicViewHolder(ItemMusicBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: MusicViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current)
    }

    companion object {
        private val musicDiffUtil = ItemDiffCallback<Music>(
            onItemsTheSame = { old, new -> old.id == new.id },
            onContentsTheSame = { old, new -> old == new }
        )
    }
}
