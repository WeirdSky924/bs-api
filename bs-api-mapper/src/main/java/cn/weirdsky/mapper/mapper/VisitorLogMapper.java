package cn.weirdsky.mapper.mapper;

import cn.weirdsky.entity.entity.VisitorLog;
import cn.weirdsky.entity.entity.qo.VisitorLogQo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface VisitorLogMapper extends BaseMapper<VisitorLog> {

    @Select("SELECT MAX(visitorLogId) from visitor_log")
    String getMaxId();

    @Select("SELECT l.visitorLogId, l.visitorId, l.visitorTimeIn, l.visitorTimeOut, l.deleteMark, l.visitorTime, v.visitorId, v.visitorName, v.visitorSex, v.visitorDepartmentId, v.attendanceDays, v.remarks, v.createId, v.createName, v.createTime, v.deleteMark" +
            " from visitor_log as l left join visitor v on l.visitorId = v.visitorId where l.deleteMark=0 ORDER BY l.visitorTime")
    List<VisitorLogQo> getAll();

    @Select("SELECT * from visitor_log as l left join visitor v on l.visitorId = v.visitorId left join department as d on v.visitorDepartmentId=d.departmentId where ${ew.sqlSegment} ORDER BY l.visitorTime")
    List<VisitorLogQo> getBySearch(@Param("ew") QueryWrapper queryWrapper);

    @Select("SELECT * from visitor_log as l left join visitor v on l.visitorId = v.visitorId left join department as d on v.visitorDepartmentId=d.departmentId where ${ew} ORDER BY l.visitorTime")
    List<VisitorLogQo> getBySearchString(@Param("ew") String queryWrapper);

    @Select("SELECT * FROM visitor_log as l left join visitor v on l.visitorId = v.visitorId where v.visitorDepartmentId=${departmentId} ORDER BY l.visitorLogId")
    List<VisitorLogQo> getByDepartmentId(@Param("departmentId") String departmentId);

    @Select("SELECT * FROM visitor_log as l left join visitor v on l.visitorId = v.visitorId where l.visitorId=${id} and l.deleteMark='0'")
    VisitorLogQo getVoByVisitorId(@Param("id") String id);

    VisitorLog toVisitorLog(VisitorLogQo visitorLogQo);

    VisitorLogQo toVisitorLogQo(VisitorLog visitorLog);

    @Select("SELECT * FROM visitor_log as l left join visitor v on l.visitorId = v.visitorId where l.deleteMark=0 and v.visitorName='${visitorName}'")
    VisitorLog getByVisitorName(@Param("visitorName") String visitorName);

    @Select("SELECT visitorTime, count(*) as count FROM visitor_log WHERE deleteMark=0 GROUP BY visitorTime")
    List<VisitorLogQo> getCountLogByDay();

    @Select("SELECT pic from visitor_log where deleteMark='0' and visitorLogId=${visitorLogId}")
    VisitorLog getPicByVisitorLogId(@Param("visitorLogId") String visitorLogId);

    @Select("SELECT * from visitor_log as l left join visitor as v on l.visitorId=v.visitorId\n" +
            "where l.deleteMark='0'\n" +
            "  and ${ew.sqlSegment}")
    List<VisitorLogQo> getLogBySpace(@Param("ew") QueryWrapper queryWrapper);

    @Select("SELECT * from visitor_log as l left join visitor as v on l.visitorId=v.visitorId\n" +
            "where l.deleteMark='0'\n" +
            "  and ${ew}")
    List<VisitorLogQo> getLogBySpaceString(@Param("ew") String queryWrapper);

    @Select("SELECT * FROM visitor_log as l left join visitor v on l.visitorId = v.visitorId where l.deleteMark=0 and ${ew.sqlSegment}")
    VisitorLog getTodayLogByVisitorName(@Param("ew") QueryWrapper queryWrapper);

}
