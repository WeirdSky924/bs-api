package cn.weirdsky.login.controller;

import cn.weirdsky.entity.entity.Code;
import cn.weirdsky.entity.entity.LoginUser;
import cn.weirdsky.entity.entity.R;
import cn.weirdsky.entity.entity.qo.UserQo;
import cn.weirdsky.login.service.LoginService;
import cn.weirdsky.login.service.UserService;
import cn.weirdsky.utils.util.LoginUtil;
import cn.weirdsky.utils.util.StringUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
//import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private LoginService loginService;
    @Autowired
    private UserService userService;
    @Autowired
    private StringUtil stringUtil;
    @Autowired
    private LoginUtil loginUtil;
//    @Autowired
//    private RabbitTemplate rabbitTemplate;
//    @Autowired
//    private ElasticsearchOperations esTemplate;

    /**
     * 判断登录情况，并设置session
     * @param userQo
     * @param request
     * @return
     */
    @PostMapping("/getLogin")
    public R getLogin(@RequestBody UserQo userQo, HttpServletRequest request) throws ServletException {
        loginService.logOut(request.getSession());
        Boolean login = loginService.getLogin(userQo);
        if (!login) {
            return R.errorMsg("账号密码错误！");
        }
        HttpSession session = request.getSession();
        LoginUser user = loginUtil.getLoginUser();
        Map<String, String> attr = (Map<String, String>) session.getAttribute("attr");
        if (attr == null) {
            attr = new HashMap<>();
        }
//        rabbitTemplate.convertAndSend("user","user");

        attr.put("token", stringUtil.getToken(user));
        session.setAttribute("attr", attr);
        session.setAttribute("user", user);
        return R.ok(attr);
    }

    /**
     * 登出
     * @param request
     * @return
     * @throws ServletException
     */
    @DeleteMapping("/logout")
    public R logout(HttpServletRequest request) throws ServletException {
        Boolean re = loginService.logOut(request.getSession());
        return re?R.ok(Code.BUSINESS_ERR, "登出成功！"):R.error();
    }

    /**
     * 获取当前用户的token
     *
     * @param request
     * @return
     */
    @GetMapping("/getToken")
    public R getToken(HttpServletRequest request) {
        Map<String, String> attr = (Map<String, String>) request.getSession().getAttribute("attr");
        if (attr == null) {
            return R.error();
        }
        String userToken = attr.getOrDefault("token", "");
        if (!userToken.equals("")){
            return R.ok(request.getSession().getAttribute("user"));
        } else {
            return R.error();
        }
    }

    /**
     * 注册新的普通用户
     * @param userQo
     * @return
     */
    @PostMapping("/reg")
    public R regUser(@RequestBody UserQo userQo){
        return loginService.regUser(userQo) ? R.ok(Code.SAVE_OK, "注册成功！") : R.error(Code.SAVE_ERR, "注册失败");
    }
}
