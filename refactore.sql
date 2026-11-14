UPDATE cadet
SET bite ='ORTHOGNATHIC'
WHERE bite = 'NONE';

UPDATE cadet
SET bite_type ='Ортогнатический'
WHERE bite_type IN ('постоянный', 'ортогнатический', 'Отогнатический', 'отрогнатический', 'Отогнатический');

UPDATE cadet
SET bite_type ='Ортогнатический'
WHERE bite = 'ORTHOGNATHIC';

UPDATE cadet
SET bite_type ='Скученность'
WHERE bite = 'CROWDING';

UPDATE cadet
SET bite_type ='Глубокий'
WHERE bite = 'DEEP';

UPDATE cadet
SET bite_type ='Перекрестный'
WHERE bite = 'CROSS';

UPDATE cadet
SET bite_type ='Прямой'
WHERE bite = 'STRAIGHT';

UPDATE cadet
SET bite_type ='Открытый'
WHERE bite = 'OPEN';

UPDATE cadet
SET bite_type ='Дистальный'
WHERE bite = 'DISTAL';

UPDATE cadet
SET bite_type ='Мезиальный'
WHERE bite_type = 'мезиальный';

UPDATE cadet
SET bite_type ='Сменный'
WHERE bite_type = 'сменный';

UPDATE cadet
SET dental_anomalies =''
WHERE dental_anomalies IN ('-', 'нет', '', 'Нет', 'ортогнатический', 'частичная адентия');

UPDATE cadet
SET dental_anomalies ='Скученность (К07.30)'
WHERE dental_anomalies IN
      ('скученное положение зубов', 'скученность зубных рядов', 'глубокий.скученность зубных рядов');

UPDATE cadet
SET dental_anomalies ='Нарушение межзубных промежутков (К07.33)'
WHERE dental_anomalies IN ('тремы');

UPDATE cadet
SET dental_anomalies ='Чрезмерно глубокий вертикальный прикус (К07.23)'
WHERE dental_anomalies IN
      ('глубокая резцовая окклюзия', 'глубокая окклюзия', 'глубока окклюзия', 'глубокое резцовое перекрытие');


UPDATE cadet
SET dental_anomalies ='Микрогнатия обеих челюстей (К07.05)'
WHERE dental_anomalies IN ('сужение зубных рядов');


UPDATE cadet
SET dental_anomalies ='Открытый прикус (К07.24)'
WHERE dental_anomalies IN ('открытый прикус', 'открытый');

UPDATE cadet
SET dental_anomalies ='Дистальный прикус (К07.20)'
WHERE dental_anomalies IN ('дистальный прикус');

UPDATE cadet
SET dental_anomalies ='Ретенированные или импактные зубы с неправильным положением их или соседних зубов (К07.35)'
WHERE dental_anomalies IN ('ретенированные зубы');

UPDATE cadet
SET dental_anomalies ='Поворот (К07.32)'
WHERE dental_anomalies IN ('тортоаномалия ', 'тортоаномалия отдельных зубов', 'тортоаномалия');

UPDATE cadet
SET dental_anomalies ='Нарушение межзубных промежутков (К07.33)'
WHERE dental_anomalies IN ('дистальный прикус.тремы на верхней челюсти.ретенция 25');

UPDATE cadet
SET dental_anomalies ='Аномалия соотношений зубных дуг не уточненная (К07.29)'
WHERE dental_anomalies IN ('обратное перекрытие справа', 'обратное перекрытие', 'обратное перекрытие челюстей');



SELECT vvk, count(vvk)
FROM cadet
GROUP BY vvk
ORDER BY count(vvk) DESC;

UPDATE cadet
SET vvk = 'Здоров'
WHERE vvk IN ('-', '', 'сменный');

UPDATE cadet
SET vvk = 'Миопия (H52.1)'
WHERE vvk IN ('Миопия слабой степени', 'Миопия средней стенки.Астигматизм',
              'Аллергия (пеницилиновый ряд).Миопия слабой степени.', 'Миопия средней степени', 'Миопия 1 ст.слева');

