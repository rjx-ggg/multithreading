package com.woniu.dynamicsql;

import com.woniu.dynamicsql.mapper.DynamicSqlMapper;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
class DynamicsqlApplicationTests {

    // 测试类

    @Autowired
    private DynamicSqlMapper dynamicSqlMapper;

    @Test
    public void test() {
        Long count = dynamicSqlMapper.count();
    }

}
