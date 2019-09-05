**Restaurants**
-

An android application shows a list of restaurants (read from a local asset file), with the ability to
- Favorite/Unfavorite a restaurant
- Filter the list by entering a search text in the search view 
- Sort the list based on a sorting key chosen by the user

To run the app and the unit tests, simply clone the application and run it using Android Studio. 
The application was developed using (Android Studio 3.4.2)

Technical decisions:
- The application follows the MVVM clean architecture
- RxJava is used for communication between different layers
- LiveData is used for holding data and communication between View and ViewModel
- Mockito and Google Truth are used for testing, besides JUnit