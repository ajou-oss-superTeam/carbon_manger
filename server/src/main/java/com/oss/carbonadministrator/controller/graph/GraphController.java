package com.oss.carbonadministrator.controller.graph;

import com.oss.carbonadministrator.dto.request.user.UserEmailRequest;
import com.oss.carbonadministrator.dto.response.ResponseDto;
import com.oss.carbonadministrator.service.graph.GraphService;
import com.oss.carbonadministrator.service.graph.GraphService.GraphData;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/graph")
@RequiredArgsConstructor
public class GraphController {

    private final GraphService graphService;


    @PostMapping("/electricity/fee")
    public ResponseDto electricFeeGraph(@RequestBody @Valid UserEmailRequest requestDto) {
        GraphData elecFeeGraph = graphService.getElecFeeGraph(requestDto.getEmail());
        return ResponseDto.success(elecFeeGraph, "전기 지역별 요금 비교 그래프 성공");
    }

    @PostMapping("/all/carbon")
    public ResponseDto allCarbonUsageGraph(@RequestBody @Valid UserEmailRequest requestDto) {
        GraphData allCarbonGraph = graphService.getAllCarbonGraph(requestDto.getEmail());
        return ResponseDto.success(allCarbonGraph, "전체 탄소배출량 데이터 그래프 성공");
    }

}
