package cn.weirdsky.utils.util.impl;

import cn.weirdsky.utils.util.DynamicDataSourceUtil;
import org.springframework.stereotype.Service;

@Service
public class DynamicDataSourceUtilImpl implements DynamicDataSourceUtil {

    private static final ThreadLocal<String> CONTEXT_HOLDER = new ThreadLocal<>();

    public void setDataSource(String dataSourceKey) {
        CONTEXT_HOLDER.set(dataSourceKey);
    }

    public String getDataSource() {
        return CONTEXT_HOLDER.get();
    }

    public void clearDataSource() {
        CONTEXT_HOLDER.remove();
    }
}
