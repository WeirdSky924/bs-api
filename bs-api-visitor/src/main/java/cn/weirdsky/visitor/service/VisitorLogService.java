package cn.weirdsky.visitor.service;

import cn.weirdsky.entity.entity.VisitorLog;
import cn.weirdsky.entity.entity.qo.VisitorLogQo;
import cn.weirdsky.entity.entity.qo.VisitorSpaceQo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface VisitorLogService {

    /**
     * 获得所有访客记录
     * @return
     */
    List<VisitorLogQo> getAll();

    /**
     * 保存该用户访客记录
     * @param visitorId
     * @return
     */
    Boolean saveVisitorLog(String visitorId, String attendanceState, byte[] pic, Date time);

    /**
     * 更新该用户访客记录
     * @param visitorLog
     * @return
     */
    Integer updateVisitorLog(VisitorLog visitorLog);

    /**
     * 删除该用户访客记录，逻辑删除
     * @param visitorLogIds
     * @return
     */
    Integer deleteVisitorLog(String visitorLogIds);

    /**
     * 获得指定要求的访客记录
     * @param logVo
     * @return
     */
    List<VisitorLogQo> getBySearch(VisitorLogQo logVo);

    /**
     * 获得指定要求的访客记录
     * @param queryWrapper
     * @return
     */
    List<VisitorLogQo> getBySearch(String queryWrapper);

    /**
     * 获得指定Id的访客记录
     * @param visitorLogIds
     * @return
     */
    List<VisitorLogQo> getById(String visitorLogIds);

    /**
     * 获得指定部门的访客记录
     * @param departmentId
     * @return
     */
    List<VisitorLogQo> getByDepartmentId(String departmentId);

    /**
     * 获得指定访客空间的信息
     * @param spaceId
     * @return
     */
    List<VisitorLogQo> getBySpaceId(String spaceId);

    /**
     * 进入时保存记录接口
     * @param visitor
     * @return
     */
    boolean in(MultipartFile visitor, Date time) throws IOException;

    /**
     * 出去时保存记录接口
     * @param visitor
     * @return
     */
    boolean out(MultipartFile visitor, Date time) throws IOException;

    List<List<String>> getChartData();

    boolean in(String visitorName, String path);

    boolean out(String visitorName, String path);

    byte[] getLogPic(String visitorLogId);

    List<VisitorLogQo> getLogBySpace(VisitorSpaceQo visitorSpaceQo);

    List<VisitorLogQo> getLogBySpace(String queryWrapper);

    Boolean openCV();

}
