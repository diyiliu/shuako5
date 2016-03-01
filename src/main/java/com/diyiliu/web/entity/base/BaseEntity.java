package com.diyiliu.web.entity.base;

import com.diyiliu.support.annotation.TableColumn;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.HashMap;

/**
 * Description: BaseEntity
 * Author: DIYILIU
 * Update: 2016-02-19 14:02
 */
public class BaseEntity implements Serializable {

    public HashMap toHashMap() {

        HashMap map = new HashMap();
        try {
            Field[] fields = this.getClass().getDeclaredFields();

            String column = "";
            Object value = null;
            for (Field field : fields) {

                if (field.isAccessible()) {
                    column = field.getAnnotation(TableColumn.class).columnName();
                    value = field.get(this);
                    if (value != null) {

                        map.put(column, value);
                    }
                } else {
                    field.setAccessible(true);

                    column = field.getAnnotation(TableColumn.class).columnName();
                    value = field.get(this);
                    if (value != null) {
                        map.put(column, value);
                    }

                    field.setAccessible(false);
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return map;
    }
}
