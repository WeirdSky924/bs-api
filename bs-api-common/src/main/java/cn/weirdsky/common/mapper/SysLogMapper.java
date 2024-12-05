package cn.weirdsky.common.mapper;

import cn.weirdsky.common.entity.SysLog;
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
