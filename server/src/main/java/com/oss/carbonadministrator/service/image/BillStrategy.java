package com.oss.carbonadministrator.service.image;

/*
 * 전기, 수도, 가스 선택 방식으로 전략 패턴 사용
 */
public interface BillStrategy {

    void call();

    String callOcrFilename();
}
