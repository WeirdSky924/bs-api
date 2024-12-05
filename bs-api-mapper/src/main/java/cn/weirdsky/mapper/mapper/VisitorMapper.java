package cn.weirdsky.mapper.mapper;

import cn.weirdsky.entity.entity.Visitor;
import cn.weirdsky.entity.entity.qo.VisitorQo;
import cn.weirdsky.entity.entity.vo.DepartmentVo;
import cn.weirdsky.entity.entity.vo.VisitorLogVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface VisitorMapper extends BaseMapper<Visitor> {

    @Select("SELECT MAX(visitorId) from visitor")
    String getMaxId();

    @Select("SELECT * from visitor where deleteMark='0' ORDER BY createTime")
    List<Visitor> getAll();

    @Select("SELECT * from visitor where ${ew.sqlSegment} ORDER BY createTime")
    List<Visitor> getListBySearch(@Param("ew") QueryWrapper queryWrapper);

    @Select("SELECT * from visitor where ${ew} ORDER BY createTime")
    List<Visitor> getListBySearchString(@Param("ew") String queryWrapper);

    @Select("SELECT * from visitor where visitorName='${visitorName}' and deleteMark=0 ORDER BY createTime")
    Visitor getByVisitorName(@Param("visitorName") String visitorName);

    VisitorQo toVisitorQo(Visitor visitor);

    Visitor toVisitor(VisitorQo visitorQo);

    @Select("SELECT v.visitorName,year(l.visitorTime) as year,MONTH(l.visitorTime) as month, count(*) as sum from visitor_log as l left join visitor as v on l.visitorId=v.visitorId where l.deleteMark='0' and visitorTimeIn is not null group by v.visitorName,year(l.visitorTime),MONTH(l.visitorTime)")
    List<VisitorLogVo> visitorStatisticsGroupByDate();

    @Select("SELECT d.departmentName,COUNT(*) as visitorCount from visitor as v left join department as d on v.visitorDepartmentId=d.departmentId where v.deleteMark='0' GROUP BY d.departmentName")
    List<DepartmentVo> visitorStatisticGroupByDepartment();

    @Select("SELECT * from visitor as v left join department d on v.visitorDepartmentId = d.departmentId where v.deleteMark='0' and d.deleteMark='0' and d.departmentName='${departmentName}'")
    List<Visitor> getVisitorByDepartmentName(@Param("departmentName") String departmentName);

}
