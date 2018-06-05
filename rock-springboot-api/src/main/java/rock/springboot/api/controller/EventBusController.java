package rock.springboot.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import springeventbus.publish.EventBusSupportSender;

/**
 * Created by lichuanjie on 2018/6/5.
 */
@RestController
@RequestMapping(value = {"/event/v1.1/"})
public class EventBusController {
    @Autowired
    private EventBusSupportSender eventBusSupportSender;

    @RequestMapping(value = "publish", method = RequestMethod.GET)
    public String publish() {
        eventBusSupportSender.sendEvents();
        return "ok";
    }

}
