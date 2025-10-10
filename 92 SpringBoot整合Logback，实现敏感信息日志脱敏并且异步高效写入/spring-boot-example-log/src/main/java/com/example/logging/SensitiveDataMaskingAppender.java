package com.example.logging;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;
import com.lmax.disruptor.BusySpinWaitStrategy;
import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SensitiveDataMaskingAppender extends AppenderBase<ILoggingEvent> {

    // 正则表达式模式用于匹配身份证号码
    private static final Pattern ID_CARD_PATTERN = Pattern.compile("\\d{15}(\\d{2}[A-Za-z])?");
    // 正则表达式模式用于匹配手机号码
    private static final Pattern PHONE_NUMBER_PATTERN = Pattern.compile("(\\+86)?(1[3-9]\\d{9})");

    private RingBuffer<Event> ringBuffer;

    @Override
    public void start() {
        super.start();

        // 创建事件工厂
        EventFactory<Event> factory = Event::new;
        // 设置环形缓冲区大小，必须是2的幂
        int bufferSize = 1024;
        // 使用缓存线程池
        Executor executor = Executors.newCachedThreadPool();
        // 创建Disruptor实例
        Disruptor<Event> disruptor = new Disruptor<>(factory,
                bufferSize, executor, ProducerType.MULTI, new BusySpinWaitStrategy());
        // 设置事件处理器
        disruptor.handleEventsWith((event, sequence, endOfBatch) -> {
            // 脱敏日志消息
            String logMessage = maskSensitiveData(event.getLogMessage());
            // 打印脱敏后的日志消息到控制台
            System.out.println(logMessage);
        });
        // 获取RingBuffer
        ringBuffer = disruptor.getRingBuffer();
        // 启动Disruptor
        disruptor.start();
    }

    @Override
    protected void append(ILoggingEvent eventObject) {
        // 获取下一个序列号
        long sequence = ringBuffer.next();
        try {
            // 根据序列号获取事件对象
            Event event = ringBuffer.get(sequence);
            // 设置日志消息
            event.setLogMessage(eventObject.getFormattedMessage());
        } finally {
            // 发布事件
            ringBuffer.publish(sequence);
        }
    }

    /**
     * 脱敏日志消息中的敏感信息
     *
     * @param message 日志消息
     * @return 脱敏后的日志消息
     */
    private String maskSensitiveData(String message) {
        Matcher idCardMatcher = ID_CARD_PATTERN.matcher(message);
        while (idCardMatcher.find()) {
            // 替换身份证号码中间部分为星号
            String maskedIdCard = idCardMatcher.group().substring(0, 6) + "********" + idCardMatcher.group().substring(14);
            message = message.replace(idCardMatcher.group(), maskedIdCard);
        }

        Matcher phoneNumberMatcher = PHONE_NUMBER_PATTERN.matcher(message);
        while (phoneNumberMatcher.find()) {
            // 替换手机号码中间部分为星号
            String maskedPhoneNumber = phoneNumberMatcher.group().substring(0, 3) + "****" + phoneNumberMatcher.group().substring(7);
            message = message.replace(phoneNumberMatcher.group(), maskedPhoneNumber);
        }

        return message;
    }


}