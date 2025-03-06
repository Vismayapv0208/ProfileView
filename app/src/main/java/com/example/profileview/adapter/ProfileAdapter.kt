package com.example.profileview.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.profileview.databinding.ProfileItemBinding
import com.example.profileview.model.Profile

class ProfileAdapter(private val onEditClick: (Profile) -> Unit) :
    ListAdapter<Profile, ProfileAdapter.ProfileViewHolder>(ProfileDiffCallback()) {

    interface ProfileClickListener {
        fun onDeleteClick(profile: Profile)
        fun onEditClick(profile: Profile)
    }
    private var clickListener: ProfileClickListener? = null

    fun setClickListener(clickListener: ProfileClickListener) {
        this.clickListener = clickListener
    }

    class ProfileViewHolder(private val binding: ProfileItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(profile: Profile, clickListener: ProfileClickListener?) {
            binding.profile = profile
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ProfileItemBinding.inflate(inflater, parent, false)
        return ProfileViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProfileViewHolder, position: Int) {
        val profile = getItem(position)
        holder.bind(profile, clickListener)
    }
}

class ProfileDiffCallback : DiffUtil.ItemCallback<Profile>() {
    override fun areItemsTheSame(oldItem: Profile, newItem: Profile): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Profile, newItem: Profile): Boolean {
        return oldItem == newItem
    }
}