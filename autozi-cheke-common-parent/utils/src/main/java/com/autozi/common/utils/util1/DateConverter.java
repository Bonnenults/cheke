package com.autozi.common.utils.util1;




import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.beanutils.ConversionException;
import org.apache.commons.beanutils.Converter;
import org.apache.commons.lang.StringUtils;


public class DateConverter implements Converter {
	public  static  String DATEFORMATHOUR="yyyy-MM-dd HH:mm:ss";
	public  static  String DATEFORMAT="yyyy-MM-dd";
	
	private static DateFormat df = null;

	public DateConverter() {
		df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	}

	public DateConverter(String format) {
		df = new SimpleDateFormat(format);
	}

	public Object convert(Class type, Object value) {
		if (value == null) {
			return null;
		} else if (type == Date.class) {
			return convertToDate(type, value);
		} else if (type == String.class) {
			return convertToString(type, value);
		}else if(type== Object.class)
        {
		    return value;
        }

		throw new ConversionException("Could not convert "
				+ value.getClass().getName() + " to " + type.getName());
	}

    protected Object convertToDate(Class<?> type, Object value) {
		if (value instanceof String) {
			try {
				if (StringUtils.isEmpty(value.toString())) {
					return null;
				}

				return df.parse((String) value);
			} catch (Exception pe) {
				throw new ConversionException("Error converting String to Date");
			}
		}

		throw new ConversionException("Could not convert "
				+ value.getClass().getName() + " to " + type.getName());
	}


	protected Object convertToString(Class<?> type, Object value) {
		if (value instanceof Date) {
			try {
				return df.format(value);
			} catch (Exception e) {
				throw new ConversionException("Error converting Date to String");
			}
		}

		return value.toString();
	}
}
