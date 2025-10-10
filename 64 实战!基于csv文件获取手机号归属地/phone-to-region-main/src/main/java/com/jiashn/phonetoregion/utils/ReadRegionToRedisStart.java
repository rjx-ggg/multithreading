package com.jiashn.phonetoregion.utils;

import com.jiashn.phonetoregion.domain.RegionVo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @author: woniu
 * @description:
 **/
@ConditionalOnProperty(havingValue = "true",value = "phoneToRegion.enabled")
@Component
public class ReadRegionToRedisStart implements CommandLineRunner {

    private final static String REGION_CSV_PATH = "classpath:/static/region/phonetmp.csv";

    private final static String PHONE_REGION_KEY = "country_phone_region_info";

    @Resource
    private ResourceLoader resourceLoader;
    @Resource
    private RedisTemplate<String,Object> redisTemplate;


    @Override
    public void run(String... args) {
        long size = redisTemplate.opsForHash().size(PHONE_REGION_KEY);
        if (size <= 0) {
            try (InputStream ism = resourceLoader.getResource(REGION_CSV_PATH).getInputStream()) {
                Assert.notNull(ism, "读取手机号信息文件为空");
                BufferedReader reader = new BufferedReader(new InputStreamReader(ism));
                String line = reader.readLine();
                while (!StringUtils.isEmpty(line)) {
                    String[] lineVal = line.split(",");
                    RegionVo regionVo = new RegionVo();
                    regionVo.setPhonePrefix(lineVal[0]).setProvince(lineVal[1]).setCity(lineVal[2]).setCarrier(lineVal[3]);
                    redisTemplate.opsForHash().put(PHONE_REGION_KEY, lineVal[0], regionVo);
                    line = reader.readLine();
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("获取手机号信息报错");
            }
        }

    }
}
