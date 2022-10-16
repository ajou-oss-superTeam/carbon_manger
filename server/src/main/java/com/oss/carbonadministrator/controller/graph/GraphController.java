package com.oss.carbonadministrator.controller.graph;

import com.oss.carbonadministrator.dto.request.user.UserEmailRequest;
import com.oss.carbonadministrator.dto.response.ResponseDto;
import com.oss.carbonadministrator.service.graph.GraphService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
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
        return graphService.getElecFeeGraph(requestDto.getEmail());
    }

    @GetMapping("/all/carbon")
    public ResponseDto allCarbonUsageGraph(@RequestBody @Valid UserEmailRequest requestDto) {
        graphService.getAllCarbonGraph(requestDto.getEmail());
        return ResponseDto.success(null, "전체 탄소배출량 데이터 그래프 성공");
    }

}
