package kr.co.joneconsulrting.newmyrestfulservice.controller;

import kr.co.joneconsulrting.newmyrestfulservice.bean.HelloWorldBean;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

@RestController
public class HelloWorldController {
    private MessageSource messageSource;

    public HelloWorldController(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @GetMapping(path = "/hello-world")
    public String helloWorld() {
        return "Hello World";
    }

    @GetMapping(path = "/hello-world-bean")
    public HelloWorldBean helloWorldBean() {
        return new HelloWorldBean("Hello Wolrd");
    }

    // hello-world-bean/path-variable/kennet
    @GetMapping(path = "/hello-world-bean/path-variable/{name}")
    public HelloWorldBean helloWorldBean(@PathVariable String name) {
        return new HelloWorldBean(String.format("Hello World, %s ", name));
    }

    @GetMapping(path = "/hello-world-internationalized")
    public String helloWorldInternationalized(
            @RequestHeader(name="Accept-Language", required=false)  Locale locale) {
        return messageSource.getMessage("greeting.message", null, locale);
    }
}
