package top.woniu.socket.context;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import top.woniu.socket.enums.MsgTypeEnum;
import top.woniu.socket.message.ChatCountMessage;
import top.woniu.socket.message.Message;

import javax.websocket.RemoteEndpoint;
import javax.websocket.Session;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * WebSocket 会话上下文工具
 *
 * @author 程序员蜗牛
 */
@Slf4j
public class WebSocketContext {


    /**
     * 用户与 Session 的映射
     */
    private static final Map<String, Session> USER_SESSION_MAP = new ConcurrentHashMap<>();

    /**
     * 添加 Session 在这个方法中，会绑定用户和 Session 之间的映射
     *
     * @param session Session
     * @param user    用户
     */
    public static void add(String user, Session session) {
        // 更新 USER_SESSION_MAP , 这里的 user 正常来讲应该是具体的用户id
        USER_SESSION_MAP.put(user, session);
    }

    /**
     * 移除 Session
     */
    public static void remove(String username) {
        // 从 USER_SESSION_MAP 中移除
        USER_SESSION_MAP.remove(username);
    }


    /**
     * 广播发送消息给所有在线用户
     *
     * @param type    消息类型
     * @param message 消息体
     * @param <T>     消息类型
     * @param me      当前消息的发送者，不会将消息发送给自己
     */
    public static <T extends Message> void broadcast(String type, T message, Session me) {
        // 创建消息
        String messageText = buildTextMessage(type, message);
        // 遍历 SESSION_USER_MAP ，进行逐个发送
        for (Session session : USER_SESSION_MAP.values()) {
            if (!session.equals(me)) {
                sendTextMessage(session, messageText);
            }
        }
    }

    /**
     * 发送消息给单个用户的 Session
     *
     * @param session Session
     * @param type    消息类型
     * @param message 消息体
     * @param <T>     消息类型
     */
    public static <T extends Message> void send(Session session, String type, T message) {
        // 创建消息
        String messageText = buildTextMessage(type, message);
        // 遍历给单个 Session ，进行逐个发送
        sendTextMessage(session, messageText);
    }

    /**
     * 发送消息给指定用户
     *
     * @param user    指定用户
     * @param type    消息类型
     * @param message 消息体
     * @param <T>     消息类型
     * @return 发送是否成功
     */
    public static <T extends Message> boolean send(String user, String type, T message) {
        // 获得用户对应的 Session
        Session session = USER_SESSION_MAP.get(user);
        if (session == null) {
            log.error("==> user({}) 不存在对应的 session", user);
            return false;
        }
        // 发送消息
        send(session, type, message);
        return true;
    }

    /**
     * 构建完整的消息
     *
     * @param type    消息类型
     * @param message 消息体
     * @param <T>     消息类型
     * @return 消息
     */
    private static <T extends Message> String buildTextMessage(String type, T message) {
        JSONObject messageObject = new JSONObject();
        messageObject.put("type", type);
        messageObject.put("body", message);
        return messageObject.toString();
    }

    /**
     * 真正发送消息
     *
     * @param session     Session
     * @param messageText 消息
     */
    private static void sendTextMessage(Session session, String messageText) {
        if (session == null) {
            log.error("===> session 为 null");
            return;
        }
        RemoteEndpoint.Basic basic = session.getBasicRemote();
        if (basic == null) {
            log.error("===> session.basic 为 null");
            return;
        }
        try {
            basic.sendText(messageText);
        } catch (IOException e) {
            log.error("===> session: {} 发送消息: {} 发生异常", session, messageText, e);
        }
    }

    /**
     * 在线人数通知
     */
    public static void countNotice() {
        Integer count = USER_SESSION_MAP.size();
        ChatCountMessage message = new ChatCountMessage();
        message.setCount(count);
        broadcast(MsgTypeEnum.CHAT_COUNT.getCode(), message, null);
    }
}
