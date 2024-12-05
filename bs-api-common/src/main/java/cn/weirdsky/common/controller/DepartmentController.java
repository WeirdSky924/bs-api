package cn.weirdsky.common.controller;

import cn.weirdsky.common.entity.Code;
import cn.weirdsky.common.entity.R;
import cn.weirdsky.common.entity.qo.DepartmentQo;
import cn.weirdsky.common.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/department")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @GetMapping("/getAll")
    public R getAll(){
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

}
