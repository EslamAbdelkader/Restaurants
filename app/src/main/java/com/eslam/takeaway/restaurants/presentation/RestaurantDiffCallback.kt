package com.eslam.takeaway.restaurants.presentation

import androidx.annotation.Nullable
import androidx.recyclerview.widget.DiffUtil
import com.eslam.takeaway.restaurants.model.RestaurantUIModel

class RestaurantDiffCallback(private val oldList: List<RestaurantUIModel>,
                             private val newList: List<RestaurantUIModel>
): DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].name === newList[newItemPosition].name
    }

    override fun areContentsTheSame(oldPosition: Int, newPosition: Int): Boolean {
        return oldList[oldPosition].isFavorite == newList[newPosition].isFavorite
    }

    @Nullable
    override fun getChangePayload(oldPosition: Int, newPosition: Int): Any? {
        return super.getChangePayload(oldPosition, newPosition)
    }
}