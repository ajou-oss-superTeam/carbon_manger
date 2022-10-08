package com.oss.carbonadministrator.service.image;

public class Gas implements BillStrategy {

    @Override
    public void call() {

    }

    @Override
    public String callOcrFilename() {
        return "ocr_gas.py";
    }
}
