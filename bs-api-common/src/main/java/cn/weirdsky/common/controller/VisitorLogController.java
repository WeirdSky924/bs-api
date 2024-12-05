package cn.weirdsky.common.controller;

import cn.weirdsky.common.entity.*;
import cn.weirdsky.common.entity.qo.VisitorLogQo;
import cn.weirdsky.common.entity.qo.VisitorSpaceQo;
import cn.weirdsky.common.service.VisitorLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.Date;

@RestController
@RequestMapping("/visitor_log")
public class VisitorLogController {

    @Autowired
    private VisitorLogService visitorLogService;

    /**
     * 进入时记录访客信息
     * @param visitor
     * @return
     */
    @PostMapping("/in")
    public R in(@RequestParam("file") MultipartFile visitor, @RequestParam("time")Date time) throws IOException {
        return visitorLogService.in(visitor, time) ? R.okSave() : R.errorSave();
    }

    /**
     * 出去时记录访客信息
     * @param visitor
     * @return
     */
    @PostMapping("/out")
    public R out(@RequestParam("file") MultipartFile visitor, @RequestParam("time")Date time) throws IOException {
        return visitorLogService.out(visitor, time) ? R.okSave() : R.errorSave();
    }

    /**
     * 进入时记录访客信息
     * @param postBody
     * @return
     */
    @PostMapping("/in2")
    public R in2(@RequestBody PostBody postBody) throws IOException {
        visitorLogService.in(postBody.getVisitorName(), postBody.getPicPath());
        return R.ok();
    }

    /**
     * 出去时记录访客信息
     * @param postBody
     * @return
     */
    @PostMapping("/out2")
    public R out2(@RequestBody PostBody postBody) throws IOException {
        visitorLogService.out(postBody.getVisitorName(), postBody.getPicPath());
        return R.ok();
    }

    @GetMapping("/getAll")
    public R getAll() {
        return R.ok(visitorLogService.getAll());
    }

    @PostMapping("/getBySearch")
    public R getBySearch(@RequestBody VisitorLogQo logQo){
        return R.ok(visitorLogService.getBySearch(logQo));
    }

    @PostMapping("/delLog")
    public R delLog(@RequestBody VisitorLogQo visitorLogQo) {
        return visitorLogService.deleteVisitorLog(visitorLogQo.getLogIds()) > 0 ? R.okDel() : R.errorDel();
    }

    @GetMapping("/getChartData")
    public R getChartData() {
        return R.ok(visitorLogService.getChartData());
    }

    @PostMapping("/getLogPic")
    public R getLogPic(@RequestBody VisitorLog visitorLog) {
        String str = Base64.getEncoder().encodeToString(visitorLogService.getLogPic(visitorLog.getVisitorLogId()));
        return R.ok("data:image/jpeg;base64," + str);
    }

    @PostMapping("/getLogBySpace")
    public R getLogBySpace(@RequestBody VisitorSpaceQo visitorSpaceQo) {
        return R.ok(visitorLogService.getLogBySpace(visitorSpaceQo));
    }

    @GetMapping("/openCV")
    public R openCV() {
        visitorLogService.openCV();
        return R.ok();
    }
}
