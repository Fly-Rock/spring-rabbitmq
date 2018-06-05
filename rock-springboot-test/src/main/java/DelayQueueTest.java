import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import rock.springboot.rabbitmq.delayqueue.ExpirationMessagePostProcessor;
import rock.springboot.rabbitmq.delayqueue.ProcessReceiver;
import rock.springboot.rabbitmq.delayqueue.QueueConfig;

import java.util.concurrent.CountDownLatch;

/**
 * Created by lichuanjie on 2018/6/1.
 */
@Component
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {BaseTestConfig.class, ActiveEnvironment.DEV.class})
public class DelayQueueTest {


    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Test
    public void testDelayQueuePerMessageTTL() throws InterruptedException {
        /*ProcessReceiver.latch = new CountDownLatch(3);

        ProcessReceiver.latch.await();
*/
        for (int i = 1; i <= 3; i++) {
            long expiration = i * 1000*60;
            rabbitTemplate.convertAndSend(QueueConfig.DELAY_QUEUE_PER_MESSAGE_TTL_NAME,
                    (Object) ("Message From delay_queue_per_message_ttl with expiration " + expiration), new ExpirationMessagePostProcessor(expiration));
        }

    }

    @Test
    public void testDelayQueuePerQueueTTL() throws InterruptedException {
        ProcessReceiver.latch = new CountDownLatch(3);
        for (int i = 1; i <= 3; i++) {
            rabbitTemplate.convertAndSend(QueueConfig.DELAY_QUEUE_PER_QUEUE_TTL_NAME,
                    "Message From delay_queue_per_queue_ttl with expiration " + QueueConfig.QUEUE_EXPIRATION);
        }
        ProcessReceiver.latch.await();
    }

    @Test
    public void testFailMessage() throws InterruptedException {
        ProcessReceiver.latch = new CountDownLatch(6);
        for (int i = 1; i <= 3; i++) {
            rabbitTemplate.convertAndSend(QueueConfig.DELAY_PROCESS_QUEUE_NAME, ProcessReceiver.FAIL_MESSAGE);
        }
        ProcessReceiver.latch.await();
    }


}
