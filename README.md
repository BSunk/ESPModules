# ESPModules
Android app to control an esp8266 microcontroller attached to RGB light.

Control a ESP8266 (cheap 4-5 dollar wifi microcontroller) attached to a WS2812 RGB light.
Control color, brightness, pattern.
ESP8266 hosts a web server + connects to a MQTT server(optional, for control outside of Wifi network). 

So far implemented: 
 - Search using mDNS for ESP8266 on wifi network.
 - Add connection to realm database
 - View devices 
 - BottomNavigation to go through pages
 - Brightness slider and power off/on
 - Select color using color picker dialog
 
Need to implement:
 - Settings screen
 - Phone events to trigger light using Broadcast Receiver
 - MQTT for control anywhere
 - Delete/Edit a connection

Libraries Used:
 - RxJava2
 - Dagger2
 - Retrolambda
 - Retrofit2
 - Butterknife
 - Timber
 - Realm/RealmRecyclerView
 - ColorPicker

 ##Prelim Screenshots
 <img src="/screenshots/Screenshot_20170222-142854.png" alt="image" width="300">
 <img src="/screenshots/Screenshot_20170220-211337.png" alt="image" width="300">
 <img src="/screenshots/Screenshot_20170220-211346.png" alt="image" width="300">
 <img src="/screenshots/Screenshot_20170222-182624.png" alt="image" width="300">
 <img src="/screenshots/Screenshot_20170222-182630.png" alt="image" width="300">
 <img src="/screenshots/Screenshot_20170222-182634.png" alt="image" width="300">
