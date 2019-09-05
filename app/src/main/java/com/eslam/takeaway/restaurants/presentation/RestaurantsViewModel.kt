package com.eslam.takeaway.restaurants.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.eslam.takeaway.restaurants.model.Restaurant
import com.eslam.takeaway.restaurants.model.RestaurantUIModel
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Function3
import io.reactivex.subjects.BehaviorSubject
import java.util.function.BiFunction
import javax.inject.Inject

/**
 * The default sorting key
 */
private const val DEFAULT_SORTING_KEY = "bestMatch"

/**
 * The view model of the restaurants activity, responsible for holding the restaurants list,
 * sorting and filtering the data, and notifying the view with any changes on the list
 */
class RestaurantsViewModel : ViewModel() {

    /**
     * The interactor responsible for fetching the restaurants list
     */
    @Inject
    lateinit var interactor: IRestaurantInteractor

    /**
     * The mapper responsible for converting the [Restaurant] model to [RestaurantUIModel]
     */
    @Inject
    lateinit var mapper: BiFunction<Restaurant, String, RestaurantUIModel>

    /**
     * The word entered in the search view by the user (value passed from view)
     */
    private var filterWord = BehaviorSubject.createDefault("")

    /**
     * The sorting key chosen by the user through the spinner (value passed from view)
     */
    private var sortingKey = BehaviorSubject.createDefault(DEFAULT_SORTING_KEY)

    /**
     * The live data of restaurants list
     */
    private val restaurantsLiveData = MutableLiveData<List<RestaurantUIModel>>()

    /**
     * Exposing a [LiveData] of restaurants instead of the private [MutableLiveData]
     */
    val restaurants: LiveData<List<RestaurantUIModel>>
        get() = restaurantsLiveData

    /**
     * Used to keep track of disposables and clear them when no longer needed
     */
    private val compositeDisposable by lazy { CompositeDisposable() }

    /**
     * Initializing the Rx subscription
     * The idea is observing over any change from the main 3 observables
     *      [filterWord] representing the search view changes
     *      [sortingKey] representing the spinner value changes
     *      [restaurantsObservable] representing fetching new values from interactor
     * Once a value is retrieved from any of the 3 observables, we do the sorting (based on the below criteria),
     * filtering, and mapping to [RestaurantUIModel], and emitting a new list to the view
     *
     * Sorting Criteria:- Sort by favorites, then sort by status, then sort by sortingKey
     * */
    fun initData() {
        val restaurantsObservable = interactor.fetchAllRestaurants()

        val disposable = Observable.combineLatest(filterWord, sortingKey, restaurantsObservable,
            Function3<String, String, List<Restaurant>, List<RestaurantUIModel>> { word, key, list ->
                list.filter { it.name.toLowerCase().contains(word.toLowerCase()) }
                    .sortedWith(compareByDescending<Restaurant> { it.isFavorite }
                        .thenBy { it.status }
                        .thenByDescending { it.sortingValues[key] })
                    .map { toUIModel(it, key) }
            })
            .subscribe { restaurantsLiveData.value = it }

        compositeDisposable.add(disposable)
    }

    /**
     * Maps the [Restaurant] to [RestaurantUIModel]
     */
    private fun toUIModel(restaurant: Restaurant, sortingKey: String): RestaurantUIModel {
        return mapper.apply(restaurant,sortingKey)
    }

    /**
     * Called when user click on the favorite/unfavorite icon on one of a [restaurant]
     */
    fun onFavoriteClicked(restaurant: RestaurantUIModel) {
        if (restaurant.isFavorite) {
            interactor.unfavorite(restaurant.name)
        } else {
            interactor.favorite(restaurant.name)
        }
    }

    /**
     * Called when a new value is chosen from the spinner of sorting keys
     */
    fun onSpinnerItemSelected(item: String){
        val key = item.decapitalize().replace(" ","")
        sortingKey.onNext(key)
    }

    /**
     * Called when a search/filter query is changed
     */
    fun onSearchUpdated(search: String){
        filterWord.onNext(search)
    }

    /**
     * Disposes all subscriptions in [compositeDisposable] when ViewModel is finally destroyed
     */
    override fun onCleared() {
        compositeDisposable.dispose()
    }
}