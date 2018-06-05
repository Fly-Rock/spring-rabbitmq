package springeventbus;

import com.google.common.eventbus.EventBus;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import springeventbus.config.EventBusAutoConfiguration;

/**
 * Created by lichuanjie on 2018/6/5.
 */
public class PublishTest {
    public void send(){

        System.out.println("ok");
    }


    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(EventBusAutoConfiguration.class);
        //获取bean
        EventBus tb = (EventBus) context.getBean("eventBus");
        tb.post("sdfe");
    }


}
