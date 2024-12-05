package cn.weirdsky.common.service.impl;

import cn.weirdsky.common.entity.Visitor;
import cn.weirdsky.common.entity.VisitorList;
import cn.weirdsky.common.entity.VisitorLog;
import cn.weirdsky.common.entity.qo.VisitorLogQo;
import cn.weirdsky.common.entity.qo.VisitorSpaceQo;
import cn.weirdsky.common.mapper.VisitorListMapper;
import cn.weirdsky.common.mapper.VisitorLogMapper;
import cn.weirdsky.common.mapper.VisitorMapper;
import cn.weirdsky.common.service.VisitorLogService;
import cn.weirdsky.common.util.LoginUtil;
import cn.weirdsky.common.util.PythonUtil;
import cn.weirdsky.common.util.StringUtil;
import cn.weirdsky.common.util.TableUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class VisitorLogServiceImpl implements VisitorLogService {

    @Autowired
    private VisitorLogMapper visitorLogMapper;
    @Autowired
    private VisitorMapper visitorMapper;
    @Autowired
    private LoginUtil loginUtil;
    @Autowired
    private StringUtil stringUtil;
    @Autowired
    private TableUtil tableUtill;
    @Autowired
    private VisitorListMapper visitorListMapper;
    @Autowired
    private PythonUtil pythonUtil;

    @Value("${web.upload-path}")
    private String savePath;

    public List<VisitorLogQo> getAll() {
        List<VisitorLogQo> all = visitorLogMapper.getAll();
        checkLegal(all);
        return all;
    }

    @Override
    public Boolean saveVisitorLog(String visitorId, String attendanceState, byte[] pic, Date time) {
        VisitorLog visitorLog = new VisitorLog();
        visitorLog.setVisitorLogId(tableUtill.getTableId("visitor_log"));
        visitorLog.setDeleteMark("0");
        visitorLog.setPic(pic);
        visitorLog.setVisitorTime(time);
        if (attendanceState.equals("0")) {
            visitorLog.setVisitorTimeIn(new Date());
        } else {
            visitorLog.setVisitorTimeOut(new Date());
        }
        Integer count = 0;
        if (stringUtil.IsNotEmpty(visitorId)) {
            visitorLog.setVisitorId(visitorId);
        }
        count = visitorLogMapper.insert(visitorLog);
        return count == 1;
    }

    @Override
    public Integer updateVisitorLog(VisitorLog visitorLog) {
        QueryWrapper<VisitorLog> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("deleteMark", "0");
        queryWrapper.eq("visitorLogId", visitorLog.getVisitorLogId());
        return visitorLogMapper.update(visitorLog, queryWrapper);
    }

    @Override
    public Integer deleteVisitorLog(String visitorLogIds) {
        String[] ids = visitorLogIds.split(",");
        QueryWrapper<VisitorLog> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("visitorLogId", ids);
        VisitorLog visitorLog = new VisitorLog();
        visitorLog.setDeleteMark("1");
        return visitorLogMapper.update(visitorLog, queryWrapper);
    }

    @Override
    public List<VisitorLogQo> getBySearch(VisitorLogQo logVo) {
        QueryWrapper<VisitorLog> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("l.deleteMark", "0");
        if (stringUtil.IsNotEmpty(logVo.getStartTime()) && stringUtil.IsNotEmpty(logVo.getEndTime())) {
            queryWrapper.between("l.visitorTime", logVo.getStartTime(), logVo.getEndTime());
        }
        queryWrapper.eq(stringUtil.IsNotEmpty(logVo.getVisitorId()), "l.visitorId", logVo.getVisitorId());
        queryWrapper.like(stringUtil.IsNotEmpty(logVo.getSearch()), "v.visitorName", logVo.getSearch());
        queryWrapper.like(stringUtil.IsNotEmpty(logVo.getVisitorDepartmentId()), "d.departmentName", logVo.getVisitorDepartmentId());
        List<VisitorLogQo> bySearch = visitorLogMapper.getBySearch(queryWrapper);
        checkLegal(bySearch);
        return bySearch;
    }

    @Override
    public List<VisitorLogQo> getById(String visitorLogIds) {
        List<VisitorLogQo> visitorLogs = new ArrayList<>();
        String[] ids = visitorLogIds.split(",");
        for (String id : ids) {
            if (stringUtil.IsNotEmpty(id)) {
                visitorLogs.add(visitorLogMapper.getVoByVisitorId(id));
            }
        }
        return visitorLogs;
    }

    @Override
    public List<VisitorLogQo> getByDepartmentId(String departmentId) {
        List<VisitorLogQo> byDepartmentId = visitorLogMapper.getByDepartmentId(departmentId);
        return byDepartmentId;
    }

    @Override
    public List<VisitorLogQo> getBySpaceId(String spaceId) {
        VisitorList visitorListBySpace = visitorListMapper.getVisitorListBySpace(spaceId);
        String[] visitorIds = visitorListBySpace.getVisitorIds().split(",");
        HashSet<VisitorLogQo> visitorLogQos = new HashSet<>();
        for (String visitorId : visitorIds) {
            visitorLogQos.add(visitorLogMapper.getVoByVisitorId(visitorId));
        }
        String[] departmentIds = visitorListBySpace.getDepartmentIds().split(",");
        for (String departmentId : departmentIds) {
            List<VisitorLogQo> byDepartmentId = getByDepartmentId(departmentId);
            visitorLogQos.addAll(byDepartmentId);
        }
        List<VisitorLogQo> logVos = visitorLogQos.stream().collect(Collectors.toList());
        return logVos;
    }

    /**
     * 进，保存图片，传输图片Path给Python，Python得到正确的访客名称，然后新建保存一个今天的访客记录
     *
     * @param visitor
     * @return
     */
    @Override
    public boolean in(MultipartFile visitor, Date time) throws IOException {
        return saveAndPredictPic(visitor, 0, time);
    }

    /**
     * 出，保存图片，传输图片Path给Python，Python返回正确的访客名称，然后更新一个今天以后进记录的访客记录，如果没有则新建一个今天的访客记录
     *
     * @param visitor
     * @return
     */
    @Override
    public boolean out(MultipartFile visitor, Date time) throws IOException {
        return saveAndPredictPic(visitor, 1, time);
    }

    @Override
    public List<List<String>> getChartData() {
        List<VisitorLogQo> countLogByDay = visitorLogMapper.getCountLogByDay();
        ArrayList<List<String>> lists = new ArrayList<>();
        ArrayList<String> col = new ArrayList<>();
        ArrayList<String> data = new ArrayList<>();
        for (int i = 0; i < countLogByDay.size(); i++) {
            col.add(countLogByDay.get(i).getVisitorTime().toInstant().toString().split("T")[0]);
            data.add(countLogByDay.get(i).getCount().toString());
        }
        lists.add(col);
        lists.add(data);
        return lists;
    }

    @Override
    public boolean in(String visitorName, String path) {
        if (visitorName.equals("unknow")) {
            byte[] pic = readPic(path);
            return saveVisitorLog("", "0", pic, new Date());
        } else if (stringUtil.IsNotEmpty(visitorName)){
            QueryWrapper<VisitorLog> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("visitorTime", (new Date()).toInstant().toString().split("T")[0]);
            queryWrapper.eq("visitorName", visitorName);
            VisitorLog visitorLog = visitorLogMapper.getTodayLogByVisitorName(queryWrapper);
            if (visitorLog == null) {
                Visitor visitor1 = visitorMapper.getByVisitorName(visitorName);
                byte[] pic = readPic(path);
                return saveVisitorLog(visitor1.getVisitorId(), "0", pic, new Date());
            } else {
                visitorLog.setVisitorTimeOut(new Date());
                Integer integer = updateVisitorLog(visitorLog);
                return integer == 1;
            }
        } else {
            return false;
        }
    }

    @Override
    public boolean out(String visitorName, String path) {
        if (visitorName.equals("unknow")) {
            byte[] pic = readPic(path);
            return saveVisitorLog("", "1", pic, new Date());
        } else if (stringUtil.IsNotEmpty(visitorName)){
            QueryWrapper<VisitorLog> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("visitorTime", (new Date()).toInstant().toString().split("T")[0]);
            queryWrapper.eq("visitorName", visitorName);
            VisitorLog visitorLog = visitorLogMapper.getTodayLogByVisitorName(queryWrapper);
            if (visitorLog == null) {
                Visitor visitor1 = visitorMapper.getByVisitorName(visitorName);
                byte[] pic = readPic(path);
                return saveVisitorLog(visitor1.getVisitorId(), "1", pic, new Date());
            } else {
                visitorLog.setVisitorTimeOut(new Date());
                Integer integer = updateVisitorLog(visitorLog);
                return integer == 1;
            }
        } else {
            return false;
        }
    }

    @Override
    public byte[] getLogPic(String visitorLogId) {
        VisitorLog picByVisitorLogId = visitorLogMapper.getPicByVisitorLogId(visitorLogId);
        if (picByVisitorLogId==null) {
            return new byte[0];
        }
        return picByVisitorLogId.getPic();
    }

    @Override
    public List<VisitorLogQo> getLogBySpace(VisitorSpaceQo visitorSpaceQo) {
        if (stringUtil.IsEmpty(visitorSpaceQo.getDepartmentIds())) {
            visitorSpaceQo.setDepartmentIds("");
        }
        if (stringUtil.IsEmpty(visitorSpaceQo.getVisitorIds())){
            visitorSpaceQo.setVisitorIds("");
        }
        QueryWrapper<VisitorLogQo> queryWrapper = new QueryWrapper<>();
        queryWrapper.and(
                q -> q.in(stringUtil.IsNotEmpty(visitorSpaceQo.getVisitorIds()), "l.visitorId", visitorSpaceQo.getVisitorIds().split(","))
                        .or().in(stringUtil.IsNotEmpty(visitorSpaceQo.getDepartmentIds()), "v.visitorDepartmentId", visitorSpaceQo.getDepartmentIds().split(","))
        );
        queryWrapper.between("l.visitorTimeIn", visitorSpaceQo.getSpaceStartTime().toInstant(), visitorSpaceQo.getSpaceEndTime().toInstant());
        List<VisitorLogQo> logBySpace = visitorLogMapper.getLogBySpace(queryWrapper);
        return logBySpace;
    }

    @Override
    public Boolean openCV() {
        pythonUtil.callPythonScript("bs-api-train.py", "");
        String pythonResult = pythonUtil.callPythonScript("bs-api-cv.py", "");
        return true;
    }

    private byte[] readPic(String path) {
        FileInputStream in;
        byte[] data;
        try {
            //图片读取路径
            Date today = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            String fileName = savePath + sdf.format(today);
            in = new FileInputStream(fileName + "/" + path);
            int i = in.available();
            data = new byte[i];
            in.read(data);
            in.close();
            return data;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    private boolean saveAndPredictPic(MultipartFile visitor, Integer attendanceState, Date time) throws IOException {
        String[] str = {"IN", "OUT"};
        byte[] visitorBytes = visitor.getBytes();
        Date today = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String fileName = savePath + sdf.format(today);
        log.debug(fileName);
        File file = new File(fileName);
        if (!file.exists()) {
            try {
                file.mkdir();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (visitor != null && visitor.getSize() > 0) {
            String originalFilename = visitor.getOriginalFilename();
            if (stringUtil.IsEmpty(originalFilename)) {
                log.error("上传文件失败，未获得文件名！");
                return false;
            }
            String newName = str[attendanceState] + originalFilename;
//            String newName = "test.jpg";
            File targetFile = new File(fileName + "/" + newName);
            if (!targetFile.exists()) {
                targetFile.mkdir();
            }
            try {
                visitor.transferTo(targetFile);
            } catch (IOException e) {
                log.error("上传文件失败，保存出现错误！");
                e.printStackTrace();
                return false;
            }
            String pythonResult = pythonUtil.callPythonScript("bs_api_python.py", newName);
            log.debug(pythonResult);
            VisitorLog visitorLog = visitorLogMapper.getByVisitorName(pythonResult);
            if (visitorLog == null) {
//                Visitor visitor1 = visitorMapper.getByVisitorName(pythonResult);
                Visitor visitor1 = visitorMapper.getByVisitorName("访客测试001");
                if (visitor1 == null) {
                    return false;
                }

                saveVisitorLog(visitor1.getVisitorId(), attendanceState.toString(), visitorBytes, time == null ? today : time);
            } else {
                visitorLog.setVisitorTimeOut(new Date());
                Integer integer = updateVisitorLog(visitorLog);
                return integer == 1;
            }
        }
        return true;
    }

    private void checkLegal(List<VisitorLogQo> all) {
        for (VisitorLogQo visitorLogQo : all) {
            if (visitorLogQo.getVisitorTimeIn() != null && stringUtil.IsNotEmpty(visitorLogQo.getVisitorTimeIn().toString()) && stringUtil.IsNotEmpty(visitorLogQo.getVisitorId())) {
                visitorLogQo.setIsLegal(true);
            } else {
                visitorLogQo.setIsLegal(false);
            }
        }
    }

}
