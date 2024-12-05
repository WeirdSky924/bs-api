package cn.weirdsky.common.mapper;

import cn.weirdsky.common.entity.Department;
import cn.weirdsky.common.entity.Visitor;
import cn.weirdsky.common.entity.qo.DepartmentQo;
import cn.weirdsky.common.entity.vo.DepartmentVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface DepartmentMapper extends BaseMapper<Department> {

    @Select("SELECT MAX(departmentId) from department")
    String getMaxId();

    @Select("SELECT * FROM department where deleteMark='0' ORDER BY createTime")
    List<Department> getAll();

    @Select("SELECT * from department where ${ew.sqlSegment} ORDER BY createTime")
    List<Department> getListBySearch(@Param("ew") QueryWrapper queryWrapper);

    @Select("SELECT * from department where ${ew.sqlSegment} ORDER BY createTime")
    List<Department> getBySearch(@Param("ew") QueryWrapper<Department> queryWrapper);

    @Select("SELECT d.departmentName,count(*) as visitorCount\n" +
            "from visitor_log as l left join visitor as v on l.visitorId=v.visitorId left join department as d on v.visitorDepartmentId=d.departmentId\n" +
            "where l.deleteMark='0' group by departmentName;")
    List<DepartmentVo> getVisitorLogDepartmentVo();

    Department toDepartment(DepartmentQo departmentQo);

    DepartmentQo toDepartmentQo(Department department);
}
