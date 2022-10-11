package org.sopt.sample.presentation.my_page.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.sopt.sample.data.entity.Follower
import org.sopt.sample.data.entity.FollowerInfo
import org.sopt.sample.data.entity.FollowerListTitle
import org.sopt.sample.databinding.ItemFollowerInfoBinding
import org.sopt.sample.databinding.ItemFollowerListTitleBinding
import org.sopt.sample.presentation.my_page.home.type.FollowerItemViewType
import org.sopt.sample.util.ItemDiffCallback
import timber.log.Timber

class FollowerAdapter :
    ListAdapter<Follower, RecyclerView.ViewHolder>(followerItemComparator) {
    private lateinit var inflater: LayoutInflater
    override fun getItemViewType(position: Int): Int {
        return when (position) {
            0 -> FollowerItemViewType.TITLE.id
            else -> FollowerItemViewType.FOLLOWER.id
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (!::inflater.isInitialized) inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            FollowerItemViewType.FOLLOWER.id -> FollowerViewHolder(
                ItemFollowerInfoBinding.inflate(
                    inflater,
                    parent,
                    false
                )
            )
            FollowerItemViewType.TITLE.id -> FollowerListTitleViewHolder(
                ItemFollowerListTitleBinding.inflate(
                    inflater,
                    parent,
                    false
                )
            )
            else -> throw IllegalArgumentException("viewType : $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val data = currentList[position]
        when (holder) {
            is FollowerListTitleViewHolder -> holder.onBind(data)
            is FollowerViewHolder -> holder.onBind(data)
            else -> Timber.e(IllegalArgumentException("holder : $holder"))
        }
    }

    class FollowerViewHolder(private val binding: ItemFollowerInfoBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: Follower) {
            try {
                binding.data = data as FollowerInfo
            } catch (e: ClassCastException) {
                Timber.e(e.localizedMessage)
                binding.data = FollowerInfo()
            }
        }
    }

    class FollowerListTitleViewHolder(private val binding: ItemFollowerListTitleBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: Follower) {
            try {
                binding.data = data as FollowerListTitle
            } catch (e: ClassCastException) {
                Timber.e(e.localizedMessage)
            }
        }
    }

    companion object {
        val followerItemComparator = ItemDiffCallback<Follower>(
            onItemsTheSame = { old, new -> old.id == new.id },
            onContentsTheSame = { old, new -> old == new }
        )
    }
}
