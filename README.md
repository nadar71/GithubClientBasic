
# The project
This is a basic Github app client which allows searching Github users by name and return list of users' nick names and photos (check issue #13 for more details). 
Selecting a user in the list, a detailed profile opens, as well as the list of user's repositories, which can be ordered by name, creation, update, pushing date. 
Below screenshots of each app view.



> <img src="/sshots/screenshot_1.jpg" width="200"> <img src="/sshots/screenshot_2.jpg" width="200"> <img src="/sshots/screenshot_3.jpg" width="200"> <img src="/sshots/screenshot_4.jpg" width="200">

The app is developed in **Java**, and available for  device starting from **Android 4.1 version (sdk 16) up**. 
Currently run at best in portrait, soon will be available in landscape mode (issue #2) and optimized for tablet (issue #30).



## Instructions

- To install, clone this repo from [here](https://github.com/nadar71/GithubClientBasic) or download project zip file 
- Use Android Studio (vers. 3.5+ the better) to run Project, selecting the main app folder containing *versions.gradle* 
- Wait for Gradle synchronization
- Build and run on emulator/device.


# Used in this app (vers. 2.0):

The app is developed using  **MVVM** clean architecture though **Android Architecture Components**. 

The View layer is connected using **ViewModel/Livedata** to the **Repository**, that is the single source of truth, and manage both database and the remote connections.

Each Activities or Fragments are kept aware of data changing in  data layer through **ViewModel** which drive the observables with **Livedata** technology: every changes in below layer is immediately driven to the corresponding views with the lesser latency; every view has it's own ViewModel serving for this purposes.

In detail user's view, the repositories list filter uses remote requests to Github api for each different kind of sorting as per initial requirements. 
Soon all the filtering/ordering could be emproved using directly db for that and not only as cache, faster and with no network latency (issue #17).


## Persistent storage
I adopted **Room Persistent Library** as main storage; it has 3 tables USERS_PROFILES_MINI, USERS_PROFILES_FULL, USERS_REPOSITORIES based  on 3 entity objects: UserProfile_Mini, UserProfile_Full and UserRepository.

The difference between the first 2 user object, is that the first has the half of the data in UserProfile_Full, to reduce db and recyclerView storage at minimum while loading more results, and keep the app smooth in control.

The data are deleted at each new search, and more data can be loaded with load more button or soon by scrolling at bottom (issue #20).
In the future implementations a managed caching of data could be a good optimization improvements.

## Handling tasks
Used **Executor** for efficient and global threading pool throughout the app where needed.

## Addictional library
Used **Retrofit 2** for remote requests for compact and easy coding; **Glide** for remote images downloading and caching.


# Testing
Developed unit tests and UI/Instrumentation tests,check in Android Studio; new ones are in development, check the notes in each test file.



# Future Developments and issues

- styling and layout improvements
- improvements and fixing as reported in issues on Github section 
- increase testing suite (as example, mock server for retrofit unit testing, and deeper testing of viewmodel/livedata, and so on.
- dependency injection (Dagger 2)
- migration to Kotlin and React-Native/Redux



