package com.amher.page.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
/**
 * @author yucheng
 * @version 1
 * */
@Controller
public class AmherPageControllerImpl {

 @RequestMapping(method=RequestMethod.GET, value="/whatever")
 public @ResponseBody String home() {
  return "home";
 }
 
}