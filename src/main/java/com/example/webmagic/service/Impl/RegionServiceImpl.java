package com.example.webmagic.service.Impl;

import com.example.webmagic.entity.RegionEntity;
import com.example.webmagic.repository.RegionRepository;
import com.example.webmagic.service.RegionService;
import jakarta.annotation.Resource;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RegionServiceImpl implements RegionService {

    @Resource
    private RegionRepository repository;

    @Override
    @Transactional
    public void save(RegionEntity entity) {
        this.repository.save(entity);
    }

    @Override
    public List<RegionEntity> findAll(RegionEntity entity) {
        Example<RegionEntity> example = Example.of(entity);
        List<RegionEntity> list = this.repository.findAll(example);
        return list;
    }
}
