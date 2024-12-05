package cn.weirdsky.mapper.mapper;

import cn.weirdsky.entity.entity.User;
import cn.weirdsky.entity.entity.qo.UserQo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UserMapper extends BaseMapper<User> {

    @Select("SELECT MAX(userId) from user")
    String getMaxId();

    @Select("SELECT * From user WHERE ${ew.sqlSegment} ORDER BY createTime")
    User getLogin(@Param("ew") QueryWrapper queryWrapper);

    @Select("SELECT * From user where deleteMark='0' ORDER BY createTime")
    List<User> getAll();

    @Select("SELECT * from user where ${ew.sqlSegment} ORDER BY createTime")
    List<User> getListBySearch(@Param("ew") QueryWrapper queryWrapper);

    User toUser(UserQo userQo);

    UserQo toUserQo(User user);

}
