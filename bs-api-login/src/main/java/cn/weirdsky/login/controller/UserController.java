package cn.weirdsky.login.controller;

import cn.weirdsky.entity.entity.Code;
import cn.weirdsky.entity.entity.R;
import cn.weirdsky.entity.entity.qo.UserQo;
import cn.weirdsky.login.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/getAll")
    public R getAll() {
        return R.ok(userService.getAll());
    }

    /**
     * 获得登录用户
     * @return
     */
    @GetMapping("/getInfo")
    public R getInfo(){
        return R.ok(userService.getInfo());
    }

    /**
     * 修改当前登录用户的信息
     * @param userQo
     * @return
     */
    @PostMapping("/updateInfo")
    public R updateInfo(@RequestBody UserQo userQo) {
        return userService.alterUser(userQo) ? R.ok(Code.SAVE_OK, "修改成功！") : R.error(Code.SAVE_ERR, "修改失败！");
    }

    @PostMapping("/delUser")
    public R delUser(@RequestBody UserQo userQo){
        return userService.deleteUser(userQo.getUserIds()) > 0 ? R.okDel() : R.errorDel();
    }

    @PostMapping("/valSession")
    public R valSession(@RequestParam("sessionId") String sessionId){
        return userService.valSession(sessionId) ? R.ok() : R.error();
    }
}
