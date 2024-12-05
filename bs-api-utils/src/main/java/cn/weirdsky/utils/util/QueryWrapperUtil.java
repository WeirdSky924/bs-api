package cn.weirdsky.utils.util;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

public interface QueryWrapperUtil {

    public <T> QueryWrapper<T> stringToQueryWrapper(String queryStr, Class<T> clazz);

    public <T> String getSqlWithValues(QueryWrapper<T> queryWrapper);
}
