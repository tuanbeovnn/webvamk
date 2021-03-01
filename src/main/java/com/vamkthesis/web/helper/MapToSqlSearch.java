package com.vamkthesis.web.helper;

import java.util.Map;
import java.util.Set;

public class MapToSqlSearch {
    public static String toSql(Map<String,Object> map, String prefix) {
        StringBuilder where = new StringBuilder();
        if(map==null) return "";
        Set<String> set = map.keySet();
        for (String key : set) {
            Object obj = map.get(key);

            if(obj instanceof String) {
                where.append(" AND "+ prefix +"." + key +" LIKE '%"+ obj + "%'");
            }else if(obj instanceof Integer || obj instanceof Long ) {
                where.append(" AND "+ prefix +"." + key +"="+ String.valueOf(obj));
            }
        }
        return where.toString();
    }
}
