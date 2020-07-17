package com.autozi.common.dal.format;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import com.autozi.common.dal.format.persistence;

@Documented
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Column {
	public persistence ColumnType() default persistence.PERSISTENCE;
	public String ColumnName() default "";
}
