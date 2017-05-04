Synergy Calls
=============

Synergy Calls is application for Android OS, which logging calls to the server through the [POST](https://en.wikipedia.org/wiki/POST_(HTTP)) request method.

![Synergy Calls Android Screen](https://raw.github.com/EXL/SynergyCalls/master/images/SynergyCalls_Android_Screen.png)

For getting information about calls the application uses the PhoneCallReceiver class, found on [Stack Overflow](http://stackoverflow.com/a/30521544/2467443).

![Konsole Log Linux Screen](https://raw.github.com/EXL/SynergyCalls/master/images/Konsole_Log_Linux_Screen.png)

In order to Android OS does not unload the application from memory, a Service class was used. For the application to be started with the launch of the device, a BroadcastReceiver class was used.

## Download

You can download APK-package for Android OS from the [releases](https://github.com/EXL/SynergyCalls/releases) section.

## Build instructions

For example, GNU/Linux:

* Install the latest [Android SDK](https://developer.android.com/sdk/);

* Clone repository into deploy directory;

```sh
cd ~/Deploy/
git clone https://github.com/EXL/SynergyCalls SynergyCallsAndroid
```

* Build the APK-package into deploy directory with Gradle building script;

```sh
cd ~/Deploy/SynergyCallsAndroid/
ANDROID_HOME="/opt/android-sdk/" ./gradlew assembleDebug
```

* Install Synergy Calls APK-package on your Android device via adb;

```sh
cd ~/Deploy/SynergyCallsAndroid/
/opt/android-sdk/platform-tools/adb install -r synergy/build/outputs/apk/synergy-debug.apk
```

* Check the all necessary permissions;

* Run and enjoy!

You can also open this project in Android Studio IDE and build the APK-package by using this program.

## More information

Please read [Making Guide (In Russian)](http://exlmoto.ru/new-updates-and-tools#1) for more info about making Synergy Calls for Android OS.
