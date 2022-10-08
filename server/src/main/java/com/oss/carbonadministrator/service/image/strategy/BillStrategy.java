package com.oss.carbonadministrator.service.image.strategy;

import com.oss.carbonadministrator.dto.request.image.ImgDataRequest;
import org.json.simple.JSONObject;

/*
 * 전기, 수도, 가스 선택 방식으로 전략 패턴 사용
 */
public interface BillStrategy {

    String callOcrFilename();

    <T> T toDto(String fileName, JSONObject jsonObject);

    <T> T toEntity(ImgDataRequest requestDto);
}
