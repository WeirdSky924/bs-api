package cn.weirdsky.common.mapper;

import cn.weirdsky.common.entity.VisitorList;
import cn.weirdsky.common.entity.qo.VisitorListQo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface VisitorListMapper extends BaseMapper<VisitorList> {

    @Select("SELECT MAX(listId) from visitor_list")
    String getMaxId();

    @Select("SELECT * from visitor_list where deleteMark='0' ORDER BY createTime")
    List<VisitorList> getAll();

    @Select("SELECT * from visitor_list where listId in (SELECT listId from visitor_space where spaceId=${spaceId}) and deleteMark = '0'")
    VisitorList getVisitorListBySpace(@Param("spaceId") String spaceId);

    VisitorList toVisitorList(VisitorListQo visitorListQo);

    VisitorListQo toVisitorListQo(VisitorList visitorList);

}
