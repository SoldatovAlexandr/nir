--- Подсчет распространенности флюороза
select c.age,
       round(avg(CASE dc.enamel_fluorosis
                     WHEN 0 THEN 0
                     ELSE 1
           END) * 100, 2)
from dental_condition dc
         inner join public.cadet c on c.id = dc.cadet_id
group by c.age
order by c.age;


--- Подсчет распространенности пятнист, гипоплазия
select c.age,
       round(avg(CASE es.max
                     WHEN 0 THEN 0
                     WHEN -1 THEN 0
                     ELSE 1
           END) * 100, 2)
from (select es.cadet_id, max(es.value)
      from enamel_spotting es
      group by es.cadet_id) es
         inner join public.cadet c on c.id = es.cadet_id
group by c.age
order by c.age;

---- Подсчет средней секстанты по возрастам
with ptc as (select c.id,
                    c.age,
                    (
                            (CASE max(pt1.value)
                                 WHEN 0 THEN 0
                                 WHEN -1 THEN 0
                                 ELSE 1
                                END) +
                            (CASE max(pt2.value)
                                 WHEN 0 THEN 0
                                 WHEN -1 THEN 0
                                 ELSE 1
                                END) +
                            (CASE max(pt3.value)
                                 WHEN 0 THEN 0
                                 WHEN -1 THEN 0
                                 ELSE 1
                                END) +
                            (CASE max(pt4.value)
                                 WHEN 0 THEN 0
                                 WHEN -1 THEN 0
                                 ELSE 1
                                END) +
                            (CASE max(pt5.value)
                                 WHEN 0 THEN 0
                                 WHEN -1 THEN 0
                                 ELSE 1
                                END) +
                            (CASE max(pt6.value)
                                 WHEN 0 THEN 0
                                 WHEN -1 THEN 0
                                 ELSE 1
                                END)
                        ) as secstant

             from cadet c
                      left join periodontal_tissue pt1 on c.id = pt1.cadet_id and pt1.number between 14 and 17
                      left join periodontal_tissue pt2
                                on c.id = pt2.cadet_id and
                                   (pt2.number between 11 and 13 or pt2.number between 21 and 23)
                      left join periodontal_tissue pt3 on c.id = pt3.cadet_id and pt3.number between 24 and 27
                      left join periodontal_tissue pt4 on c.id = pt4.cadet_id and pt4.number between 34 and 37
                      left join periodontal_tissue pt5
                                on c.id = pt5.cadet_id and
                                   (pt5.number between 31 and 33 or pt5.number between 41 and 43)
                      left join periodontal_tissue pt6 on c.id = pt6.cadet_id and pt6.number between 44 and 47
             group by c.id)
select ptc.age, round(avg(ptc.secstant), 2)
from ptc
group by ptc.age;


---- Среднее количество секстантов с признаками поражения тканей пародонта
with ptc as (select c.age    as age,
                    (CASE max(pt1.value)
                         WHEN 0 THEN 0
                         WHEN -1 THEN 0
                         ELSE max(pt1.value)
                        END) as s1,
                    (CASE max(pt2.value)
                         WHEN 0 THEN 0
                         WHEN -1 THEN 0
                         ELSE max(pt2.value)
                        END) as s2,
                    (CASE max(pt3.value)
                         WHEN 0 THEN 0
                         WHEN -1 THEN 0
                         ELSE max(pt3.value)
                        END) as s3,
                    (CASE max(pt4.value)
                         WHEN 0 THEN 0
                         WHEN -1 THEN 0
                         ELSE max(pt4.value)
                        END) as s4,
                    (CASE max(pt5.value)
                         WHEN 0 THEN 0
                         WHEN -1 THEN 0
                         ELSE max(pt5.value)
                        END) as s5,
                    (CASE max(pt6.value)
                         WHEN 0 THEN 0
                         WHEN -1 THEN 0
                         ELSE max(pt6.value)
                        END) as s6

             from cadet c
                      left join periodontal_tissue pt1 on c.id = pt1.cadet_id and pt1.number between 14 and 17
                      left join periodontal_tissue pt2
                                on c.id = pt2.cadet_id and
                                   (pt2.number between 11 and 13 or pt2.number between 21 and 23)
                      left join periodontal_tissue pt3 on c.id = pt3.cadet_id and pt3.number between 24 and 27
                      left join periodontal_tissue pt4 on c.id = pt4.cadet_id and pt4.number between 34 and 37
                      left join periodontal_tissue pt5
                                on c.id = pt5.cadet_id and
                                   (pt5.number between 31 and 33 or pt5.number between 41 and 43)
                      left join periodontal_tissue pt6 on c.id = pt6.cadet_id and pt6.number between 44 and 47
             group by c.id)
