package cn.weirdsky.utils.util;

import java.util.LinkedHashMap;
import java.util.List;

public interface CastUtil {

    <T> List ConvertToList(List<LinkedHashMap<String, Object>> mapList, Class<T> clazz);

    <T> T ConvertToObject(LinkedHashMap<String, Object> map, Class<T> clazz);
}
