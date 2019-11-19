package com.example.demo.线程封闭example.threadlocal;

import com.example.demo.线程封闭example.threadlocal.RequestHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/threadLocal")
public class ThreadLocalController {
    
    @RequestMapping("/test")
    @ResponseBody
    public Long test(){
        return RequestHolder.getId();
    }
    
}
