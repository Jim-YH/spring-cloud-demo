package jim.demo.spring.cloud.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author jim
 * @date 2018/4/27
 */
@RestController
@RequestMapping("config")
public class ConfigController {

    @Value("${value}")
    String value;

    @GetMapping(value = "getValue", produces = "application/json")
    public String getValue(){
        return value;
    }

}
