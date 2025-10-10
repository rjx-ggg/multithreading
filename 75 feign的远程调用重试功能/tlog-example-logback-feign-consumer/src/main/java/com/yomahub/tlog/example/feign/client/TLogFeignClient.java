package com.yomahub.tlog.example.feign.client;

import com.yomahub.tlog.example.feign.inteferce.Backoff;
import com.yomahub.tlog.example.feign.inteferce.FeignRetry;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("tlog-logback-feign-provider")
public interface TLogFeignClient {

    @RequestMapping(value = "hi",method = RequestMethod.GET)
    @FeignRetry(maxAttempt = 6, backoff = @Backoff(delay = 500L, maxDelay = 20000L, multiplier = 4))
    public String sayHello(@RequestParam(value = "name") String name);


}