UPDATE cadet
SET vvk = 'Болезни костно-мышечной системы и соединительной ткани (M00-M99)'
WHERE vvk IN ('Остеохондропатия', 'Плоскостопие 2 степени,Сколиоз грудного отдела', 'Б.Шляттера',
              'Нарушение осанки по сколеотическому типу', 'Продольное плоскостопие 2 степени',
              'Продольное плоскостопие двустороннее', 'Реактивный артрит ,ЮХА.Б-нь Шляттера справа',
              'Аномалия Киммерла,нестабильность шейного отдела позвоночника', 'Б-нь Шляттера',
              'Продольное плоскостопие 1 степени');

UPDATE cadet
SET vvk = 'Мипия (H52.1); Хронический тонзиллит (J35.0); Вазомоторный и аллергический ринит (J30); МАРС; ФИСС'
WHERE vvk IN ('Миопия слабой степени,Хр.тонзилит,Аллергический ринит.МАРС,ФИСС');

UPDATE cadet
SET vvk = 'ВСД (F45.3); МАРС'
WHERE vvk IN ('Церебростенический с-м.Цефалгия.МАРС');

UPDATE cadet
SET vvk = 'Миопия (H52.1); Болезни костно-мышечной системы и соединительной ткани (M00-M99); Ожирение (E66)'
WHERE vvk IN ('Миопия сл.степени,Плоскостопие 2 ст. ,МАРС');

UPDATE cadet
SET vvk = 'Миопия (H52.1); Ожирение (E66)'
WHERE vvk IN ('Миопия слабой степени,Ожирение 1 степени.', 'Астигматизм,избыточный вес.');

UPDATE cadet
SET vvk = 'МАРС; Вазомоторный и аллергический ринит (J30); Болезни костно-мышечной системы и соединительной ткани (M00-M99)'
WHERE vvk IN ('МАРС,Полиноз,ИМТ,Плоскостопие комбинир.1 степени.');

UPDATE cadet
SET vvk = 'Болезни костно-мышечной системы и соединительной ткани (M00-M99); Искривление или смещение перегородки (J34.2)'
WHERE vvk IN ('Нарушение осанки,искривление носовой перегородки');

UPDATE cadet
SET vvk = 'МАРС; Дискинезия желчевыводящих путей (K82.8)'
WHERE vvk IN ('МАРС,ДЖВП');

UPDATE cadet
SET vvk = 'Астма (J45)'
WHERE vvk IN ('Бронхиальная астма');

UPDATE cadet
SET vvk = 'Миопия (H52.1); Болезни костно-мышечной системы и соединительной ткани (M00-M99)'
WHERE vvk IN ('Миопия средней степени,Нестабильность стоп', 'Юношеский идиопатический сколиоз,Спазм аккомодации',
              'Миопия слабой степень,Б-нь Шинца', 'Спазм аккомодации,Люмбаго');

UPDATE cadet
SET vvk = 'Миопия (H52.1); Дискинезия желчевыводящих путей (K82.8); Гастрит и дуоденит (K29)'
WHERE vvk IN ('Миопия средней степени,ДЖВП,хр.гастродуоденит,хр.ВЭБ');

UPDATE cadet
SET vvk = 'МАРС; Миопия (H52.1); Ожирение (E66); Вазомоторный и аллергический ринит (J30)'
WHERE vvk IN ('МАРС,Миопия слабой степени.Полиноз.');

UPDATE cadet
SET vvk = 'МАРС; Ожирение (E66)'
WHERE vvk IN ('МАРС,Избыток массы тела');

UPDATE cadet
SET vvk = 'МАРС; Миопия (H52.1)'
WHERE vvk IN ('Хр.моторные тики.,Миопия слабой степени.');

UPDATE cadet
SET vvk = 'МАРС; Болезни костно-мышечной системы и соединительной ткани (M00-M99)'
WHERE vvk IN ('МАРС,Кифоснолиотическая осанка');

UPDATE cadet
SET vvk = 'Болезни костно-мышечной системы и соединительной ткани (M00-M99)'
WHERE vvk IN ('Плоскостопие 2 степени,Сколиоз грудного отдела');

UPDATE cadet
SET vvk = 'ВСД (F45.3); Дискинезия желчевыводящих путей (K82.8)'
WHERE vvk IN ('ДЖВП,ВСД по в/т типу');

