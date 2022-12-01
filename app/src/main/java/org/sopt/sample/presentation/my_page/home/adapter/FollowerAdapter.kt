package org.sopt.sample.presentation.my_page.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.sopt.sample.data.entity.Follower
import org.sopt.sample.data.entity.FollowerContent
import org.sopt.sample.data.entity.FollowerHeader
import org.sopt.sample.databinding.ItemFollowerInfoBinding
import org.sopt.sample.databinding.ItemFollowerListTitleBinding
import org.sopt.sample.presentation.my_page.home.type.FollowerItemViewType
import org.sopt.sample.util.ItemDiffCallback

class FollowerAdapter() :
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
        when (data) {
            is FollowerHeader -> (holder as FollowerListTitleViewHolder).onBind(data)
            is FollowerContent -> (holder as FollowerViewHolder).onBind(data)
        }
    }

    class FollowerViewHolder(private val binding: ItemFollowerInfoBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: FollowerContent) {
            binding.data = data
        }
    }

    class FollowerListTitleViewHolder(private val binding: ItemFollowerListTitleBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: FollowerHeader) {
            binding.data = data
        }
    }

    companion object {
        val followerItemComparator = ItemDiffCallback<Follower>(
            onItemsTheSame = { old, new -> old.id == new.id },
            onContentsTheSame = { old, new -> old == new }
        )
    }
}
