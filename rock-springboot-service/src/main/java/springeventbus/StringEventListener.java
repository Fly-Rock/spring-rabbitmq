package springeventbus;

import com.google.common.eventbus.Subscribe;
import org.springframework.stereotype.Component;

/**
 * Created by lichuanjie on 2018/6/5.
 */
@Component
public class StringEventListener {
    // 当所注册到的事件总线上发生String消息时，会在控制台上输出相应的消息
    @Subscribe
    public void listener(String event) {
        System.out.println("receive msg:" + event);
    }
}
