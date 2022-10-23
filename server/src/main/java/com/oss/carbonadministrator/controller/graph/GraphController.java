/**
 *  Copyright 2022 Carbon_Developers
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
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
