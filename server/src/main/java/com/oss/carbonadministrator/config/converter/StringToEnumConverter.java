package com.oss.carbonadministrator.config.converter;

import com.oss.carbonadministrator.service.image.strategy.BillType;
import org.springframework.core.convert.converter.Converter;

public class StringToEnumConverter implements Converter<String, BillType> {

    @Override
    public BillType convert(String source) {
        return BillType.valueOf(source.toUpperCase());
    }
}
