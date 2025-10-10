package top.woniu.socket.message;

import lombok.Data;
import lombok.experimental.Accessors;
import top.woniu.socket.enums.MsgTypeEnum;

/**
 * 聊天室人数
 *
 * @author 程序员蜗牛
 */
@Data
@Accessors(chain = true)
public class ChatCountMessage implements Message {

    public static final String TYPE = MsgTypeEnum.CHAT_COUNT.getCode();

    /**
     * 消息编号
     */
    private String msgId;
    /**
     * 内容
     */
    private Integer count;

}
