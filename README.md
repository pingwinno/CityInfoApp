Приложение для поиска городов\стран в GeonamesAPI.
Несмотря на приведение json файла к правильной кодировке (Windows-1252) некоторые названия городов невосстановимы.
Для них выдается информация о стране. Так же страна выдается для городов которые не удалось найти в выдаче из-за отсутсвии фильтров и 
продвинутых опций поиска на api.geonames.org/wikipediaSearch как в api.geonames.org/search. Не смотря на относительно продвинутую 
фильтрацию результатой выдачи, в редких случаях, может выдаваться другой город этой же страны. Для минимизирования этих проблем
информация выводится напрямую с wikipedia (по ссылке взятой в geonames API) в webview либо CustomChromeTabs в зависимости от 
доступности последних на устройстве.
