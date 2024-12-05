package cn.weirdsky.mapper.mapper;

import cn.weirdsky.entity.entity.SysLog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface SysLogMapper extends BaseMapper<SysLog> {

    @Select("SELECT MAX(sysLogId) from sys_log")
    String getMaxId();

}
