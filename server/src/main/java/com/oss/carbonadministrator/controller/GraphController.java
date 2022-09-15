package com.oss.carbonadministrator.controller;

import com.oss.carbonadministrator.dto.response.ResponseDto;
import com.oss.carbonadministrator.service.graph.GraphService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/graph")
@RequiredArgsConstructor
public class GraphController {

    @Autowired
    private final GraphService graphService;

    @GetMapping("/electricity/fee")
    public ResponseDto electricFeeGraph(String email) {
        return graphService.elecFeeGraph(email);
    }

}
