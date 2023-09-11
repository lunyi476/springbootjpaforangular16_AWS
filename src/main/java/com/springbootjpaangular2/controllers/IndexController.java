package com.springbootjpaangular2.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;

/** 
 *  See pom.xml gulp and npm, node exc plugin to deploy angular production to dist and final go to:
 *  embedded angular to folder src/main/resource/static 
 *  https://medium.com/bb-tutorials-and-thoughts/aws-deploying-angular-app-with-java-on-elastic-beanstalk-f3db3d6a7abe
 */
@Controller
@RequiredArgsConstructor
public class IndexController {
    @RequestMapping("/")
    String index(){
    	return "forward: index.html";
    }
}
