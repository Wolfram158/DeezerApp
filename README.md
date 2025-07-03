# DeezerApp
## Инструкции по запуску на эмуляторе 
### Способ 1 (через командную строку) 
1. Выполнить команду: git clone https://github.com/Wolfram158/DeezerApp.git
2. [Следовать инструкциям по ссылке](https://developer.android.com/studio/run/emulator-commandline)
### Способ 2 (с помощью Android Studio) 
1. Выполнить команду: git clone https://github.com/Wolfram158/DeezerApp.git
2. Открыть проект в Android Studio
3. Запустить приложение

## Технологический стек 
1. **Навигация**: [Jetpack Navigation](https://developer.android.com/guide/navigation)
2. **Инъекция зависимостей**: [Dagger](https://developer.android.com/training/dependency-injection/dagger-android)
3. **Сериализатор**: Gson
4. **Работа с сетью**: [Retrofit](https://github.com/square/retrofit), [OkHttp](https://github.com/square/okhttp)
5. **Многопоточность**: [Kotlin Coroutines](https://developer.android.com/kotlin/coroutines), [Flow](https://developer.android.com/kotlin/flow)
6. **View**: XML
7. **Загрузка изображений**: [Coil](https://coil-kt.github.io/coil/image_loaders/)
8. **Работа с аудио**: [Jetpack Media3](https://developer.android.com/media/media3)
9. **Build Features**: [View Binding](https://developer.android.com/reference/tools/gradle-api/8.9/com/android/build/api/dsl/BuildFeatures)
10. **Архитектура**: MVVM
11. **Принципы**: Clean Architecture, single responsibility, dependency inversion

## Проблемы, вопросы, примечания
1. Для доступа к документации [Deezer API](https://developers.deezer.com/) необходимо зарегистрироваться или войти, что затруднительно на территории РФ. Однако опытным путём установлено, что существует параметр **limit**, передав который, можно получить не более **limit** элементов (в приложении установлена константа LIMIT = 30, однако значение можно было установить в качестве настройки приложения с помощью [DataStore](https://developer.android.com/topic/libraries/architecture/datastore)). Сложности c изучением API привели к тому, что не была реализована пагинация для списка найденных с помощью Deezer API треков.
2. Изначально предполагалось, что файлы, связанные с **Dagger2**, будут располагаться в отдельном модуле **di**. Однако возникли циклические зависимости, которые удалось устранить путём перенесения всех файлов, связанных с **Dagger2**, в модуль **app**.
3. В задании не указана логика работы плеера. С одной стороны, треки могут воспроизводиться согласно порядку в имеющемся списке треков (для чего на фрагмент, отвечающий за экран воспроизведения треков, предполагалось изначально передавать список имеющихся треков и позицию). С другой стороны, при каждом переходе на экран воспроизведения можно очищать плейлист и воспроизводить трек, соответствующий последнему переходу на экран с воспроизведением. Ещё можно добавить поддержку различных стратегий, которые можно установить в настройках приложения. Однако в текущей реализации логика следующая: при каждом переходе на экран с воспроизведением трека сооветствующий трек добавляется в конец плейлиста. Очистить плейлист можно при удалении приложения из recent apps или при нажатии на системную кнопку "назад" при нахождении в start destination, для чего переопределён метод **onCleared** класса **MainViewModel**.
4. В задании не указано, можно ли использовать класс **MediaSessionService**, предоставляющий готовые уведомления, и класс **PlayerView**, предоставляющий почти готовое решение для экрана с воспроизведением трека. Имеется проблема, связанная с **PlayerView** и состоящая в том, что при переходе к следующему треку автоматически не обновляется картина трека. Эту проблему, возможно, можно было бы решить, сделав вместо **PlayerView** CustomView или добавив на экран вместо **PlayerView** отдельно **ImageView** (для отображения изображения трека), **ImageButton** (для возможности поставить на паузу, продолжить, перейти к следующему, предыдущему), **SeekBar** (для отображения прогресса), **TextView** (для отображения времени, прошедшего с начала трека, и отображения total time; для отображения указанной в задании информации о треке). Изначально предполагался последний подход, однако в текущей реализации используется **PlayerView**. Также добавлена возможность скачать трек из Deezer API.
5. Решение протестировано на эмуляторах Pixel_3a_API_34_extension_level_7_x86_64 и Pixel 2 (Edited) API 29, а также на настоящем устройстве (Android 10).
6. Допускается наличие неиспользуемых зависимостей в некоторых модулях.
7. Следует отметить факт, повлекший, возможно, ухудшение качества кода. В классе **FoundTracksFragment** имеется следующий код:
```kotlin
binding.tryLoadButton.setOnClickListener {
            when (val text = binding.editQuery.text.toString().trim()) {
                "" -> viewModel.loadChart(limit = LIMIT)
                else -> viewModel.loadTracks(text, limit = LIMIT)
            }
}
```
Нарушен принцип single responsibility. Cледовало сделать так, чтобы вызывалась только функция ```loadTracks``` от аргумента ```binding.editQuery.text.toString()``` вне зависимости от его значения. </br>
8. Граф зависимостей модулей имеет следующий вид:
   ![dependency graph](https://raw.githubusercontent.com/Wolfram158/DeezerApp/master/image.png)
