package com.oss.carbonadministrator.service.image;

import org.springframework.stereotype.Component;

@Component
public class Electricity implements BillStrategy {

    @Override
    public void call() {

    }

    @Override
    public String callOcrFilename() {
        return "ocr_electronic.py";
    }
}
