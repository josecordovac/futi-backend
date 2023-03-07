package com.futi.app.utils

object Queries {

    const val FIND_FIELD = "SELECT  " +
            " fs.`day` , (fs.start_time / 60) AS start_time, (fs.end_time/60) AS end_time," +
            " fsp.price FROM field_schedule fs " +
            " INNER JOIN field_schedule_price fsp ON " +
            " fs.field_id = fsp.field_id" +
            " WHERE fs.field_id = #{id}"

    const val SEARCH_ENCLOSURE = "SELECT" +
            " e.enclosure_id AS enclosureId," +
            " b.logourl ," +
            " e.name ," +
            " b.documentnum AS ruc," +
            " b.website AS web," +
            " (SELECT GROUP_CONCAT(snb.url) FROM social_network_business snb WHERE snb.businessid = e.business_id) AS social," +
            " e.email," +
            " e.telephone1 AS phone," +
            " e.district_id AS district," +
            " 1 AS available," +
            " (SELECT AVG(score) FROM business_rating br WHERE br.businessid = e.business_id) AS rating," +
            " e.latitude ," +
            " e.longitude," +
            " e.direction," +
            " (SELECT GROUP_CONCAT(p.id) FROM enclosure_service es, parameters p WHERE p.id = es.parameterid  AND es.enclosureid = e.enclosure_id" +
            " AND p.typeid = 2) AS serviceIds" +
            " FROM" +
            " enclosure e INNER JOIN business b  ON b.business_id = e.business_id " +
            " WHERE" +
            " e.enclosure_id = #{id}"

    const val COUNT_SEARCH_BUSINESS = "<script>SELECT COUNT( *) FROM ( SELECT e.district_id,f.capacitance" +
            " FROM enclosure e INNER JOIN fields f " +
            "ON e.enclosure_id = f.enclosure_id INNER JOIN field_schedule  fs " +
            "ON fs.field_id = f.field_id " +
            "<where>" +
            "<if test='day != null and day !=0'>" +
            " AND fs.`day` = #{day} " +
            "</if>" +
            "<if test='districtIds != null and districtIds.size > 0'>" +
            " AND e.district_id IN" +
            " <foreach collection='districtIds' item='item' index='index' open='(' separator=',' close=')'>" +
            " #{item}" +
            " </foreach>" +
            "</if>" +
            "<if test='playersIds != null and playersIds.size > 0'>" +
            " AND f.capacitance IN" +
            " <foreach collection='playersIds' item='item' index='index' open='(' separator=',' close=')'>" +
            " #{item}" +
            " </foreach>" +
            "</if>" +
            "</where>" +
            "GROUP BY e.enclosure_id ) s </script>"

    const val SP_FIND_FIELD_ENCLOSURE = "{ CALL SP_FIND_FIELD_ENCLOSURE(" +
            "#{id, mode=IN, jdbcType=INTEGER}" +
            ",#{day, mode=IN, jdbcType=DATE}" +
            ",#{fromHour, mode=IN, jdbcType=INTEGER}" +
            ",#{toHour, mode=IN, jdbcType=INTEGER}" +
            ",#{playersIds, mode=IN, jdbcType=VARCHAR}" +
            ")}"

    const val FIND_FIELD_ENCLOSURE = "<script>SELECT" +
            " f.field_id AS id," +
            " f.capacitance AS playersId," +
            " f.width ," +
            " f.`length` ," +
            " fs.price," +
            " (fs.price / f.capacitance ) AS pricePerPlayer," +
            " (p.price / f.capacitance ) AS promoPricePerPlayer," +
            " 1 AS available," +
            " f.name " +
            " FROM" +
            " fields f" +
            " INNER JOIN field_schedule fs ON" +
            " f.field_id = fs.field_id" +
            " LEFT JOIN field_promotion fp ON" +
            " fp.field_id = f.field_id" +
            " LEFT JOIN promotion p ON" +
            " p.promotion_id = fp.promotion_id" +
            " <where>" +
            " <if test='id != null and id > 0'>" +
            " AND f.enclosure_id = #{id}" +
            " </if>" +
            " <if test='day != null and day > 0'>" +
            " AND fs.`day` = #{day}" +
            " </if>" +
            " </where>" +
            " </script>"

    const val FIND_FIELD_ENCLOSURE_WEB = "SELECT" +
            " f.field_id AS id," +
            " f.width ," +
            " f.`length` ," +
            " f.capacity," +
            " 1 AS available," +
            " f.name" +
            " FROM" +
            " fields f" +
            " WHERE" +
            " f.enclosure_id = #{id}"

