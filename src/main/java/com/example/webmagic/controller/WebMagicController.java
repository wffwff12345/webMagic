package com.example.webmagic.controller;

import com.example.webmagic.entity.RegionEntity;
import com.example.webmagic.service.RegionService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/region")
public class WebMagicController {
    @Resource
    RegionService service;

    @GetMapping("/findAll")
    public List<Map<String, Object>> getRegionList() {
        RegionEntity entity = new RegionEntity();
        return service.findAll(entity);
    }
}
