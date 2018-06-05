/**
 * Created by lichuanjie on 2018/6/5.
 */

import com.google.common.eventbus.EventBus;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import springeventbus.config.EventBusSupport;
import springeventbus.publish.EventBusSupportSender;

/**
 * Created by lichuanjie on 2018/6/5.
 */
@Component
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {BaseTestConfig.class, ActiveEnvironment.DEV.class})
public class EventBusSpringTest {
    @Autowired
    private EventBusSupportSender eventBusSupportSender;

    @Autowired
    EventBusSupport eventBusSupport;
    @Test
    public void springEventBusPublish() throws Exception {
        eventBusSupportSender.sendEvents();

        eventBusSupport.post("jerry");
    }

}
