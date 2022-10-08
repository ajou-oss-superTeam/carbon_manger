package com.oss.carbonadministrator.service.image;

import org.springframework.stereotype.Service;

@Service
public class Water implements BillStrategy {

    @Override
    public void call() {

    }

    @Override
    public String callOcrFilename() {
        return "ocr_water.py";
    }
}
