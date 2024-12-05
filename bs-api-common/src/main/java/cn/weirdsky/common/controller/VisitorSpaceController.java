package cn.weirdsky.common.controller;

import cn.weirdsky.common.entity.R;
import cn.weirdsky.common.entity.qo.VisitorSpaceQo;
import cn.weirdsky.common.service.VisitorSpaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/visitor_space")
public class VisitorSpaceController {

    @Autowired
    private VisitorSpaceService visitorSpaceService;

    @GetMapping("/getAll")
    public R getAll(){
        return R.ok(visitorSpaceService.getAll());
    }

    /**
     * 保存或者更新访客空间
     * @param visitorSpaceQo
     * @return
     */
    @PostMapping("/saveOrUpdateSpace")
    public R saveOrUpdateSpace(@RequestBody VisitorSpaceQo visitorSpaceQo){
        return visitorSpaceService.saveOrUpdateSpace(visitorSpaceQo) ? R.okSave() : R.errorSave();
    }

    /**
     * 删除访客空间
     * @param visitorSpaceQo
     * @return
     */
    @PostMapping("/delSpace")
    public R delSpace(@RequestBody VisitorSpaceQo visitorSpaceQo) {
        return visitorSpaceService.deleteVisitorSpace(visitorSpaceQo.getSpaceIds()) > 0 ? R.okDel() : R.errorDel();
    }

    @GetMapping("/getMySpace")
    public R getMySpace() {
        return R.ok(visitorSpaceService.getMySpace());
    }
}
