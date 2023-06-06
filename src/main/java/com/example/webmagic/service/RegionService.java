package com.example.webmagic.service;

import com.example.webmagic.entity.RegionEntity;

import java.util.List;

public interface RegionService {
    public void save(RegionEntity entity);

    public List<RegionEntity> findAll(RegionEntity entity);
}
