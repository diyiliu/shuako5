package com.diyiliu.support.annotation;

import java.lang.annotation.*;

/**
 * Description: TableField
 * Author: DIYILIU
 * Update: 2015-11-13 17:09
 */

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TableField {

    public String tableName();

    public String primaryKey();
}
