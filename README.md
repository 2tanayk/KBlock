# KBlock :telephone_receiver: :no_entry_sign:

<img width="600" alt="kblock" src="https://user-images.githubusercontent.com/60653277/142725041-9942e985-96fd-4edc-ad12-3ec3d41b8f2e.png">

### Introduction

KBlock - A call blocking android app (currently works for API 28+)

## About
- It imports all the user contacts from the Contacts Content Provider, cleans duplicate contacts (if any,in app, without altering the actual contacts list) and loads it into perisistent storage(SQLite database), then gives user an option to block(or unblock) a contact. A refresh button is also provided in case there is a change in user contacts and thus KBlock's contact list is in sync with the user's contacts.
- Blocked contacts are saved separately in a tab and can be unblocked immediately.
- As soon as a particular blocked user places a call, the call is immediately disconnected and the user is notified about it as well.
- Finally we also provide a log of calls from the blocked user (which was disconnected by the app).

## Built With
- [Kotlin](https://kotlinlang.org/) - Official programming language for Android development.
- [Coroutines](https://kotlinlang.org/docs/reference/coroutines-overview.html) - For asynchronous programming
- [Android Architecture Components](https://developer.android.com/topic/libraries/architecture) - Collection of libraries that help you design robust, testable, and maintainable apps.
  - [LiveData](https://developer.android.com/topic/libraries/architecture/livedata) - Data objects that notify views when the underlying database changes.
  - [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) - Stores UI-related data that isn't destroyed on UI changes. 
  - [ViewBinding](https://developer.android.com/topic/libraries/view-binding) - Generates a binding class for each XML layout file present in that module and allows you to more easily write code that interacts with views.
  - [Room](https://developer.android.com/topic/libraries/architecture/room) - SQLite object mapping library.
- [Material Components for Android](https://github.com/material-components/material-components-android) - Modular and customizable Material Design UI components for Android.

## Architecture
This app uses [***MVVM (Model View View-Model)***](https://developer.android.com/jetpack/docs/guide#recommended-app-arch) architecture.



