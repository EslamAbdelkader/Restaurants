package com.eslam.takeaway.restaurants.presentation

import android.view.View
import android.view.ViewGroup
import androidx.core.util.Consumer
import androidx.recyclerview.widget.RecyclerView
import com.eslam.takeaway.R
import com.eslam.takeaway.restaurants.model.RestaurantUIModel
import com.eslam.takeaway.util.inflate
import kotlinx.android.synthetic.main.row_restaurant.view.*

/**
 * RecyclerView adapter for restaurants for the overview screen
 */
class RestaurantAdapter(
    private val restaurants: List<RestaurantUIModel>,
    private val consumer: Consumer<RestaurantUIModel>
) :
    RecyclerView.Adapter<RestaurantAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            parent.inflate(
                R.layout.row_restaurant,
                false
            )
        )
    }

    override fun getItemCount(): Int = restaurants.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(restaurants[position], consumer)
    }

    /**
     * Restaurant View Holder
     */
    class ViewHolder(
        private val view: View
    ) : RecyclerView.ViewHolder(view) {

        fun bind(restaurant: RestaurantUIModel, consumer: Consumer<RestaurantUIModel>) {
            view.title.text = restaurant.name
            view.status.text = view.context.getString(R.string.status_placeholder, restaurant.status)
            view.subtitle.text =  view.context.getString(R.string.score_placeholder,restaurant.sortValue.toString())
            view.icon.setOnClickListener { consumer.accept(restaurant) }

            when(restaurant.isFavorite){
                true -> view.icon.setImageResource(R.drawable.filled_star)
                false -> view.icon.setImageResource(R.drawable.empty_star)
            }
        }

    }
}
