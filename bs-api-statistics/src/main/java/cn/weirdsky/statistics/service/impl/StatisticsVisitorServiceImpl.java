package cn.weirdsky.statistics.service.impl;


import cn.weirdsky.statistics.clients.DepartmentFeignClient;
import cn.weirdsky.statistics.clients.VisitorFeignClient;
import cn.weirdsky.entity.entity.Visitor;
import cn.weirdsky.entity.entity.VisitorLog;
import cn.weirdsky.entity.entity.qo.VisitorLogQo;
import cn.weirdsky.entity.entity.qo.VisitorSpaceQo;
import cn.weirdsky.entity.entity.vo.DepartmentVo;
import cn.weirdsky.entity.entity.vo.VisitorLogVo;
import cn.weirdsky.statistics.service.StatisticsVisitorService;
import cn.weirdsky.utils.util.*;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class StatisticsVisitorServiceImpl implements StatisticsVisitorService {


    private final DepartmentFeignClient departmentFeignClient;

    private final VisitorFeignClient visitorFeignClient;
    @Autowired
    private LoginUtil loginUtil;
    @Autowired
    private StringUtil stringUtil;
    @Autowired
    private TableUtil tableUtill;
    @Autowired
    private CastUtil castUtil;
    @Autowired
    private QueryWrapperUtil queryWrapperUtil;
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    public StatisticsVisitorServiceImpl(DepartmentFeignClient departmentFeignClient, VisitorFeignClient visitorFeignClient) {
        this.departmentFeignClient = departmentFeignClient;
        this.visitorFeignClient = visitorFeignClient;
    }

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
        List<VisitorLogVo> visitorLogVos = castUtil.ConvertToList((List<LinkedHashMap<String, Object>>) (visitorFeignClient.visitorStatisticsGroupByDate().getData()), VisitorLogVo.class);
        HashMap<String, List<HashMap<String, String>>> map = new HashMap<>();
        for (VisitorLogVo visitorLogVo : visitorLogVos) {
            if (stringUtil.IsEmpty(visitorLogVo.getVisitorName())) {
                continue;
            }
            Visitor byVisitorName = castUtil.ConvertToObject((LinkedHashMap<String, Object>)visitorFeignClient.getByVisitorName(visitorLogVo.getVisitorName()).getData(), Visitor.class);
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
//            List<VisitorLogQo> search = visitorLogMapper.getBySearch(queryWrapper);
            List<VisitorLogQo> search = castUtil.ConvertToList((List<LinkedHashMap<String, Object>>) (visitorFeignClient.getBySearch(queryWrapperUtil.getSqlWithValues(queryWrapper)).getData()), VisitorLogQo.class);
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
        List<DepartmentVo> departmentVos1 = castUtil.ConvertToList((List<LinkedHashMap<String, Object>>) (departmentFeignClient.getVisitorLogDepartmentVo().getData()), DepartmentVo.class);
        List<DepartmentVo> departmentVos2 = castUtil.ConvertToList((List<LinkedHashMap<String, Object>>) (visitorFeignClient.visitorStatisticGroupByDepartment().getData()), DepartmentVo.class);
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
            List<Visitor> visitorByDepartmentName = castUtil.ConvertToList((List<LinkedHashMap<String, Object>>) (visitorFeignClient.getVisitorByDepartmentName(departmentVos2.get(i).getDepartmentName())).getData(), Visitor.class);
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
            List<VisitorLogQo> search = castUtil.ConvertToList((List<LinkedHashMap<String, Object>>) (visitorFeignClient.getBySearch(queryWrapperUtil.getSqlWithValues(queryWrapper)).getData()), VisitorLogQo.class);
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
        QueryWrapper<VisitorSpaceQo> eq = new QueryWrapper<>();
        eq.eq("s.deleteMark", "0").eq("s.spaceEnable", "1");
        List<VisitorSpaceQo> spaceQos = castUtil.ConvertToList((List<LinkedHashMap<String, Object>>) (visitorFeignClient.getListBySearchSpace(queryWrapperUtil.getSqlWithValues(eq)).getData()), VisitorSpaceQo.class);
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
            List<VisitorLogQo> logBySpace = castUtil.ConvertToList((List<LinkedHashMap<String, Object>>) (visitorFeignClient.getLogBySpace(queryWrapperUtil.getSqlWithValues(queryWrapper)).getData()), VisitorLogQo.class);

            String[] departmentIds = spaceQo.getDepartmentIds().split(",");
            QueryWrapper<Visitor> queryWrapper2 = new QueryWrapper<>();
            queryWrapper2.eq("deleteMark", "0");
            queryWrapper2.in("visitorDepartmentId", departmentIds);
            List<Visitor> listByDepartmentIds = castUtil.ConvertToList((List<LinkedHashMap<String, Object>>) (visitorFeignClient.getListBySearchVisitor(queryWrapperUtil.getSqlWithValues(queryWrapper2)).getData()), Visitor.class);

            String[] visitorIds = spaceQo.getVisitorIds().split(",");
            QueryWrapper<Visitor> queryWrapper3 = new QueryWrapper<>();
            queryWrapper3.eq("deleteMark", "0");
            queryWrapper3.in("visitorId", visitorIds);
            List<Visitor> listByVisitorIds = castUtil.ConvertToList((List<LinkedHashMap<String, Object>>) (visitorFeignClient.getListBySearchVisitor(queryWrapperUtil.getSqlWithValues(queryWrapper3)).getData()), Visitor.class);

            HashSet<Visitor> visitors = new HashSet<>();
            if (!listByDepartmentIds.isEmpty())
                visitors.addAll(listByDepartmentIds);
            if (!listByVisitorIds.isEmpty())
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
