package cn.weirdsky.common.controller;

import cn.weirdsky.common.entity.Code;
import cn.weirdsky.common.entity.R;
import cn.weirdsky.common.entity.Visitor;
import cn.weirdsky.common.entity.qo.VisitorQo;
import cn.weirdsky.common.entity.qo.VisitorSpaceQo;
import cn.weirdsky.common.service.VisitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/visitor")
public class VisitorController {

    @Autowired
    private VisitorService visitorService;

    @GetMapping("/getAll")
    public R getAll() {
        return R.ok(visitorService.getAll());
    }

    /**
     * 接收ZIP
     *
     * @param file
     * @return
     */
    @PostMapping("/getVisitorZip")
    public R getVisitorZip(@RequestParam("file") MultipartFile file) {
        return visitorService.getVisitorZip(file) ? R.okSave() : R.errorSave();
    }

    /**
     * 接收ZIP
     *
     * @param file
     * @return
     */
    @PostMapping("/getVisitorExcel")
    public R getVisitorExcel(@RequestParam("file") MultipartFile file) {
        return visitorService.getVisitorExcel(file) ? R.okSave(): R.errorSave();
    }

    /**
     * 保存或者更新登记人员
     * @param file
     * @param visitorName
     * @return
     */
    @PostMapping("/saveOrUpdateVisitor")
    public R saveOrUpdateVisitor(@RequestParam("file") MultipartFile file, @RequestParam("visitor") String visitorName) {
        return visitorService.saveOrUpdateVisitor(file, visitorName) ? R.okSave() : R.errorSave();
    }

    @PostMapping("/saveVisitor")
    public R saveVisitor(@RequestBody Visitor visitor) {
        return visitorService.saveVisitor(visitor) ? R.okSave() : R.errorSave();
    }

    /**
     * 删除登记人员
     * @param visitorQo
     * @return
     */
    @PostMapping("/delVisitor")
    public R delVisitor(@RequestBody VisitorQo visitorQo) {
        return visitorService.deleteVisitor(visitorQo.getVisitorIds()) > 0 ? R.okDel() : R.errorDel();
    }

    @PostMapping("/getBySpace")
    public R getBySpace(@RequestBody VisitorSpaceQo space) {
        return R.ok(visitorService.getBySpace(space));
    }

    @PostMapping("/updateVisitor")
    public R updateVisitor(@RequestBody VisitorQo visitorQo) {
        return R.ok(visitorService.updateVisitor(visitorQo));
    }
}
