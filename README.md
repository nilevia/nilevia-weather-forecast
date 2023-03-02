# nilevia-weather-forecast

Weather Forecast Project

## Result

![](https://github.com/nilevia/nilevia-weather-forecast/blob/main/documentation/1.png)
![](https://github.com/nilevia/nilevia-weather-forecast/blob/main/documentation/2.png)
![](https://github.com/nilevia/nilevia-weather-forecast/blob/main/documentation/3.png)
![](https://github.com/nilevia/nilevia-weather-forecast/blob/main/documentation/4.png)


## Archietecture
Clean Arch and MVVM Pattern

Separate application into 3 modules

- presentation layer (module app)

- domain layer (module domain)

- data layer (module data)

Use Single Activity and Navigation Component

Using Koin as Depedency Injection

Using Room as Local Database

Using Compose as ui


## Known Bug
I run out of time because this done on weekday with full of meeting, so here's known bug

- If you already save the city and then click on the favourite city, the button save will appear (it should be delete city) if you click, it will save twice
- Layout is not good enough, have no time to desain
- I give unit test example just for one viewmodel
- Im not do the hackerank yet.