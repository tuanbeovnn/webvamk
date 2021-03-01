package com.vamkthesis.web.helper;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class ObjectToMap {
    public static <T> Map<String,Object> toMap(Object dto) {
        Map<String,Object> map = new HashMap<>();
        try {
            Class<?> cl = dto.getClass();
            while(cl!=null) {
                Field[] fields = cl.getDeclaredFields();

                for(Field field : fields) {
                    field.setAccessible(true);
                    Object obj = field.get(dto);

                    if(obj!=null) {
                        map.put(field.getName(), obj);
                    }
                }
                cl = cl.getSuperclass();
            }
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return map;
    }
}
