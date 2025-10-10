package top.woniu.socket.server;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import top.woniu.socket.config.HttpSessionConfig;
import top.woniu.socket.context.WebSocketContext;
import top.woniu.socket.enums.MsgTypeEnum;
import top.woniu.socket.message.ChatMsgMessage;

import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;

/**
 * 配置接入点
 * ServerEndpoint 注解是一个类层次的注解，它的功能主要是将目前的类定义成一个websocket服务器端,
 * 注解的值将被用于监听用户连接的终端访问URL地址,客户端可以通过这个URL来连接到WebSocket服务器端
 * @author 程序员蜗牛
 *
 *
 * 实战！websocket实现在线聊天室功能！
 */
@Component
@ServerEndpoint(value = "/chat", configurator = HttpSessionConfig.class)
@Slf4j
public class WebSocketServer {

    HttpSession httpSession;

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session, EndpointConfig config) {
        httpSession = (HttpSession) config.getUserProperties().get(HttpSession.class.getName());
        String username = (String) httpSession.getAttribute("username");
        // 上线，并且通知到其他人
        WebSocketContext.add(username, session);
        WebSocketContext.countNotice();
        log.info("===> onOpen:{}", username);
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose(Session session) {
        // 下线，并且通知到其他人
        String username = (String) httpSession.getAttribute("username");
        WebSocketContext.remove(username);
        WebSocketContext.countNotice();
        log.info("===> onClose:{}", username);
    }

    /**
     * 收到客户端消息后调用的方法
     * @param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        log.info("===> message:{}", message);
        // 进行消息的转发，同步到其他的客户端上
        ChatMsgMessage msg = JSON.parseObject(message, ChatMsgMessage.class);
        WebSocketContext.broadcast(MsgTypeEnum.CHAT_MSG.getCode(), msg, session);
    }

    /**
     * 监听错误
     *
     * @param session session
     * @param error   错误
     */
    @OnError
    public void onError(Session session, Throwable error) {
        log.error("出现异常：{}", error.getMessage());
        error.printStackTrace();
    }

}
