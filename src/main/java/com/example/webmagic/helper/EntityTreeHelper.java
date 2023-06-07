package com.example.webmagic.helper;

import com.example.webmagic.entity.RegionEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EntityTreeHelper {
    public static List<Map<String, Object>> createTree(List<RegionEntity> list, String parentCode) {
        if (parentCode == null) {
            parentCode = "";
        }
        List<Map<String, Object>> items = new ArrayList<>();
        List<RegionEntity> unUsed = new ArrayList<>();
        for (RegionEntity entity : list) {
            String xParentCode = entity.getParentCode();
            if (xParentCode == null) {
                xParentCode = "";
            }
            if (parentCode.equals(xParentCode)) {
                Map<String, Object> map = new HashMap<>();
                map.put("code", entity.getCode());
                map.put("name", entity.getName());
                items.add(map);
            } else {
                unUsed.add(entity);
            }
        }
        items.forEach(x -> {
            List<Map<String, Object>> nodes = createTree(unUsed, x.get("code").toString());
            if (nodes.size() > 0) {
                x.put("children", nodes);
            }
        });
        return items;
    }
}
