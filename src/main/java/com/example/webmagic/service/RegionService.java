package com.example.webmagic.service;

import com.example.webmagic.entity.RegionEntity;

import java.util.List;
import java.util.Map;

public interface RegionService {
    public void save(RegionEntity entity);

    public List<Map<String, Object>> findAll(RegionEntity entity);

}
