package cn.weirdsky.common.service.impl;

import cn.weirdsky.common.entity.Visitor;
import cn.weirdsky.common.entity.VisitorList;
import cn.weirdsky.common.entity.VisitorLog;
import cn.weirdsky.common.entity.qo.DepartmentQo;
import cn.weirdsky.common.entity.qo.VisitorLogQo;
import cn.weirdsky.common.entity.qo.VisitorSpaceQo;
import cn.weirdsky.common.entity.qo.VisitorQo;
import cn.weirdsky.common.entity.vo.DepartmentVo;
import cn.weirdsky.common.entity.vo.VisitorLogVo;
import cn.weirdsky.common.mapper.*;
import cn.weirdsky.common.service.*;
import cn.weirdsky.common.util.LoginUtil;
import cn.weirdsky.common.util.StringUtil;
import cn.weirdsky.common.util.TableUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class StatisticsVisitorServiceImpl implements StatisticsVisitorService {

    @Autowired
    private VisitorMapper visitorMapper;
    @Autowired
    private VisitorListMapper visitorListMapper;
    @Autowired
    private VisitorSpaceMapper visitorSpaceMapper;
    @Autowired
    private VisitorLogMapper visitorLogMapper;
    @Autowired
    private DepartmentMapper departmentMapper;
    @Autowired
    private VisitorListService visitorListService;
    @Autowired
    private LoginUtil loginUtil;
    @Autowired
    private StringUtil stringUtil;
    @Autowired
    private TableUtil tableUtill;

    @Override
    public HashMap<String, List<HashMap<String, String>>> visitorStatistics() {
        String[] days = {
                "星期一",
                "星期二",
                "星期三 ",
                "星期四 ",
                "星期五 ",
                "星期六 ",
                "星期天 ",
        };
        List<VisitorLogVo> visitorLogVos = visitorMapper.visitorStatisticsGroupByDate();
        HashMap<String, List<HashMap<String, String>>> map = new HashMap<>();
        for (VisitorLogVo visitorLogVo : visitorLogVos) {
            if (stringUtil.IsEmpty(visitorLogVo.getVisitorName())) {
                continue;
            }
            Visitor byVisitorName = visitorMapper.getByVisitorName(visitorLogVo.getVisitorName());
            List<HashMap<String, String>> list = map.getOrDefault(visitorLogVo.getVisitorName(), new ArrayList<>());
            Date date = new Date();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            Date timeStart = calendar.getTime();
            calendar.add(Calendar.DATE, -7);
            Date timeEnd = calendar.getTime();
            int count = 0;
            QueryWrapper<VisitorLog> queryWrapper = new QueryWrapper<>();
            queryWrapper.between("l.visitorTime", timeEnd.toInstant().toString().split("T")[0],timeStart.toInstant().toString().split("T")[0]);
            queryWrapper.eq("l.visitorId", byVisitorName.getVisitorId());
            queryWrapper.eq("l.deleteMark", "0");
            List<VisitorLogQo> search = visitorLogMapper.getBySearch(queryWrapper);
            for (int i = 0; i < search.size(); i++) {
                Date time = search.get(i).getVisitorTime();
                String week = stringUtil.getWeek(time);
                if (byVisitorName.getAttendanceDays().indexOf(week) != -1) {
                    count++;
                }
            }
            double rate = count * 1.0 / byVisitorName.getAttendanceDays().split(",").length;
            String rateStr = new DecimalFormat("0.0000").format(rate);
            HashMap<String, String> info = new HashMap<>();
            info.put("year", visitorLogVo.getYear());
            info.put("month", visitorLogVo.getMonth());
            info.put("sum", visitorLogVo.getSum());
            info.put("rate", Double.parseDouble(String.valueOf(Double.parseDouble(rateStr) * 10000)) / 100.0 + "%");
            list.add(info);
            map.put(visitorLogVo.getVisitorName(), list);
        }
        return map;
    }

    @Override
    public HashMap<String, HashMap<String, String>> visitorByDepartmentStatistics() {
        List<DepartmentVo> departmentVos1 = departmentMapper.getVisitorLogDepartmentVo();
        List<DepartmentVo> departmentVos2 = visitorMapper.visitorStatisticGroupByDepartment();
        HashMap<String, HashMap<String, String>> map = new HashMap<>();
        for (DepartmentVo departmentVo : departmentVos1) {
            if (stringUtil.IsEmpty(departmentVo.getDepartmentName())) {
                continue;
            }
            HashMap<String, String> temp = new HashMap<>();
            temp.put("logCount", departmentVo.getVisitorCount().toString());
            map.put(departmentVo.getDepartmentName(), temp);
        }
        for (int i = 0; i < departmentVos2.size(); i++) {
            int dayCount;
            HashMap<String, String> temp = map.getOrDefault(departmentVos2.get(i).getDepartmentName(), new HashMap<>());
            List<Visitor> visitorByDepartmentName = visitorMapper.getVisitorByDepartmentName(departmentVos2.get(i).getDepartmentName());
            temp.put("visitorCount", String.valueOf(visitorByDepartmentName.size()));
            dayCount = visitorByDepartmentName.stream().mapToInt(data -> (data.getAttendanceDays().split(",").length)).sum();
            Date date = new Date();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            Date timeStart = calendar.getTime();
            calendar.add(Calendar.DATE, -30);
            Date timeEnd = calendar.getTime();
            QueryWrapper<VisitorLog> queryWrapper = new QueryWrapper<>();
            queryWrapper.between("l.visitorTime", timeEnd.toInstant().toString().split("T")[0],timeStart.toInstant().toString().split("T")[0]);
            queryWrapper.eq("v.visitorDepartmentId", visitorByDepartmentName.get(0).getVisitorDepartmentId());
            queryWrapper.eq("l.deleteMark", "0");
            List<VisitorLogQo> search = visitorLogMapper.getBySearch(queryWrapper);
            double rate = search.size() / (dayCount * 4.0);
            String rateStr = new DecimalFormat("0.0000").format(rate);
            temp.put("rate", Double.parseDouble(String.valueOf(Double.parseDouble(rateStr) * 10000)) / 100 + "%");
            map.put(departmentVos2.get(i).getDepartmentName(), temp);
        }
        return map;
    }

    @Override
    public HashMap<String, HashMap<String, String>> visitorBySpaceStatistics() {
        HashMap<String, HashMap<String, String>> map = new HashMap<>();
        List<VisitorSpaceQo> spaceQos = visitorSpaceMapper.getListBySearch(new QueryWrapper<>().eq("s.deleteMark", "0").eq("s.spaceEnable","1"));
        for (VisitorSpaceQo spaceQo : spaceQos) {
            HashMap<String, String> temp = new HashMap<>();
            if (stringUtil.IsEmpty(spaceQo.getDepartmentIds())) {
                spaceQo.setDepartmentIds("");
            }
            if (stringUtil.IsEmpty(spaceQo.getVisitorIds())){
                spaceQo.setVisitorIds("");
            }
            QueryWrapper<VisitorLogQo> queryWrapper = new QueryWrapper<>();
            queryWrapper.and(
                    q -> q.in(stringUtil.IsNotEmpty(spaceQo.getVisitorIds()), "l.visitorId", spaceQo.getVisitorIds().split(","))
                            .or().in(stringUtil.IsNotEmpty(spaceQo.getDepartmentIds()), "v.visitorDepartmentId", spaceQo.getDepartmentIds().split(","))
            );
            queryWrapper.between("l.visitorTimeIn", spaceQo.getSpaceStartTime().toInstant(), spaceQo.getSpaceEndTime().toInstant());
            List<VisitorLogQo> logBySpace = visitorLogMapper.getLogBySpace(queryWrapper);

            String[] departmentIds = spaceQo.getDepartmentIds().split(",");
            QueryWrapper<Visitor> queryWrapper2 = new QueryWrapper<>();
            queryWrapper2.eq("deleteMark", "0");
            queryWrapper2.in("visitorDepartmentId", departmentIds);
            List<Visitor> listByDepartmentIds = visitorMapper.getListBySearch(queryWrapper2);

            String[] visitorIds = spaceQo.getVisitorIds().split(",");
            QueryWrapper<Visitor> queryWrapper3 = new QueryWrapper<>();
            queryWrapper3.eq("deleteMark", "0");
            queryWrapper3.in("visitorId", visitorIds);
            List<Visitor> listByVisitorIds = visitorMapper.getListBySearch(queryWrapper3);

            HashSet<Visitor> visitors = new HashSet<>();
            visitors.addAll(listByDepartmentIds);
            visitors.addAll(listByVisitorIds);

            List<Visitor> list = visitors.stream().collect(Collectors.toList());
            int dayCount = list.stream().mapToInt(data -> (data.getAttendanceDays().split(",").length)).sum();
            double rate = logBySpace.size() / (dayCount * 1.0);
            String rateStr = new DecimalFormat("0.0000").format(rate);
            temp.put("rate", Double.parseDouble(String.valueOf(Double.parseDouble(rateStr) * 10000)) / 100 + "%");
            temp.put("logCount", String.valueOf(logBySpace.size()));
            temp.put("visitorCount", String.valueOf(list.size()));
            map.put(spaceQo.getSpaceName(), temp);
        }
        return map;
    }
}
