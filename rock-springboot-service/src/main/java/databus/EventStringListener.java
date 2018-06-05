package databus;

import com.google.common.eventbus.Subscribe;

/**
 * Created by lichuanjie on 2018/6/5.
 */
public class EventStringListener {

    private String lastMessage;

    @Subscribe
    public void listenString(String content) {
        this.lastMessage = content;
        System.out.println("jerry:" + content);
    }

    public String getLastMessage() {
        return lastMessage;
    }


}
