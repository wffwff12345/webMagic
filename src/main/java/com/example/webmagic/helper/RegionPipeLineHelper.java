package com.example.webmagic.helper;

import com.example.webmagic.entity.RegionEntity;
import com.example.webmagic.service.RegionService;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Component
public class RegionPipeLineHelper implements Pipeline {

    @Resource
    private RegionService service;

    @Override
    public void process(ResultItems resultItems, Task task) {
        Elements regions = resultItems.get("regions");
        regions.forEach(region -> {
            // 北京 110000, 天津 120000, 上海 310000, 重庆 500000
            List<String> codeList = Arrays.asList("110000", "120000", "310000", "500000");
            List<String> list = Arrays.asList("11", "12", "31", "50");
            String code = region.select("tr td:nth-child(2) ").text();
            String name = region.select("tr td:nth-child(3) ").text();
            RegionEntity entity = new RegionEntity();
            entity.setCode(code);
            entity.setName(name);
            entity.setCreateTime(new Date());
            if (codeList.contains(code) || list.stream().anyMatch(code::startsWith)) {
                entity.setCreateTime(new Date());
                if (codeList.contains(code)) {
                    entity.setParentCode("0");
                    entity.setType("2");
                }
                list.forEach(item -> {
                    if (code.startsWith(item) && !codeList.contains(code)) {
                        // String parentCode = codeList.stream().filter(x -> x.startsWith(item)).collect(Collectors.toList()).get(0);
                        String parentCode = code.substring(0, 2) + "0000";
                        entity.setParentCode(parentCode);
                        entity.setType("3");
                    }
                });
            }
            if (RegionHelper.provinceJustify(code)) {
                entity.setParentCode("0");
                entity.setType("1");
            }
            if (RegionHelper.cityJustify(code)) {
                String parentCode = code.substring(0, 2) + "0000";
                entity.setParentCode(parentCode);
                entity.setType("2");
            }
            if (!RegionHelper.provinceJustify(code) && !RegionHelper.cityJustify(code) && list.stream().noneMatch(code::startsWith)) {
                String parentCode = "460300";
                if (StringUtils.isNotBlank(code)) {
                    parentCode = code.substring(0, 4) + "00";
                }
                entity.setParentCode(parentCode);
                entity.setType("3");
            }
            service.save(entity);
        });
    }
}