select ptc.age
     , round((
                             COUNT(*) FILTER (where ptc.s1 = 0) +
                             COUNT(*) FILTER (where ptc.s2 = 0) +
                             COUNT(*) FILTER (where ptc.s3 = 0) +
                             COUNT(*) FILTER (where ptc.s4 = 0) +
                             COUNT(*) FILTER (where ptc.s5 = 0) +
                             COUNT(*) FILTER (where ptc.s6 = 0)
                 ) * 1.0 / COUNT(*), 2) as healthy
     , round((
                             COUNT(*) FILTER (where ptc.s1 = 1) +
                             COUNT(*) FILTER (where ptc.s2 = 1) +
                             COUNT(*) FILTER (where ptc.s3 = 1) +
                             COUNT(*) FILTER (where ptc.s4 = 1) +
                             COUNT(*) FILTER (where ptc.s5 = 1) +
                             COUNT(*) FILTER (where ptc.s6 = 1)
                 ) * 1.0 / COUNT(*), 2) as bleeding
     , round((
                             COUNT(*) FILTER (where ptc.s1 = 2) +
                             COUNT(*) FILTER (where ptc.s2 = 2) +
                             COUNT(*) FILTER (where ptc.s3 = 2) +
                             COUNT(*) FILTER (where ptc.s4 = 2) +
                             COUNT(*) FILTER (where ptc.s5 = 2) +
                             COUNT(*) FILTER (where ptc.s6 = 2)
                 ) * 1.0 / COUNT(*), 2) as tartar
     , round((
                             COUNT(*) FILTER (where ptc.s1 = 3) +
                             COUNT(*) FILTER (where ptc.s2 = 3) +
                             COUNT(*) FILTER (where ptc.s3 = 3) +
                             COUNT(*) FILTER (where ptc.s4 = 3) +
                             COUNT(*) FILTER (where ptc.s5 = 3) +
                             COUNT(*) FILTER (where ptc.s6 = 3)
                 ) * 1.0 / COUNT(*), 2) as pocket4
     , round((
                             COUNT(*) FILTER (where ptc.s1 = 4) +
                             COUNT(*) FILTER (where ptc.s2 = 4) +
                             COUNT(*) FILTER (where ptc.s3 = 4) +
                             COUNT(*) FILTER (where ptc.s4 = 4) +
                             COUNT(*) FILTER (where ptc.s5 = 4) +
                             COUNT(*) FILTER (where ptc.s6 = 4)
                 ) * 1.0 / COUNT(*), 2) as pocket6
     , round((
                             COUNT(*) FILTER (where ptc.s1 = 9) +
                             COUNT(*) FILTER (where ptc.s2 = 9) +
                             COUNT(*) FILTER (where ptc.s3 = 9) +
                             COUNT(*) FILTER (where ptc.s4 = 9) +
                             COUNT(*) FILTER (where ptc.s5 = 9) +
                             COUNT(*) FILTER (where ptc.s6 = 9)
                 ) * 1.0 / COUNT(*), 2) as not_registered
from ptc
group by ptc.age;



--------------------Распростаненность (%) признаков поражения тканей пародонта

with ptc as (select c.id,
                    (CASE max(pt.value)
                         WHEN 0 THEN 0
                         WHEN -1 THEN 0
                         ELSE max(pt.value)
                        END) as value
             from cadet c
                      left join periodontal_tissue pt on c.id = pt.cadet_id
             group by c.id)
