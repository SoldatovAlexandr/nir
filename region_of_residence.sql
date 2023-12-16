SELECT preventive_actions_details, count(preventive_actions_details)
FROM cadet
GROUP BY preventive_actions_details
ORDER BY count(preventive_actions_details) DESC;



UPDATE cadet
SET region_of_residence ='Санкт-Петербург'
WHERE region_of_residence IN ('СПб', 'С-Пб', 'Санкт-Питербург', 'г.Санкт-Петербург', 'Спб', 'Новоизмаиловск', 'С-Пб.');

UPDATE cadet
SET region_of_residence ='Республика Северная Осетия'
WHERE region_of_residence IN
      ('РСО - Алания', 'РСО-Алания', 'РСР - Алания', 'РСО- Алания', 'Алания', 'Владикавказ', 'РЮОсетин');

UPDATE cadet
SET region_of_residence ='Москва'
WHERE region_of_residence IN
      ('Москва', 'г.Москва', 'Малас', 'Московская обл.,г.Павловский Посад', 'Троицк', 'Московская орбл.,г.Ивантеевка',
       'Московская обл.,п.Видное', 'Московская обл.,г.Ивантеевка', 'Московская обл.,г.Мытищи',
       'Московская обл.,г.Люберцы', 'г. Москва', 'Г.Москва', 'Московская обл.,г.Кубинка');

UPDATE cadet
SET region_of_residence ='Астраханская область'
WHERE region_of_residence IN ('Астрахань');


UPDATE cadet
SET region_of_residence ='Владимирская область'
WHERE region_of_residence IN ('Ковров', 'г.Владимир', 'Владимир','Владимирская обл.');


UPDATE cadet
SET region_of_residence ='Республика Ингушетия'
WHERE region_of_residence IN ('Ингешетия');


UPDATE cadet
SET region_of_residence ='Ленинградская область'
WHERE region_of_residence IN ('Ленинградская обл.,г.Тосно', 'ЛО', 'Выборг', 'Ленинградская обл.,г.Всеволжск');


UPDATE cadet
SET region_of_residence ='Кабардино-Балкарская Республика'
WHERE region_of_residence IN ('КБР');

UPDATE cadet
SET region_of_residence ='Екатеринбург'
WHERE region_of_residence IN ('Екб', 'Екб.');

UPDATE cadet
SET region_of_residence ='Московская область'
WHERE region_of_residence IN
      ('Московская обл', 'Московская обл.', 'Московская обл.,г.Видное', 'Московская обл.,г.Жуковский', 'МО', 'Балашиха',
       'Московская обл.,г.Одинцово', 'Московская обл.,г.Балашиха', 'Подольск', 'Московская область.г.Раменское',
       'Московская обл.,г.Подольск', 'Московская обл.,г.Химки', 'Московская обл.,г.Чехов',
       'Московская обл.,Солнечногорский район', 'Московская обл.,пос Нахабино', 'Московская обл.,г.Кашира',
       'Московская обл.,г.Долгопрудный', 'Московская обл.,Павлово- Посадский район', 'Московская область.г.Бронницы',
       'Московская обл.,Люберецкий район', 'Московская обл.,г.Кательники', 'Московская обл.,г.Красногорск',
       'Московская обл.,пос.Власиха','Московская обл.,г.Коломна');

UPDATE cadet
SET region_of_residence ='Тульская область'
WHERE region_of_residence IN
      ('г.Тула', 'Тула', 'Тульская обл.,г.Новомосковск', 'Тульская обл.,г.Алексин', 'Тульская область,город Алексин',
       'Тульская обл.', 'Тульская обл.,г.Белев', 'Тульская обл.,г.Узловая', 'Тульская обл.,г.Венев',
       'Тульская обл.,г.Богородицк', 'Тульская обл.,г.Одоев', 'Тульская обл.,Киреевский район','Тульская обл.,пос.Куркино');

UPDATE cadet
SET region_of_residence ='Республика Дагестан'
WHERE region_of_residence IN ('р-ка Дагестан', 'Дагестан', 'Махачкала', 'р-на Дагестан');

