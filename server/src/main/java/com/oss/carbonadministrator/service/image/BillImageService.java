package com.oss.carbonadministrator.service.image;

import com.oss.carbonadministrator.domain.bill.Bill;
import com.oss.carbonadministrator.dto.request.image.ImgDataRequest;

public interface BillImageService {

    Bill save(String email, int year, int month, ImgDataRequest requestDto);

}
