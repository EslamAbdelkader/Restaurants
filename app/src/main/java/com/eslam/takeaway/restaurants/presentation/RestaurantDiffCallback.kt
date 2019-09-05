package com.eslam.takeaway.restaurants.presentation

import androidx.annotation.Nullable
import androidx.recyclerview.widget.DiffUtil
import com.eslam.takeaway.restaurants.model.RestaurantUIModel

/**
 * Responsible for calculating the differences between two lists of [RestaurantUIModel]
 * for better user experience and better performance
 */
class RestaurantDiffCallback(private val oldList: List<RestaurantUIModel>,
                             private val newList: List<RestaurantUIModel>
): DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].name === newList[newItemPosition].name
    }

    override fun areContentsTheSame(oldPosition: Int, newPosition: Int): Boolean {
        return oldList[oldPosition] == newList[newPosition]
    }

    @Nullable
    override fun getChangePayload(oldPosition: Int, newPosition: Int): Any? {
        return super.getChangePayload(oldPosition, newPosition)
    }
}