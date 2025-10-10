package com.example.rabbitmq.junit;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ControllerTest {

    @Autowired
    private TestRestTemplate testRestTemplate;


    @Test
    public void testController() throws Exception {
        testRestTemplate.postForObject("/sendMailToDB?to={to}&title={title}&content={content}", null, String.class, "xxxx@qq.com", "标题", "正文");
    }
}
