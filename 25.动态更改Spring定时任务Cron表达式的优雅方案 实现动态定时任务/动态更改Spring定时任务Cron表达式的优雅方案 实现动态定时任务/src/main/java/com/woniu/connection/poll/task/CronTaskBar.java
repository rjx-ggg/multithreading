package com.woniu.connection.poll.task;

import com.woniu.connection.poll.IPollableService;
import org.springframework.stereotype.Service;

@Service
public class CronTaskBar implements IPollableService {
    @Override
    public void poll() {
        System.out.println("Say Bar");
    }

    @Override
    public String getCronExpression() {
        return "0/1 * * * * ?";
    }
}