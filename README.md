# Weather app

Please take into account this was a fast paced, simple MVVM weather app creation with everything below mentioned used, to display the weather in a simple way following the weather app designed provided.

If you want to take a look into a more structed codebase, with better practices uses and overwall more quality, please consider taking a look at: 

### Lightning Money: 
https://github.com/filipedrmorgado/LightningMoney

-An Android application for managing a Lightning Network Bitcoin wallet, showcasing its speed and efficiency as a proof-of-concept, highlighting its potential for rapid and cost-effective transactions. More details on the repo.

**Preview**

<div align="center">
  <img src="images/screenshot_1.png?raw=true" width="300" height="700" alt="Image 1">
  &nbsp;&nbsp;&nbsp; <!-- Add more "&nbsp;" as needed for spacing -->
  <img src="images/screenshot_2.png?raw=true" width="300" height="700" alt="Image 2">
  &nbsp;&nbsp;&nbsp;
  <img src="images/screenshot_3.png?raw=true" width="300" height="700" alt="Image 3">
</div>


**Required to run it**
- Android studio
- JDK 8
- Android SDK 33
- Supports API Level +26
- Material Components 1.8.0

**Highlights**
- Use [OpenWeatherMap](https://www.weatherapi.com/) API
    - API Key is openly shown and can be used by anyone, so you are able to clone it and just run it.
- Coroutines 
- Retrofit
- Fragments
- MVVM + Repository pattern
- Koin dependency injection
- Use Shared Preferences for storage

**Libraries & Dependencies**
- [Support libraries]: appcompat / recyclerview / constraintlayout
- [Material Design 2]: MaterialCardView / MaterialButton / Bottom App Bars / ExtendedFloatingActionButton
- [FastAdapter]: The bullet proof, fast and easy to use adapter library, which minimizes developing time to a fraction
- [Calligraphy3]: Custom fonts in Android the easy way
- Square [Retrofit] / [Okhttp] / [Logging-Interceptor]
- [Glide]: An image loading and caching library for Android focused on smooth scrolling
- [Lottie-Android]: Render After Effects animations natively on Android
- [MaterialSearchView]: Library to implement SearchView in a Material Design Approach
- [MPAndroidChart]: A powerful & easy to use chart library for Android

**Credit**

### This app was inspired from [Weather App Freebie] concept Designed by [Raman Yv] 

# License

    Copyright 2023 Filipe Morgado

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
    
[Weather App Freebie]: https://www.uplabs.com/posts/weather-app-freebie    
[OpenWeatherMap]: (https://www.weatherapi.com/)
[Support libraries]: https://developer.android.com/jetpack/androidx/
[Material Design 2]: https://material.io/develop/android/
[FastAdapter]: https://github.com/mikepenz/FastAdapter
[Retrofit]: https://github.com/square/retrofit
[Okhttp]: https://github.com/square/okhttp
[Logging-Interceptor]: https://github.com/square/okhttp/tree/master/okhttp-logging-interceptor
[Glide]: https://github.com/bumptech/glide
[Lottie-Android]: https://github.com/airbnb/lottie-android
[MaterialSearchView]: https://github.com/MiguelCatalan/MaterialSearchView
[MPAndroidChart]: https://github.com/PhilJay/MPAndroidChart
[ButterKnife]: https://github.com/JakeWharton/butterknife
