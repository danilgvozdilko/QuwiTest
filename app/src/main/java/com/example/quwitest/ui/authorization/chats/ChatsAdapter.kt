package com.example.quwitest.ui.authorization.chats

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.quwitest.Constants
import com.example.quwitest.R
import com.example.quwitest.data.Channel
import com.example.quwitest.databinding.ChatsBinding

class ChatsAdapter : ListAdapter<Channel, ChatsAdapter.ShopCategoryViewHolder>(
    DiffCallback()
) {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holderShop: ShopCategoryViewHolder, position: Int) {
        holderShop.bind(currentList[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopCategoryViewHolder {
        return ShopCategoryViewHolder(
            ChatsBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ), parent, false
            )
        )
    }

    class ShopCategoryViewHolder(
        private var binding: ChatsBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(channel: Channel?) {
            with(binding) {
                val context = root.context
                if (channel?.message_last?.isRead() == true) {
                    isRead.setImageDrawable(
                        ContextCompat.getDrawable(
                            context,
                            R.drawable.ic_readed
                        )
                    )
                } else {
                    isRead.setImageDrawable(
                        ContextCompat.getDrawable(
                            context,
                            R.drawable.ic_new_message
                        )
                    )
                }

                val me = context.getString(R.string.me)
                val emptyDialog = context.getString(R.string.this_dialog_is_empty)
                val dateFromApi = channel?.lastMessage?.dta_create
                val dates = dateFromApi?.substring(Constants.HOUR, Constants.MINUTES)

                name.text = channel?.lastMessage?.user?.name ?: me
                txtDate.text = dates
                dialog.text = channel?.lastMessage?.text ?: emptyDialog
                if (channel?.pin == true) {
                    pin.setImageDrawable(
                        ContextCompat.getDrawable(
                            context,
                            R.drawable.ic_pin
                        )
                    )
                }
                val avatarUrl = channel?.lastMessage?.user?.avatar_url?.firstOrNull()
                if (avatarUrl != null) {
                    Glide.with(avatar.context)
                        .load(avatarUrl)
                        .into(avatar)
                } else {
                    avatar.setImageDrawable(
                        ContextCompat.getDrawable(
                            context,
                            R.drawable.ic_no_avatar
                        )
                    )
                }
            }

        }
    }

    class DiffCallback : DiffUtil.ItemCallback<Channel>() {
        override fun areItemsTheSame(
            oldItem: Channel,
            newItem: Channel
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: Channel,
            newItem: Channel
        ): Boolean {
            return oldItem == newItem
        }
    }

}