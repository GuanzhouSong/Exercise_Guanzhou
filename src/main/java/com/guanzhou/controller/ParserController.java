package com.guanzhou.controller;


import com.guanzhou.Model.UserInfo;
import com.guanzhou.Services.ParseService;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ParserController {

  @Autowired
  private ParseService parseService;

  @RequestMapping(value = "/", method = RequestMethod.GET)
  public ModelAndView homePage() {
    ModelAndView mv = new ModelAndView("index");
    String CSV_FILE_NAME = "userInfo.csv";

    //map userinfo to a string.
    List<List<UserInfo>> res = parseService.getPossibleDupUser(CSV_FILE_NAME,0,0);

    String error_msg = "unable to read the file, please check csv file again.";
    if(res==null || res.size()==0){
      mv.addObject("error", error_msg);
      return mv;
    }

    List<String> dupUserStr = res.get(0).stream().map(UserInfo::getAll).collect(Collectors.toList());
    List<String> no_dupUserStr = res.get(1).stream().map(UserInfo::getAll).collect(Collectors.toList());

    mv.addObject("dupUsers", dupUserStr);
    mv.addObject("noDupUsers", no_dupUserStr);
    return mv;
  }
}
