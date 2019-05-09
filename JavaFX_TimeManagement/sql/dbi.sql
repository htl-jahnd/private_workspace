insert into visitors values (1,'Otto',SDO.Geometry(2000,NULL,SDO.POINT_TYPE(120,130,NULL),NULL,NULL));

SELECT *
  FROM D4B12.VISITORS v, D4B12.VILLAGE b
  WHERE SDO_CONTAINS(b.building,
            v.POSITION
            ) = 'TRUE';
