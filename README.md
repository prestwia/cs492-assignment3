# Assignment 3
**Due by 11:59pm on Monday, 2/21/2022** <br />
**Demo due by 11:59pm on Monday, 3/7/2022**

In this assignment, we'll adapt our weather app to gracefully deal with transitions in the activity lifecycle by implementing the `ViewModel` architecture.  You'll also add some basic user preferences to the app.  There are a few different tasks associated with this assignment, described below.  This repository provides you with some starter code that implements the connected weather app from assignment 2.

**NOTE: make sure to add your own API key as described in [`MainActivity.kt`](app/src/main/java/com/example/android/lifecycleweather/ui/MainActivity.kt#L28-L44) to make the app work.**

## 1. Implement the ViewModel architecture and use Retrofit

One thing you might notice is that when you do things like rotate your device when viewing the main activity, the activity is recreated, resulting in a new network call to fetch the same weather forecast data.  You can know this is happening because the loading indicator will be displayed, indicating that the network call to fetch forecast data from the OpenWeather API is being re-executed.  This happens because the network call is initiated in the `onCreate()` function in the main activity class.

Your first task in this assignment is to fix this problem by moving the main activity's data management behind a `ViewModel` to make our activity better cope with lifecycle transitions.  Doing this will involve a few different sub-tasks:

  * Set up a Retrofit service interface that you'll use to communicate with the OpenWeather API.  Your service interface will only need one method to call the OpenWeather 5 day/3 hour forecast API.  Make sure to set this method up with appropriate arguments to instantiate all the fields you'll need to put into the URL to make the API call (e.g. city name, units, API key).

  * Implement a Repository class to perform the data operations associated with communicating with the OpenWeather API.  This Repository class will use your Retrofit service to make API calls.  Remember that all network calls must be executed in a background thread, not the main UI thread.  The Repository class should use elements of the Kotlin coroutines framework to make sure this happens.

  * Implement a `ViewModel` class to serve as the intermediary between the Repository class and the UI.  This class should contain methods for triggering a new data load, and it should make the fetched forecast data available to the UI.

  * Set up the UI (i.e. the main activity class) to observe changes to the forecast data held within the `ViewModel` and to update the state of the UI as appropriate based on changes to that data.  Importantly, this should be done in such a way that the loading indicator and error message behave as currently implemented in the UI.

As a result of these changes, you should see your app fetch results from the OpenWeather API only one time through typical usage of the app, including through rotations of the phone and navigation around the app.

## 2. Add some basic user preferences to the app

Your second task in this assignment is to create a new activity named `SettingsActivity` that implements a user preferences screen using a `PreferenceFragment`.  Add a "settings" action button to the app bar in the main activity to launch the preferences screen.

The preferences screen should allow the user to set the following preferences:

  * **Forecast units** - The user should be allowed to select between "Imperial", "Metric", and "Kelvin" temperature units.  The currently-selected value should be displayed as the summary for the preference.  See the OpenWeather API documentation here for more info on how this preference value will be used to formulate API requests: https://openweathermap.org/forecast5#data.

  * **Forecast location** - The user should be allowed to enter an arbitrary location for which to fetch a forecast.  The currently-set value should be set as the summary for the preference.  You can specify any default location you'd like.  See the OpenWeather API documentation here for more info on how this preference value will be used to formulate API requests: https://openweathermap.org/forecast5#name5.

The settings of these preferences should affect the URL used to query the OpenWeather API.  The app should be hooked up so that any change to the preferences results in the OpenWeather API being re-queried and the newly-fetched forecast data being displayed.  Importantly, there are a couple places in the UI where the "F" is hard-coded to indicate a Fahrenheit temperature.  Make sure to update these locations to display the appropriate unit for the current setting ("F" for Fahrenheit, "C" for Celsius, and "K" for Kelvin).

## Submission

As usual, we'll be using GitHub Classroom for this assignment, and you will submit your assignment via GitHub.  Make sure your completed files are committed and pushed by the assignment's deadline to the master branch of the GitHub repo that was created for you by GitHub Classroom.  A good way to check whether your files are safely submitted is to look at the master branch your assignment repo on the github.com website (i.e. https://github.com/osu-cs492-w22/assignment-3-YourGitHubUsername/). If your changes show up there, you can consider your files submitted.

## Grading criteria

This assignment is worth 100 points, broken down as follows:

  * 65 points: Implements `ViewModel` architecture
    * 10 points: implements Retrofit service interface to represent communication with the OpenWeather API
    * 20 points: implements Repository class to perform data fetching and store data
    * 10 points: implements `ViewModel` class to interface between UI and Repository
    * 15 points: observes `ViewModel` data in UI and updates the UI state appropriately (including loading indicator and error message) as data changes
    * 10 points: correctly uses Kotlin coroutines to execute network calls in a background thread, following best practices as discussed in lecture

  * 35 points: Implements user settings activity
    * 15 points: implements a preference fragment to allow the user to select temperature units and forecast location
    * 5 points: summaries of both preferences reflect the current values set for those preferences
    * 15 points: changing preference values results in new data being fetched/displayed and correct updates being made to the UI, as described above