UPDATE cadet
SET vvk = 'Ожирение (E66); Болезни костно-мышечной системы и соединительной ткани (M00-M99)'
WHERE vvk IN ('Ожирение 2 ст,двустроннее плоскостопие 2 степени.');

UPDATE cadet
SET vvk = 'Гиертрофия аденоидов (J35.2); Болезни костно-мышечной системы и соединительной ткани (M00-M99)'
WHERE vvk IN ('Аденоиды2 ст,Продольно-поперечное плоскостопие 1 степени');


UPDATE cadet
SET vvk = 'ВСД (F45.3); Болезни щитовидной железы (E00-E07)'
WHERE vvk IN ('ВСД,Цефалгия,Дифуз.изменение щитовидной железы.');

UPDATE cadet
SET vvk = 'МАРС'
WHERE vvk IN ('МАРС, ПМК 1 ст.');

UPDATE cadet
SET vvk = 'ФИСС'
WHERE vvk IN ('ФИСС');

UPDATE cadet
SET vvk = 'Болезни костно-мышечной системы и соединительной ткани (M00-M99); МАРС'
WHERE vvk IN ('Плосковальгусная стопа,синусовая аритмия');


UPDATE cadet
SET vvk = 'Дискинезия желчевыводящих путей (K82.8); Гастрит и дуоденит (K29)'
WHERE vvk IN ('ДЖВП,хр.гастродуоденит,Дисфункция сфинктера Одди');

UPDATE cadet
SET vvk = 'Искривление или смещение перегородки (J34.2)'
WHERE vvk IN ('Искривление носовой перегородки');

UPDATE cadet
SET vvk = 'Болезни костно-мышечной системы и соединительной ткани (M00-M99)'
WHERE vvk IN ('Нарушение осанки по сколеотическому типу');


UPDATE cadet
SET vvk = 'Миопия (H52.1); МАРС'
WHERE vvk IN ('МАРС,Миопия слабой степени.');

UPDATE cadet
SET vvk = 'Болезни щитовидной железы (E00-E07)'
WHERE vvk IN ('хрон.аутоиммунный тиреоидит');

UPDATE cadet
SET vvk = 'Болезни костно-мышечной системы и соединительной ткани (M00-M99)'
WHERE vvk IN ('Продольное плоскостопие 2 степени');

UPDATE cadet
SET vvk = 'Болезни костно-мышечной системы и соединительной ткани (M00-M99); Атопический дерматит (L20 )'
WHERE vvk IN ('Атопический дерматит,нарушение осанки');

UPDATE cadet
SET vvk = 'Дискинезия желчевыводящих путей (K82.8)'
WHERE vvk IN ('ДЖВП');

UPDATE cadet
SET vvk = 'МАРС'
WHERE vvk IN ('МАРС');

UPDATE cadet
SET vvk = 'ВСД (F45.3)'
WHERE vvk IN ('Носовые кровотечения,Головная боль напряжения');

UPDATE cadet
SET vvk = 'Миопия (H52.1)'
WHERE vvk IN ('Миопия 1 ст.слева');

UPDATE cadet
SET vvk = 'Болезни костно-мышечной системы и соединительной ткани (M00-M99); ФИСС'
WHERE vvk IN ('Сколиоз 1 степени,ФИСС');

UPDATE cadet
SET vvk = 'Болезни щитовидной железы (E00-E07)'
WHERE vvk IN ('Дифф.изменения щитовидной железы');

UPDATE cadet
SET vvk = 'МАРС; Болезни щитовидной железы (E00-E07)'
WHERE vvk IN ('МАРС,Эутиреоз');

UPDATE cadet
SET vvk = 'Миопия (H52.1); ФИСС'
WHERE vvk IN ('Миопия слабой степени,ФИСС');

UPDATE cadet
SET vvk = 'ВСД (F45.3)'
WHERE vvk IN ('Церебростенический синдром ШТС слева');

UPDATE cadet
SET vvk = 'МАРС; Болезни костно-мышечной системы и соединительной ткани (M00-M99)'
WHERE vvk IN ('МАРС,нарушение осанки');

UPDATE cadet
SET vvk = 'Миопия (H52.1); МАРС; Болезни щитовидной железы (E00-E07)'
WHERE vvk IN ('Миопия слабой степени,Дифф.изменения щитовидной железы,МАРС');

