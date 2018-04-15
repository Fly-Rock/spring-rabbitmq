package rock.springboot.rabbitmq.basemessage;

import java.util.Date;

/**
 * Created by lichuanjie on 2018/4/6.
 */
public class MessageLog {
    /**
     *
     */
    private Long id;

    /**
     *
     */
    private String messageId;

    /**
     *
     */
    private Date sendDateTime;

    /**
     *
     */
    private String messageCotent;

    /**
     * 发送端Id
     */
    private String produceId;

    /**
     * 发送消息端IP
     */
    private String sendMachineIp;

    /**
     * The constant serialVersionUID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * 获取
     *
     * @return
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置
     *
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取
     *
     * @return
     */
    public String getMessageId() {
        return messageId;
    }

    /**
     * 设置
     *
     * @param messageId
     */
    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    /**
     * 获取
     *
     * @return
     */
    public Date getSendDateTime() {
        return sendDateTime;
    }

    /**
     * 设置
     *
     * @param sendDateTime
     */
    public void setSendDateTime(Date sendDateTime) {
        this.sendDateTime = sendDateTime;
    }

    /**
     * 获取
     *
     * @return
     */
    public String getMessageCotent() {
        return messageCotent;
    }

    /**
     * 设置
     *
     * @param messageCotent
     */
    public void setMessageCotent(String messageCotent) {
        this.messageCotent = messageCotent;
    }

    /**
     * 获取发送端Id
     *
     * @return 发送端Id
     */
    public String getProduceId() {
        return produceId;
    }

    /**
     * 设置发送端Id
     *
     * @param produceId 发送端Id
     */
    public void setProduceId(String produceId) {
        this.produceId = produceId;
    }

    /**
     * 获取发送消息端IP
     *
     * @return 发送消息端IP
     */
    public String getSendMachineIp() {
        return sendMachineIp;
    }

    /**
     * 设置发送消息端IP
     *
     * @param sendMachineIp 发送消息端IP
     */
    public void setSendMachineIp(String sendMachineIp) {
        this.sendMachineIp = sendMachineIp;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", messageId=").append(messageId);
        sb.append(", sendDateTime=").append(sendDateTime);
        sb.append(", messageCotent=").append(messageCotent);
        sb.append(", produceId=").append(produceId);
        sb.append(", sendMachineIp=").append(sendMachineIp);
        sb.append("]");
        return sb.toString();
    }
}
