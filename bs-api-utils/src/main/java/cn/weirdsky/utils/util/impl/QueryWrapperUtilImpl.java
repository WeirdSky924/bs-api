package cn.weirdsky.utils.util.impl;

import cn.weirdsky.utils.util.QueryWrapperUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

@Service
public class QueryWrapperUtilImpl implements QueryWrapperUtil {

    /**
     * 将格式化的字符串转换为 QueryWrapper 对象。
     *
     * @param queryStr 查询条件字符串，格式为 "field1=1,field2>2,field3<3"
     * @param clazz 实体类的类型
     * @param <T> 实体类型
     * @return 生成的 QueryWrapper 对象
     */
    @Override
    public <T> QueryWrapper<T> stringToQueryWrapper(String queryStr, Class<T> clazz) {
        QueryWrapper<T> queryWrapper = new QueryWrapper<>();

        if (queryStr == null || queryStr.trim().isEmpty()) {
            return queryWrapper;
        }

        String[] conditions = queryStr.split(",");

        for (String condition : conditions) {
            String[] parts = condition.split("=");
            if (parts.length == 2) {
                queryWrapper.eq(parts[0].trim(), parts[1].trim());
                continue;
            }

            parts = condition.split(">");
            if (parts.length == 2) {
                queryWrapper.gt(parts[0].trim(), parts[1].trim());
                continue;
            }

            parts = condition.split("<");
            if (parts.length == 2) {
                queryWrapper.lt(parts[0].trim(), parts[1].trim());
                continue;
            }

            parts = condition.split(">=");
            if (parts.length == 2) {
                queryWrapper.ge(parts[0].trim(), parts[1].trim());
                continue;
            }

            parts = condition.split("<=");
            if (parts.length == 2) {
                queryWrapper.le(parts[0].trim(), parts[1].trim());
                continue;
            }

            // 可扩展其他操作符
        }

        return queryWrapper;
    }

    public <T> String getSqlWithValues(QueryWrapper<T> queryWrapper) {
        String sqlSegment = queryWrapper.getCustomSqlSegment();  // 获取 SQL 片段
        Map<String, Object> paramMap = queryWrapper.getParamNameValuePairs();  // 获取参数列表
        for (String key : paramMap.keySet()) {
            Object value = paramMap.get(key);
            sqlSegment = sqlSegment.replace("#{ew.paramNameValuePairs." + key + "}", "\'" + value.toString() + "\'");
        }
        sqlSegment = sqlSegment.replace("WHERE", "");
        return sqlSegment;
    }

    private static <T> List<Object> getParams(QueryWrapper<T> queryWrapper) {
        try {
            // 利用反射获取 "paramNameValuePairs" 属性（此属性包含参数值）
            Field paramNameValuePairsField = queryWrapper.getClass().getSuperclass().getDeclaredField("paramNameValuePairs");
            paramNameValuePairsField.setAccessible(true);
            return (List<Object>) paramNameValuePairsField.get(queryWrapper);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
