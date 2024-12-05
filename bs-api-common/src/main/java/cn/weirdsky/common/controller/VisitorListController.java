package cn.weirdsky.common.controller;

import cn.weirdsky.common.service.VisitorListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/visitor_list")
public class VisitorListController {

    @Autowired
    private VisitorListService visitorListService;

}
