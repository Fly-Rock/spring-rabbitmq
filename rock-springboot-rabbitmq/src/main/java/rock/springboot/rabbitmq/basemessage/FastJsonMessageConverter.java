package rock.springboot.rabbitmq.basemessage;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.google.common.base.Charsets;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.support.converter.MessageConversionException;

import java.io.IOException;

/**
 * Created by lichuanjie on 2018/4/5.
 */
public class FastJsonMessageConverter  implements org.springframework.amqp.support.converter.MessageConverter{

    protected transient Logger log = LoggerFactory.getLogger(this.getClass());

    private JsonMessageBodyConverter messageBodyConverter = null;

    public FastJsonMessageConverter() {
        this(new JsonMessageBodyConverterImpl());
    }

    public FastJsonMessageConverter(JsonMessageBodyConverter messageBodyConverter) {
        super();

        this.messageBodyConverter = messageBodyConverter;
    }

    @Override
    public Message toMessage(Object object, MessageProperties messageProperties) throws MessageConversionException {

        String charsetName = this.getDefaultCharset(messageProperties);
        byte[] bytes;
        try {
            String jsonString = JSON.toJSONString(object, SerializerFeature.WriteMapNullValue);
            bytes = jsonString.getBytes(charsetName);
        } catch (IOException e) {
            throw new MessageConversionException(
                    "Failed to convert Message content", e);
        }

        if (messageProperties != null) {
            messageProperties.setContentType(MessageProperties.CONTENT_TYPE_JSON);
            if (StringUtils.isBlank(messageProperties.getContentEncoding())) {
                messageProperties.setContentEncoding(charsetName);
            }
            messageProperties.setContentLength(bytes.length);
        }

        return new Message(bytes, messageProperties);
    }

    @Override
    public Object fromMessage(Message message) throws MessageConversionException {
        Object content = null;
        MessageProperties properties = message.getMessageProperties();
        if (properties != null) {
            String contentType = properties.getContentType();
            if (contentType != null && contentType.contains("json")) {
                String encoding = this.getDefaultCharset(properties);

                byte[] bytes = message.getBody();
                if (bytes != null && this.messageBodyConverter != null) {
                    try{
                        content = this.messageBodyConverter.fromMessage(bytes, encoding, properties);
                        log.info("解析：{}",JSON.toJSONString(content));
                    }catch(Exception e){
                        throw new MessageConversionException("Failed to convert Message content");
                    }
                } else {
                    throw new MessageConversionException("Failed to convert Message content");
                }
            } else {
                if (log.isWarnEnabled()) {
                    log.warn("Could not convert incoming basemessage with content-type ["
                            + contentType + "]");
                }
                throw new MessageConversionException("Failed to convert Message content");
            }
        }
        if (content == null) {
            content = message.getBody();
        }
        return content;
    }

    private String getDefaultCharset(MessageProperties messageProperties) {

        if (messageProperties != null && StringUtils.isNotBlank(messageProperties.getContentEncoding())) {
            return messageProperties.getContentEncoding();
        }

        return Charsets.UTF_8.name();
    }
}
