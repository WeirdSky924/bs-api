package cn.weirdsky.utils.util.impl;

import cn.weirdsky.mapper.mapper.*;
import cn.weirdsky.utils.util.DynamicDataSourceUtil;
import cn.weirdsky.utils.util.TableUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TableUtilImpl implements TableUtil {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private VisitorMapper visitorMapper;
    @Autowired
    private DepartmentMapper departmentMapper;
    @Autowired
    private VisitorSpaceMapper visitorSpaceMapper;
    @Autowired
    private VisitorListMapper visitorListMapper;
    @Autowired
    private VisitorLogMapper visitorLogMapper;
    @Autowired
    private SysLogMapper sysLogMapper;
    @Autowired
    private DynamicDataSourceUtil dynamicDataSourceUtil;

    public static final String USER = "user";
    public static final String DEPARTMENT = "department";
    public static final String VISITOR = "visitor";
    public static final String VISITOR_SPACE = "visitor_space";
    public static final String VISITOR_LIST = "visitor_list";
    public static final String VISITOR_LOG = "visitor_log";
    public static final String SYS_LOG = "sys_log";

    public String getTableId(String tableName) {
        String ID = "";
        if (tableName.toLowerCase().equals(USER)) {
//            dynamicDataSourceUtil.setDataSource("user");
            ID = userMapper.getMaxId();
//            dynamicDataSourceUtil.clearDataSource();
        } else if (tableName.toLowerCase().equals(VISITOR)) {
            ID = visitorMapper.getMaxId();
        } else if (tableName.toLowerCase().equals(DEPARTMENT)) {
            ID = departmentMapper.getMaxId();
        } else if (tableName.toLowerCase().equals(VISITOR_SPACE)) {
            ID = visitorSpaceMapper.getMaxId();
        } else if (tableName.toLowerCase().equals(VISITOR_LIST)) {
            ID = visitorListMapper.getMaxId();
        } else if (tableName.toLowerCase().equals(VISITOR_LOG)) {
            ID = visitorLogMapper.getMaxId();
        } else if (tableName.toLowerCase().equals(SYS_LOG)) {
            ID = sysLogMapper.getMaxId();
        } else {
            ID = "00000000";
        }
        return String.format("%08d", Integer.parseInt(ID == null ? "00000000" : ID) + 1);
    }

}
