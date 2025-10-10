package com.woniu;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author woniu
 * @description:
 * @date 2024/2/19
 */
@Slf4j
@ComponentScan(value = {"com.woniu"})
@EnableConfigurationProperties(GlobalProperties.class)
public class EncryGlobalAutoConfiguration {

}
