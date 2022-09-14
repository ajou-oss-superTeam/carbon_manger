package com.oss.carbonadministrator.controller;

import com.oss.carbonadministrator.dto.request.user.UserEmailRequest;
import com.oss.carbonadministrator.dto.response.ResponseDto;
import com.oss.carbonadministrator.service.graph.GraphService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/graph")
@RequiredArgsConstructor
public class GraphController {

    @Autowired
    private final GraphService graphService;

    @GetMapping("/electricity/fee")
    public ResponseDto electricFeeGraph(@RequestBody @Valid UserEmailRequest requestDto){
        return graphService.elecFeeGraph(requestDto.getEmail());
    }


}