    const val SP_SEARCH_ENCLOSURE = "{ CALL SP_SEARCH_ENCLOSURE( #{id, mode=IN, jdbcType=INTEGER}" +
            ",#{userId, mode=IN, jdbcType=INTEGER})}"

    const val SP_SEARCH = "{ CALL SP_SEARCH( #{vday, mode=IN, jdbcType=DATE}" +
            ",#{from_hour, mode=IN, jdbcType=INTEGER}" +
            ",#{to_hour, mode=IN, jdbcType=INTEGER}" +
            ",#{players, mode=IN, jdbcType=VARCHAR}" +
            ",#{districts, mode=IN, jdbcType=VARCHAR}" +
            ",#{favorite, mode=IN, jdbcType=INTEGER}" +
            ",#{user_id, mode=IN, jdbcType=INTEGER}" +
            ")}"

    const val SEARCH_BUSINESS = "<script>SELECT " +
            " s.name, " +
            " s.affiliated , " +
            " s.imageurl, " +
            " s.enclosure_id AS enclosureId , " +
            " MIN(s.minprice) AS price, " +
            " s.capacitances AS playersIds, " +
            " s.latitude, " +
            " s.longitude, " +
            " (s.start_time / 60) AS fromHour , " +
            " (s.end_time / 60) AS toHour, " +
            " (s.minprice / s.capacitance) AS pricePerPlayer, " +
            " s.preciopromo AS promoPrice, " +
            " (s.preciopromo / s.capacitance) AS promoPricePerPlayer, " +
            " s.rating, " +
            " s.day " +
            "FROM " +
            " ( " +
            " SELECT " +
            "  b.business_id , " +
            "  b.name, " +
            "  b.affiliated, " +
            "  ei.imageurl, " +
            "  e.enclosure_id, " +
            "  e.latitude , " +
            "  e.longitude, " +
            "  fs.start_time, " +
            "  fs.end_time , " +
            "  GROUP_CONCAT(f.capacitance) AS capacitances, " +
            "  f.capacitance , " +
            "  fs.price AS minprice, " +
            "  fs.`day`, " +
            "  p.price AS preciopromo, " +
            "  (SELECT AVG(br.score) FROM business_rating br WHERE br.businessid = b.business_id ) AS rating " +
            " FROM " +
            "  enclosure e " +
            " INNER JOIN business b ON  " +
            "  b.business_id = e.business_id  " +
            " INNER JOIN fields f ON " +
            "  f.enclosure_id = e.enclosure_id " +
            " INNER JOIN field_schedule fs ON " +
            "   fs.field_id = f.field_id " +
            " LEFT JOIN field_promotion fp ON " +
            "  fp.field_id = f.field_id  " +
            " LEFT JOIN promotion p ON " +
            "  fp.promotion_id = p.promotion_id " +
            " LEFT JOIN enclosure_image ei ON  " +
            "  (ei.enclosureid  = e.enclosure_id  AND ei.enabled  = 1) " +
            "<where>" +
            "<if test='day != null and day !=0'>" +
            " AND fs.`day` = #{day}" +
            "</if>" +
            "<if test='fromHour > 0 and toHour > 0'>" +
            " AND ((#{fromHour} * 60) &gt;= fs.start_time AND (#{toHour}*60) &lt;= fs.end_time)" +
            "</if>" +
            "<if test='playersIds != null and playersIds.size > 0'>" +
            " AND f.capacitance IN" +
            "<foreach collection='playersIds' item='item' index='index' open='(' separator=',' close=')'>" +
            "   #{item}" +
            "</foreach>" +
            "</if>" +
            "<if test='districtIds != null and districtIds.size > 0'>" +
            " AND e.district_id IN" +
            "<foreach collection='districtIds' item='item' index='index' open='(' separator=',' close=')'>" +
            "   #{item}" +
            "</foreach>" +
            "</if>" +
            "</where>" +
            " GROUP BY " +
            "  e.enclosure_id, " +
            "  fs.price) s " +
            "GROUP by " +
            " s.enclosure_id" +
            "<if test='orderBy != null and orderBy == 6'>" +
            "  ORDER BY price ASC" +
            "</if>" +
            "<if test='orderBy != null and orderBy == 7'>" +
            "  ORDER BY price DESC" +
            "</if>" +
            "<if test='orderBy != null and orderBy == 9'>" +
            "  ORDER BY s.rating ASC" +
            "</if>" +
            " <if test='endLimit != null and endLimit > 0'>" +
            "    LIMIT #{startLimit},#{endLimit}" +
            " </if>" +
            "</script>"

}