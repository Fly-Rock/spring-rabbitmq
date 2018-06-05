import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.EventBus;
import databus.DeadEventListener;
import databus.EventStringListener;
import databus.OurTestEvent;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import springeventbus.EventBusConfig;
import springeventbus.EventBusPublisher;

import java.util.concurrent.Executors;


/**
 * Created by lichuanjie on 2018/6/5.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class EventBusDataTest {

    @Configuration
    static class EventBusConfigV {
        //定义事件总线bean
        @Bean
        public EventBus eventBus() {
            return new EventBus();
        }

        @Bean
        public AsyncEventBus asyncEventBus() {
            return new AsyncEventBus(Executors.newCachedThreadPool());
        }
    }

    @Autowired
    private EventBus eventBus;


    @Test
    public void should_create_event_bus_instance() throws Exception {
        // given
        EventBus eventBus = new EventBus("test");
        EventStringListener listener = new EventStringListener();

        eventBus.register(listener);
        String content = "My Event Bus";
        // when
        eventBus.post(content);

    }


    @Test
    public void shouldDetectEventWithoutListeners() throws Exception {

        // given
        EventBus eventBus = new EventBus("test");

        DeadEventListener deadEventListener = new DeadEventListener();
        eventBus.register(deadEventListener);
        eventBus.unregister(deadEventListener);

        // when
        eventBus.post(new OurTestEvent(200));
    }


    @Test
    public void springEventBusPublish() throws Exception {
        /*final AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(EventBusConfig.class);
        final EventBusPublisher publisher = context.getBean(EventBusPublisher.class);
        // when
        publisher.publishNewUUID();
        context.close();*/


        eventBus.post("test");
    }


}
