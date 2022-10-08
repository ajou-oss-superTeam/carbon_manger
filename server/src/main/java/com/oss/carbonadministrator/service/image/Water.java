package com.oss.carbonadministrator.service.image;

public class Water implements BillStrategy {

    @Override
    public void call() {

    }

    @Override
    public String callOcrFilename() {
        return "ocr_water.py";
    }
}