select c.age
     , round(COUNT(*) FILTER (where ptc.value = 0) * 100.0 / COUNT(*), 3) as healthy
     , round(COUNT(*) FILTER (where ptc.value = 1) * 100.0 / COUNT(*), 3) as bleeding
     , round(COUNT(*) FILTER (where ptc.value = 2) * 100.0 / COUNT(*), 3) as tartar
     , round(COUNT(*) FILTER (where ptc.value = 3) * 100.0 / COUNT(*), 3) as pocket4
     , round(COUNT(*) FILTER (where ptc.value = 4) * 100.0 / COUNT(*), 3) as pocket6
     , round(COUNT(*) FILTER (where ptc.value = 9) * 100.0 / COUNT(*), 3) as not_registered
from ptc
         join cadet c on ptc.id = c.id
group by c.age
order by c.age;

--------------------



--- нуждаемость в лечении
with has_caries as (select ca.id as id,
				ca.age as age,
	(CASE COUNT(*) FILTER (where t.value = 1 or t.value = 2)
	WHEN 0 THEN 0
	ELSE 1 END) as has_caries
from tooth t
inner join year_dental_condition ydc on t.year_dental_condition_id = ydc.id and ydc.year = '2017-2018'
inner join dental_condition dc on dc.id = ydc.dental_condition_id
inner join cadet ca on ca.id = dc.cadet_id
group by ca.id
)
select
age
, round(count(*) filter (where hs.has_caries = 1) * 100.0/count(*), 3) as persent
from has_caries hs
group by age
order by age;

--------------------

select
ca.id as id,
(CASE ca.age
	 WHEN 10 THEN '10-12'
	 WHEN 11 THEN '10-12'
	 WHEN 12 THEN '10-12'
	 WHEN 13 THEN '13-15'
	 WHEN 14 THEN '13-15'
	 WHEN 15 THEN '13-15'
	 ELSE '16-18' END
	) as age,
COUNT(*) FILTER (where t.value in (1,2,3,4,5,6,7)) as cpu_cp
from year_dental_condition ydc
left join tooth t on t.year_dental_condition_id = ydc.id and ydc.year = '2017-2018'
join dental_condition dc on dc.id = ydc.dental_condition_id
join cadet ca on ca.id = dc.cadet_id
group by ca.id
order by age

--------------------


with delta_cpu as (with cpu as (select
ca.id as id
,(case ydc.year
	WHEN '2011-2012' THEN 1
	WHEN '2012-2013' THEN 2
	WHEN '2013-2014' THEN 3
	WHEN '2014-2015' THEN 4
	WHEN '2015-2016' THEN 5
	WHEN '2016-2017' THEN 6
	WHEN '2017-2018' THEN 7
	END
) as year_count
,ca.age as age
,COUNT(*) FILTER (where t.value in (1, 2, 3, 6, 7, 4, 5)) as cpu
from year_dental_condition ydc
join tooth t on t.year_dental_condition_id = ydc.id
join dental_condition dc on dc.id = ydc.dental_condition_id
join cadet ca on ca.id = dc.cadet_id
group by ca.id, ydc.year
)
select
c1.id as cadet_id
,(c2.cpu - c1.cpu) as delta
,(c1.age + c2.year_count - 7) as age
, c2.year_count as year_count
from cpu c1
join cpu c2 on c1.id = c2.id and c1.year_count = c2.year_count - 1
)
select
age
, round((avg(dc.delta) FILTER ( where dc.year_count = 2)), 3) as year12
, round((avg(dc.delta) FILTER ( where dc.year_count = 3)), 3) as year13
, round((avg(dc.delta) FILTER ( where dc.year_count = 4)), 3) as year14
, round((avg(dc.delta) FILTER ( where dc.year_count = 5)), 3) as year15
, round((avg(dc.delta) FILTER ( where dc.year_count = 6)), 3) as year16
, round((avg(dc.delta) FILTER ( where dc.year_count = 7)), 3) as year17
from delta_cpu dc
group by age
order by age
;


------------------------------

