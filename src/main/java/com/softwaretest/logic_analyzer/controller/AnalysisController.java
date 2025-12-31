package com.softwaretest.logic_analyzer.controller;

import com.softwaretest.logic_analyzer.model.AnalysisRequest;
import com.softwaretest.logic_analyzer.model.TestCase;
import com.softwaretest.logic_analyzer.service.CoverageService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/analyze")
@CrossOrigin(origins = "*")
public class AnalysisController {

    private final CoverageService coverageService;

    public AnalysisController(CoverageService coverageService) {
        this.coverageService = coverageService;
    }

    @PostMapping("/coc")
    public List<TestCase> getCoC(@RequestBody AnalysisRequest request) {
        return coverageService.generateCoC(request.getExpression());
    }

    @PostMapping("/cacc")
    public List<TestCase> getCACC(@RequestBody AnalysisRequest request) {
        return coverageService.generateCACC(request.getExpression());
    }

    @PostMapping("/junit")
    public Map<String, String> getJUnitCode(@RequestBody AnalysisRequest request) {
        String type = (request.getType() == null) ? "cacc" : request.getType();
        String code = coverageService.generateJUnitCode(request.getExpression(), type);
        return Map.of("code", code);
    }
}