UPDATE cadet
SET vvk = 'Болезни костно-мышечной системы и соединительной ткани (M00-M99); Дискинезия желчевыводящих путей (K82.8)'
WHERE vvk IN ('Плосковальгусная стома,ДЖВП');

UPDATE cadet
SET dental_anomalies = 'Скученность (К07.30)'
WHERE bite_type = 'Скученность'
  AND dental_anomalies = '';

UPDATE cadet
SET dental_anomalies = 'Поворот (К07.32)'
WHERE bite_type = 'Тортоаномалия'
  AND dental_anomalies = '';

UPDATE cadet
SET dental_anomalies = 'Нарушение межзубных промежутков (К07.33)',
    bite_type='Ортогнатический'
WHERE bite_type = 'Диастема/Тремы';

UPDATE cadet
SET dental_anomalies = 'Чрезмерно глубокий вертикальный прикус (К07.23)'
WHERE bite_type = 'Глубокий'
  AND dental_anomalies = '';

UPDATE cadet
SET age = 15
WHERE age = 115;

UPDATE cadet
SET age = 2019 - EXTRACT(YEAR FROM birthday)
WHERE age = 0
  AND birthday > '2000-01-01 00:00:00.000000';

UPDATE cadet
SET preventive_actions = 'Да'
WHERE preventive_actions IN ('Да', 'да');

UPDATE cadet
SET preventive_actions = 'Нет'
WHERE preventive_actions IN ('Нет', '', 'нет', 'нет данных', '-');


UPDATE cadet
SET preventive_actions_details = ''
WHERE preventive_actions_details IN ('-');

UPDATE cadet
SET preventive_actions_details = 'Проф. осмотры'
WHERE preventive_actions_details IN
      ('проф. осмотры', 'осмотр', 'проф. осмотры, лекции', 'лечение зубов', 'исправление прикуса');

UPDATE cadet
SET preventive_actions_details = 'Уроки гигиены'
WHERE preventive_actions_details IN
      ('Уроки гигиены, проф. осмотры', 'осмотр', 'проф. осмотры, лекции', 'профессиональную гигиену полости рта',
       'профилактичесая гигиена полости рта', 'профессиональная чистка зубов');

SELECT preventive_actions_details, count(preventive_actions_details)
FROM cadet
GROUP BY preventive_actions_details
ORDER BY count(preventive_actions_details) DESC;



UPDATE cadet
SET bite_type ='Ортогнатический'
WHERE bite = 'CROWDING'
  AND bite_type = 'Скученность'
  AND dental_anomalies = 'Скученность (К07.30)';

UPDATE cadet
SET dental_anomalies ='Дистальный прикус (К07.20)'
WHERE bite = 'DISTAL'
  AND bite_type = 'Дистальный';

UPDATE cadet
SET dental_anomalies ='Аномалия соотношений зубных дуг не уточненная (К07.29)'
WHERE bite = 'CROSS'
  AND bite_type = 'Перекрестный';

UPDATE cadet
SET dental_anomalies ='Открытый прикус (К07.24)'
WHERE bite = 'OPEN'
  AND bite_type = 'Открытый';

UPDATE cadet
SET bite_type ='Мезиальный'
WHERE bite = 'MESIAL'
  AND bite_type = 'Ортогнатический';

UPDATE cadet
SET dental_anomalies ='Мезиальный прикус (К07.21)'
WHERE bite = 'MESIAL'
  AND bite_type = 'Мезиальный';

UPDATE cadet
SET bite_type ='Ортогнатический'
WHERE bite = 'TORTOANOMALY'
  AND bite_type = 'Тортоаномалия';

UPDATE cadet
SET bite_type ='Ортогнатический'
WHERE bite = 'DIASTIMA'
  AND bite_type = 'Сменный';

UPDATE cadet
SET bite_type ='Ортогнатический'
WHERE bite = 'DIASTIMA'
  AND bite_type = 'Ортогнатический';

UPDATE cadet
SET dental_anomalies ='Чрезмерно глубокий вертикальный прикус (К07.23); Скученность (К07.30)'
WHERE bite = 'DEEP'
  AND bite_type = 'Глубокий'
  AND dental_anomalies = 'Скученность (К07.30)';

