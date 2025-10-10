package com.woniu;

import com.woniu.controller.SayHelloController;
import com.woniu.entity.Person;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({SayHelloController.class, Person.class})
public class SayHiAutoConfiguration {

}