UPDATE cadet
SET region_of_residence ='Ульяновская область'
WHERE region_of_residence IN ('Ульяновск');

UPDATE cadet
SET region_of_residence ='Республика Абхазия'
WHERE region_of_residence IN ('Респ.Абхазия.,г.Гудаута');

UPDATE cadet
SET region_of_residence ='Хабаровский край'
WHERE region_of_residence IN ('г.Хабаровск', 'Хабаровск');

UPDATE cadet
SET region_of_residence ='Брянская область'
WHERE region_of_residence IN ('Брянск', 'г.Брянск');


UPDATE cadet
SET region_of_residence ='Республика Коми'
WHERE region_of_residence IN ('Республика Коми');

UPDATE cadet
SET region_of_residence ='Приморский край'
WHERE region_of_residence IN ('Владивосток', 'Приморский края', 'Приморский кр.', 'Приморский край,г.Пограничный');

UPDATE cadet
SET region_of_residence ='Забайкальский край'
WHERE region_of_residence IN ('Чита');

UPDATE cadet
SET region_of_residence ='Тюменская область'
WHERE region_of_residence IN ('Тюмень', 'Тобольск', 'Ишим', 'Ялуторовск', 'Тюменская обл.', 'Тюмнь');

UPDATE cadet
SET region_of_residence ='Оренбургская область'
WHERE region_of_residence IN ('Оренбург', 'Орск', 'Бугуруслан', 'оренбупг', 'Оренбургская обл. п Ясный', 'оренбург');

UPDATE cadet
SET region_of_residence ='Саратовская область'
WHERE region_of_residence IN ('Саратов', 'Саратовская обл.,г.Балаково');

UPDATE cadet
SET region_of_residence ='Челябинская область'
WHERE region_of_residence IN ('Челябинск', 'Чебаркуль', 'Кыштым', 'Магнитогорск', 'г.Челябинск', 'Копейск');

UPDATE cadet
SET region_of_residence ='Калининградская область'
WHERE region_of_residence IN ('Калининград');

UPDATE cadet
SET region_of_residence ='Республика Адыгея'
WHERE region_of_residence IN ('Майкоп', 'Респуцблика Адыгея');

UPDATE cadet
SET region_of_residence ='Краснодарский край'
WHERE region_of_residence IN ('Краснодар', 'Краснодарский край', 'Сочи', 'Геленджик', 'Армовир', 'г. Сочи', 'Анапа');

UPDATE cadet
SET region_of_residence ='Пермский край'
WHERE region_of_residence IN ('Пермь');

UPDATE cadet
SET region_of_residence ='Республики Саха'
WHERE region_of_residence IN ('Якутск');

UPDATE cadet
SET region_of_residence ='Ханты-Мансийский автономный округ'
WHERE region_of_residence IN
      ('Сургут', 'Когалым', 'Радужный', 'Нижневартовск', 'Лангепас', 'Н.-Вартовск', 'Югра', 'Мегион', 'Югорск',
       'Нефтьюганск');

UPDATE cadet
SET region_of_residence ='Курганская область'
WHERE region_of_residence IN ('Курган', 'Курганская обл.', 'Коргаполье', 'Кургансая обл.');

UPDATE cadet
SET region_of_residence ='Волгоградская область'
WHERE region_of_residence IN ('Волгоград', 'Волгоградская область');

UPDATE cadet
SET region_of_residence ='Магаданская область'
WHERE region_of_residence IN ('Магаданская обл.,г.Магадан', 'г. Магадан', 'Магаданская обл.');

UPDATE cadet
SET region_of_residence ='Курская область'
WHERE region_of_residence IN ('Курская область', 'Курск', 'г.Курск');

UPDATE cadet
SET region_of_residence ='Самарская область'
WHERE region_of_residence IN ('Самара', 'Самарская обл.,г.Кинель-Черкассы');

UPDATE cadet
SET region_of_residence ='Чувашская Республика'
WHERE region_of_residence IN ('Чувашская Республика', 'г.Чебоксары');

UPDATE cadet
SET region_of_residence ='Нижегородская область'
WHERE region_of_residence IN ('Нижегородская область');

