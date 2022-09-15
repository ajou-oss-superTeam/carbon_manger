package com.oss.carbonadministrator.controller.graph;

import com.oss.carbonadministrator.dto.response.ResponseDto;
import com.oss.carbonadministrator.service.graph.GraphService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/graph")
@RequiredArgsConstructor
public class GraphController {

    private final GraphService graphService;

    @PostMapping("/electricity/fee")
    public ResponseDto electricFeeGraph(String email) {
        return graphService.elecFeeGraph(email);
    }

}