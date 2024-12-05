package cn.weirdsky.utils.util.impl;

import cn.weirdsky.utils.util.CastUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CastUtilImpl implements CastUtil {
    @Override
    public <T> List ConvertToList(List<LinkedHashMap<String, Object>> mapList, Class<T> clazz) {
        if (mapList == null){
            return null;
        }
        List<T> collect = mapList.stream()
                .map(map -> {
                    try {
                        return new ObjectMapper().convertValue(map, clazz);
                    } catch (Exception e) {
                        throw new RuntimeException("转换失败", e);
                    }
                })
                .collect(Collectors.toList());
        return collect;
    }

    @Override
    public <T> T ConvertToObject(LinkedHashMap<String, Object> map, Class<T> clazz) {
        return new ObjectMapper().convertValue(map, clazz);
    }
}