UPDATE cadet
SET dental_anomalies ='Микрогнатия обеих челюстей (К07.05); Скученность (К07.30)'
WHERE bite = 'CROWDING'
  AND bite_type = 'Скученность'
  AND dental_anomalies = 'Микрогнатия обеих челюстей (К07.05)';

UPDATE cadet
SET bite_type ='Ортогнатический'
WHERE bite = 'CROWDING'
  AND bite_type = 'Скученность';


UPDATE cadet
SET dental_anomalies ='Дистальный прикус (К07.20); Нарушение межзубных промежутков (К07.33)'
WHERE bite = 'DISTAL'
  AND bite_type = 'Дистальный'
  AND dental_anomalies = 'Нарушение межзубных промежутков (К07.33)';

UPDATE cadet
SET dental_anomalies ='Поворот (К07.32)'
WHERE bite = 'TORTOANOMALY'
  AND bite_type = 'Сменный'
  AND dental_anomalies = 'Микрогнатия обеих челюстей (К07.05)';

UPDATE cadet
SET bite_type ='Ортогнатический'
WHERE bite = 'TORTOANOMALY'
  AND dental_anomalies = 'Поворот (К07.32)'
  AND bite_type = 'Сменный';

UPDATE cadet
SET dental_anomalies ='Открытый прикус (К07.24); Поворот (К07.32)'
WHERE bite = 'OPEN'
  AND bite_type = 'Открытый'
  AND dental_anomalies = 'Поворот (К07.32)';

UPDATE cadet
SET dental_anomalies ='Скученность (К07.30); Поворот (К07.32)'
WHERE bite = 'CROWDING'
  AND bite_type = 'Скученность'
  AND dental_anomalies = 'Поворот (К07.32)';

UPDATE cadet
SET bite_type ='Ортогнатический'
WHERE bite = 'CROWDING'
  AND bite_type = 'Скученность'
  AND dental_anomalies = 'Поворот (К07.32)';

UPDATE cadet
SET dental_anomalies ='Нарушение межзубных промежутков (К07.33)'
WHERE bite = 'DIASTIMA'
  AND bite_type = 'Ортогнатический';

UPDATE cadet
SET dental_anomalies ='Отсутвуют'
WHERE dental_anomalies = '';

UPDATE cadet
SET health_group = 1
WHERE health_group = 0;

UPDATE cadet
SET health_group = 3
WHERE health_group = 4;

UPDATE cadet
SET age = 11
WHERE age = 0
  AND group_id IN (SELECT id
                   FROM groups
                   WHERE name IN (
                                  '1 курс',
                                  '1 рота 5 класс',
                                  '5 рота',
                                  'Папка №3 +'
                       ));

UPDATE cadet
SET age = 12
WHERE age = 0
  AND group_id IN (SELECT id
                   FROM groups
                   WHERE name IN (
                                  '2 курс',
                                  '1 рота 5 класс',
                                  '2 рота',
                                  '6 рота',
                                  'Папка №4 +'
                       ));

UPDATE cadet
SET age = 13
WHERE age = 0
  AND group_id IN (SELECT id
                   FROM groups
                   WHERE name IN (
                                  '2 курс',
                                  '6a',
                                  '6б',
                                  '7 рота',
                                  'Папка №1 +'
                       ));

UPDATE cadet
SET age = 14
WHERE age = 0
  AND group_id IN (SELECT id
                   FROM groups
                   WHERE name IN (
                                  '3 курс',
                                  'Папка №5 +'
                       ));

UPDATE cadet
SET age = 15
WHERE age = 0
  AND group_id IN (SELECT id
                   FROM groups
                   WHERE name IN (
                                  '4 курс',
                                  'Папка №2 +'
                       ));

UPDATE cadet
SET age = 16
WHERE age = 0
  AND group_id IN (SELECT id
                   FROM groups
                   WHERE name IN (
                       '5 курс'
                       ));

UPDATE cadet
SET age = 17
WHERE age = 0
  AND group_id IN (SELECT id
                   FROM groups
                   WHERE name IN (
                       'Папка №6 +'
                       ));