UPDATE cadet
SET region_of_residence ='Смоленская область'
WHERE region_of_residence IN ('Смоленск', 'Смоленская обл.', 'г.Смоленск', 'Смоленская обл.,г.Дорогобуж');

UPDATE cadet
SET region_of_residence ='Республика Крым'
WHERE region_of_residence IN ('Республика Крым,г.Феодосия', 'Республика Крым');

UPDATE cadet
SET region_of_residence ='Луганская область'
WHERE region_of_residence IN ('Украина,Луганская обл.', 'Луганская область', 'Украина,г.Луганск');

UPDATE cadet
SET region_of_residence ='Иркутская область'
WHERE region_of_residence IN ('Иркутская обл.', 'Саянск', 'Иркутская обл.,г.Шелехов', 'Братск');

UPDATE cadet
SET region_of_residence ='Сахалинская область'
WHERE region_of_residence IN ('Южно-Сахалинск', 'Сахалин');

UPDATE cadet
SET region_of_residence ='Красноярский край'
WHERE region_of_residence IN ('Красноярский  кр.', 'Красноярский край', 'Красноярский  край', 'Красноярский кр.');

UPDATE cadet
SET region_of_residence ='Кемеровская область'
WHERE region_of_residence IN ('Кемерово', 'Топки', 'Тайга', 'Журавлево', 'Междуреченск');

UPDATE cadet
SET region_of_residence ='Белгородская область'
WHERE region_of_residence IN ('г.Белгород', 'Белгородская обл.,г.Старый Оскол', 'Белгород');

UPDATE cadet
SET region_of_residence ='Республика Марий Эл'
WHERE region_of_residence IN ('Республика Марий Эл', 'Йошкар-Ола');

UPDATE cadet
SET region_of_residence ='Калужская область'
WHERE region_of_residence IN
      ('Калуга', 'г.Калуга', 'Калужская область', 'Калужская обл.,г.Боровск', 'Калужская обл.,г.Обнинск');

UPDATE cadet
SET region_of_residence ='Республика Башкортостан'
WHERE region_of_residence IN ('Уфа', 'г.Уфа', 'Республика Башкортостан,г.Стерлитамак');

UPDATE cadet
SET region_of_residence ='Омская область'
WHERE region_of_residence IN ('Омск');

UPDATE cadet
SET region_of_residence ='Липецкая область'
WHERE region_of_residence IN ('Липецкая обл.,г.Елец', 'Липецкая область', 'Липецкая обл.');

UPDATE cadet
SET region_of_residence ='Воронежская область'
WHERE region_of_residence IN ('г.Воронеж');

UPDATE cadet
SET region_of_residence ='Новосибирская область'
WHERE region_of_residence IN ('Новосибирск', 'Новосибирская обл.,г.Татарск', 'г. Новосибирск');

UPDATE cadet
SET region_of_residence ='Ростовская область'
WHERE region_of_residence IN ('Ростовская область', 'Ростов', 'Ростов-на-Дону', 'Ростовкая обл.', 'Морозовск');

UPDATE cadet
SET region_of_residence ='Архангельская область'
WHERE region_of_residence IN ('Архангельск', 'Северодвинск');

UPDATE cadet
SET region_of_residence ='Республика Казахстан'
WHERE region_of_residence IN ('Республика Казахстан', 'Казахтан');

UPDATE cadet
SET region_of_residence ='Вологодская область'
WHERE region_of_residence IN ('Череповец', 'Вологодская область');


UPDATE cadet
SET region_of_residence ='Республика Калмыкия'
WHERE region_of_residence IN ('Республика Колмыкия', 'Республика Колмыкия ');

UPDATE cadet
SET region_of_residence ='Свердловская область'
WHERE region_of_residence IN
      ('Екатеринбург', 'Каменск-Уральский', 'Красноуфимск', 'Ирбит', 'Н.Тагил', 'Первоуральск', 'Талица', 'В. Тавда',
       'Асбест', 'Серов', 'Тавда', 'Камышлов', 'Н. Тагил');

