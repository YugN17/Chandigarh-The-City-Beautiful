# Chandigarh-The-City-Beautiful
An Android app developed using Kotlin to fetch JSON data from an URL and to display it in a GridView using RecyclerView.

Features:
1. Uses MVVM Architecture.
2. Data Binding.
3. Developed using Android Architecture components Room, ViewModel, LiveData, Navigation etc.
4. JSON is fetched from API (http://xdeveloper.esy.es/chd/api.php) using Retrofit and Moshi library.
5. Uses Glide library to load images from URL.
6. RecyclerView with custom adapter for smooth scrolling.
7. Manual Dependency Injection is used.
8. Kotlin coroutines used for concurrency.

<h2>Architecture</h2>
<img src="https://github.com/ravi-kumar7/Chandigarh-The-City-Beautiful/raw/master/architecture.png"/>

<h2>Snapshot</h2>
<div style={display:flex}>
<img src="https://github.com/ravi-kumar7/Chandigarh-The-City-Beautiful/raw/master/screenshot_home.png" height="400px" width="200px">
<img src="https://github.com/ravi-kumar7/Chandigarh-The-City-Beautiful/blob/master/screenshot_home_places.jpg" height="400px" width="200px">
<img src="https://github.com/ravi-kumar7/Chandigarh-The-City-Beautiful/blob/master/screenshot_place_detail.jpg" height="400px" width="200px">
</div>
