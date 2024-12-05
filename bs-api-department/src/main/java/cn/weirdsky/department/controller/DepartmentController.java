package cn.weirdsky.department.controller;

import cn.weirdsky.entity.entity.R;
import cn.weirdsky.entity.entity.qo.DepartmentQo;
import cn.weirdsky.department.service.DepartmentService;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/department")
public class DepartmentController {

     /**
     * 引入micrometer的工具类
     */
    @Autowired
    private MeterRegistry meterRegistry;
    @Autowired
    private DepartmentService departmentService;

    @GetMapping("/getAll")
    public R getAll(){
        incrementMonitorConut("cn.weirdsky.department.controller.DepartmentController.getAll");
        return R.ok(departmentService.getAll());
    }

    @PostMapping("/saveOrUpdateDepartment")
    public R saveOrUpdateDepartment(@RequestBody DepartmentQo departmentQo){
        return departmentService.saveOrUpdateDepartment(departmentQo) ? R.okSave() : R.errorSave();
    }

    @PostMapping("/delDepartment")
    public R delDepartment(@RequestBody DepartmentQo departmentQo){
        return departmentService.deleteDepartment(departmentQo.getDepartmentIds()) > 0 ? R.okDel() : R.errorDel();
    }

    @GetMapping("/getMyDepartment")
    public R getMyDepartment(){
        return departmentService.getMyDepartment() ? R.ok(departmentService.getLoginDepartment()) : R.error();
    }

    @GetMapping("/getVisitorLogDepartmentVo")
    public R getVisitorLogDepartmentVo(){
        return R.ok(departmentService.getVisitorLogDepartmentVo());
    }

     /**
     * 记录接口调用次数
     * @param method
     */
    public void incrementMonitorConut(String method) {
        //定义指标名称
        String metricsName = "method_count";
        Counter counter = meterRegistry.counter(metricsName, "methodName", method);
        counter.increment();
    }

}