with avg_cpu as (with delta_cpu as (with cpu as (select
ca.id as id
,(case ydc.year
	WHEN '2011-2012' THEN 1
	WHEN '2012-2013' THEN 2
	WHEN '2013-2014' THEN 3
	WHEN '2014-2015' THEN 4
	WHEN '2015-2016' THEN 5
	WHEN '2016-2017' THEN 6
	WHEN '2017-2018' THEN 7
	END
) as year_count
,ca.age as age
,COUNT(*) FILTER (where t.value in (1, 2, 3, 6, 7, 4, 5)) as cpu
from year_dental_condition ydc
join tooth t on t.year_dental_condition_id = ydc.id
join dental_condition dc on dc.id = ydc.dental_condition_id
join cadet ca on ca.id = dc.cadet_id
group by ca.id, ydc.year
)
select
c1.id as cadet_id
,(c2.cpu - c1.cpu) as delta
,(c1.age + c2.year_count - 7) as age
, c2.year_count as year_count
from cpu c1
join cpu c2 on c1.id = c2.id and c1.year_count = c2.year_count - 1
)
select
age
, round((avg(dc.delta) FILTER ( where dc.year_count = 2)), 3) as year12
, round((avg(dc.delta) FILTER ( where dc.year_count = 3)), 3) as year13
, round((avg(dc.delta) FILTER ( where dc.year_count = 4)), 3) as year14
, round((avg(dc.delta) FILTER ( where dc.year_count = 5)), 3) as year15
, round((avg(dc.delta) FILTER ( where dc.year_count = 6)), 3) as year16
, round((avg(dc.delta) FILTER ( where dc.year_count = 7)), 3) as year17
from delta_cpu dc
group by age
order by age
),
delta_cpu as (with cpu as (select
ca.id as id
,(case ydc.year
	WHEN '2011-2012' THEN 1
	WHEN '2012-2013' THEN 2
	WHEN '2013-2014' THEN 3
	WHEN '2014-2015' THEN 4
	WHEN '2015-2016' THEN 5
	WHEN '2016-2017' THEN 6
	WHEN '2017-2018' THEN 7
	END
) as year_count
,ca.age as age
,COUNT(*) FILTER (where t.value in (1, 2, 3, 6, 7, 4, 5)) as cpu
from year_dental_condition ydc
join tooth t on t.year_dental_condition_id = ydc.id
join dental_condition dc on dc.id = ydc.dental_condition_id
join cadet ca on ca.id = dc.cadet_id
group by ca.id, ydc.year
)
select
c1.id as cadet_id
,(c2.cpu - c1.cpu) as delta
,(c1.age + c2.year_count - 7) as age
, c2.year_count as year_count
from cpu c1
join cpu c2 on c1.id = c2.id and c1.year_count = c2.year_count - 1
)
select
dc.age
, round(sqrt(sum((dc.delta - avg_cpu.year12)*(dc.delta - avg_cpu.year12)) FILTER ( where dc.year_count = 2)/ count(*)  FILTER ( where dc.year_count = 2)),2)
, round(sqrt(sum((dc.delta - avg_cpu.year13)*(dc.delta - avg_cpu.year13)) FILTER ( where dc.year_count = 3)/ count(*)  FILTER ( where dc.year_count = 3)),2)
, round(sqrt(sum((dc.delta - avg_cpu.year14)*(dc.delta - avg_cpu.year14)) FILTER ( where dc.year_count = 4)/ count(*)  FILTER ( where dc.year_count = 4)),2)
, round(sqrt(sum((dc.delta - avg_cpu.year15)*(dc.delta - avg_cpu.year15)) FILTER ( where dc.year_count = 5)/ count(*)  FILTER ( where dc.year_count = 5)),2)
, round(sqrt(sum((dc.delta - avg_cpu.year16)*(dc.delta - avg_cpu.year16)) FILTER ( where dc.year_count = 6)/ count(*)  FILTER ( where dc.year_count = 6)),2)
, round(sqrt(sum((dc.delta - avg_cpu.year17)*(dc.delta - avg_cpu.year17)) FILTER ( where dc.year_count = 7)/ count(*)  FILTER ( where dc.year_count = 7)),2)
from delta_cpu dc
join avg_cpu on avg_cpu.age = dc.age
group by dc.age
order by dc.age
;

--------------------------------------------


