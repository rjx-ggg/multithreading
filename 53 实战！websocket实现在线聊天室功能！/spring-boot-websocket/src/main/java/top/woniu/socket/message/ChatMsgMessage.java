package top.woniu.socket.message;

import lombok.Data;
import lombok.experimental.Accessors;
import top.woniu.socket.enums.MsgTypeEnum;

/**
 * 发送给所有人的群聊消息的 Message
 *
 * @author 程序员蜗牛
 */
@Data
@Accessors(chain = true)
public class ChatMsgMessage implements Message {

    public static final String TYPE = MsgTypeEnum.CHAT_MSG.getCode();

    /**
     * 消息编号
     */
    private String msgId;
    /**
     * 内容
     */
    private String msg;

}
