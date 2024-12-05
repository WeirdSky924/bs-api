package cn.weirdsky.common.controller;

import cn.weirdsky.common.entity.Code;
import cn.weirdsky.common.entity.R;
import cn.weirdsky.common.entity.User;
import cn.weirdsky.common.entity.qo.UserQo;
import cn.weirdsky.common.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

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
}