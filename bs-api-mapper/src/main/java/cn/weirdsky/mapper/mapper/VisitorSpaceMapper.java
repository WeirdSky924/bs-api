package cn.weirdsky.mapper.mapper;

import cn.weirdsky.entity.entity.VisitorSpace;
import cn.weirdsky.entity.entity.qo.VisitorSpaceQo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface VisitorSpaceMapper extends BaseMapper<VisitorSpace> {

    @Select("SELECT MAX(spaceId) from visitor_space")
    String getMaxId();

    @Select("SELECT * FROM visitor_space as s left join visitor_list as l on s.listId=l.listId where s.deleteMark='0' ORDER BY s.createTime")
    List<VisitorSpaceQo> getAll();

    @Select("SELECT * from visitor_space as s left join visitor_list as l on s.listId=l.listId where ${ew.sqlSegment} ORDER BY s.createTime")
    List<VisitorSpaceQo> getListBySearch(@Param("ew") QueryWrapper queryWrapper);

    @Select("SELECT * from visitor_space as s left join visitor_list as l on s.listId=l.listId where ${ew} ORDER BY s.createTime")
    List<VisitorSpaceQo> getListBySearchString(@Param("ew") String queryWrapper);

    @Select("SELECT * from visitor_space as s left join visitor_list as l on s.listId=l.listId where deleteMark = 0 and spaceAdminUserIds like '%'||#{loginUser}||'%' ORDER BY createTime")
    List<VisitorSpaceQo> getLoginList(String loginUser);

    @Select("SELECT * from visitor_space where ${ew.sqlSegment} ORDER BY createTime")
    List<VisitorSpaceQo> getById(@Param("ew") QueryWrapper<VisitorSpace> queryWrapper);

    @Select("SELECT * FROM visitor_space as s left join visitor_list as l on s.listId=l.listId where s.deleteMark='0' and ${ew.sqlSegment} ORDER BY s.createTime")
    List<VisitorSpaceQo> getMySpace(@Param("ew") QueryWrapper queryWrapper);

    VisitorSpace toVisitorSpace(VisitorSpaceQo visitorSpaceQo);

    VisitorSpaceQo toVisitorSpaceQo(VisitorSpace visitorSpace);
}
