package com.oss.carbonadministrator.service.image;

public class Electricity implements BillStrategy {

    @Override
    public void call() {

    }

    @Override
    public String callOcrFilename() {
        return "ocr_electronic.py";
    }
}
