package com.fen.fund.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Fei
 * @date 2020-01-14
 */
@RestController
public class ThymeController {

    @RequestMapping("/index")
    public ModelAndView demo() {
        return new ModelAndView("index");
    }

}