UPDATE cadet
SET region_of_residence ='Республика Мордовия'
WHERE region_of_residence IN ('Республика Мордовия');

UPDATE cadet
SET region_of_residence ='Псковская область'
WHERE region_of_residence IN ('Псков', 'Псковская обл.,г.Остров');

UPDATE cadet
SET region_of_residence ='Ямало-Ненецкий автономный округ'
WHERE region_of_residence IN ('Новый Уренгой', 'Ярково ЯНАО', 'Надым', 'Муравленко', 'Ноябрьск');

UPDATE cadet
SET region_of_residence ='Рязанская область'
WHERE region_of_residence IN ('г.Рязань', 'Рязань', 'Рязанская обл.,село Александрово');

UPDATE cadet
SET region_of_residence ='Мурманская область'
WHERE region_of_residence IN ('г.Мурманск', 'Мурманск', 'Кировск');

UPDATE cadet
SET region_of_residence ='Кировская область'
WHERE region_of_residence IN ('Котельнич', 'Киров');

UPDATE cadet
SET region_of_residence ='Чеченская республика'
WHERE region_of_residence IN
      ('Чеченская республика', 'ЧР', 'Грозный', 'Чеченская р-на', 'Чеченская р-ка', 'Чеченская р-на ');

UPDATE cadet
SET region_of_residence ='Ставропольский Край'
WHERE region_of_residence IN ('Ставропольский Край', 'Ставрополь', 'Пятигорск', 'Ставраполь', 'Ставропол. край',
                              'Ставропольский край г. Ессентуки', 'Кисловотск', 'Кисловодск', 'Лермонтов',
                              'г. Кисловодск',
                              'Есентуки', 'Ставропольский край,г.Железноводск', 'Кав. Мин. Воды', 'Кав. МинВоды');

UPDATE cadet
SET region_of_residence ='Орловская область'
WHERE region_of_residence IN ('Орловская область', 'г.Орел', 'город Орел');


UPDATE cadet
SET region_of_residence ='Томская область'
WHERE region_of_residence IN ('Белый Яр', 'Томская обл. Стрежевой', 'Томская область', 'Стрежевой');

UPDATE cadet
SET region_of_residence ='Новгородская область'
WHERE region_of_residence IN ('Новгород', 'В.Новгород', 'Новгородская обл.', 'Великий Новгород');

UPDATE cadet
SET region_of_residence ='Пензенская область'
WHERE region_of_residence IN ('Пензенская область', 'Пенза');

UPDATE cadet
SET region_of_residence ='Тверская область'
WHERE region_of_residence IN ('Тверь', 'Торжок');

UPDATE cadet
SET region_of_residence ='Алтайский край'
WHERE region_of_residence IN ('Алтайский кр.', 'Барнаул', 'Алтайский край.,г.Барнаул');

UPDATE cadet
SET region_of_residence ='Республика Татарстан'
WHERE region_of_residence IN ('Казань', 'Набережные Челны');

UPDATE cadet
SET region_of_residence ='Республика Бурятия'
WHERE region_of_residence IN ('Улан-Удэ', 'Улан-Уде');

UPDATE cadet
SET region_of_residence ='Удмуртская Республика'
WHERE region_of_residence IN ('Ижевск', 'Удмурская Республика', 'Сарапул', 'Балезино');

UPDATE cadet
SET region_of_residence ='Ярославская область'
WHERE region_of_residence IN ('Ярославская область');

UPDATE cadet
SET region_of_residence ='Ивановская область'
WHERE region_of_residence IN ('Ивановская обл.', 'г.Иваново', 'Кинишма', 'Иваново');

UPDATE cadet
SET region_of_residence ='Камчатский край'
WHERE region_of_residence IN ('Камчатский край', 'Петропавловск-камчатский');

UPDATE cadet
SET region_of_residence ='Республики Карелия'
WHERE region_of_residence IN ('Петрозаводск', 'Карелия', 'Петразаводск', 'Беломорск');


UPDATE cadet
SET region_of_residence =''
WHERE region_of_residence IN ('Ужинвал', 'нет данных');

