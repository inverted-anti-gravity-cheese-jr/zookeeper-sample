package pl.stan.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import pl.stan.service.zookeeper.ZookeeperSessionStorage;

@RestController
@RequestMapping("/hello")
public class HelloWorldController {
    
    @Autowired
    private ZookeeperSessionStorage zookeeperSessionStorage;
    
    @RequestMapping(method = RequestMethod.GET)
    public String showHello() {
        try {
        System.out.println(zookeeperSessionStorage.getZNodes());
        } catch (Exception e) {}
        return "hello";
    }

}
