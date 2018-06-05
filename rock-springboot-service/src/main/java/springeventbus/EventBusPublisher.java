package springeventbus;

import com.google.common.eventbus.AsyncEventBus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import java.util.UUID;

/**
 * Created by lichuanjie on 2018/6/5.
 */
@Component
public class EventBusPublisher {
    private final AsyncEventBus eventBus;

    @Autowired
    public EventBusPublisher(final AsyncEventBus eventBus) {
        this.eventBus = eventBus;
    }

    public void publishNewUUID() {
        final UUID uuid = UUID.randomUUID();
        eventBus.post(uuid);
    }

}
