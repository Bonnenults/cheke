package com.autozi.common.utils.o2o;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.support.FormattingConversionServiceFactoryBean;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A central place to register application converters and formatters. 
 */
@Configurable
public class ApplicationConversionServiceFactoryBean extends FormattingConversionServiceFactoryBean {

	// DELETE BY LIHF@Autozi.com 2017-8-3下午2:34:50 START 需求描述：升级到4.X版本，去掉该方法
//	@Override
//	protected void installFormatters(FormatterRegistry registry) {
//		super.installFormatters(registry);
//		// Register application converters and formatters
//	}
	// DELETE BY LIHF@Autozi.com 2017-8-3下午2:34:50 END  

    public void afterPropertiesSet() {
        super.afterPropertiesSet();
        installLabelConverters(getObject());
    }


    public Converter<String, Date> getStringToDateConverter() {
        return new Converter<String, Date>() {
            private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            private DateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            private DateFormat dateTimeLongFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            private Pattern pattern = Pattern.compile("^\\d{4}-\\d{2}-\\d{2}(\\s\\d{2}:\\d{2}(:\\d{2})?)?$");
            
            public Date convert(String string) {
                Matcher matcher = pattern.matcher(string);
                if (matcher.matches()) {
                    MatchResult result = matcher.toMatchResult();
                    try {
                    if (result.group(2) != null) {
                        return dateTimeLongFormat.parse(string);
                    } else if (result.group(1) != null) {
                        return dateTimeFormat.parse(string);
                    } else {
                        return dateFormat.parse(string);
                    }
                    } catch (ParseException e) {
                        e.printStackTrace();
                        return null;
                    }
                } else {
                    return null;
                }
            }
        };
    }

    public void installLabelConverters(FormatterRegistry registry) {
        registry.addConverter(getStringToDateConverter());
    }
    
}
