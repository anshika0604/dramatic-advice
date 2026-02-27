package com.project.dramatic_advice.controller;

import com.project.dramatic_advice.dto.AdviceRequest;
import com.project.dramatic_advice.dto.AdviceResponse;
import com.project.dramatic_advice.service.AdviceService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${API_URL}/api/advice")
public class AdviceController {

    private final AdviceService service;

    public AdviceController(AdviceService service) {
        this.service = service;
    }

    @PostMapping
    public AdviceResponse generate(@RequestBody AdviceRequest req) {
        String advice = service.getAdvice(req.getText(), req.getMood());
        return new AdviceResponse(advice);
    }
}
