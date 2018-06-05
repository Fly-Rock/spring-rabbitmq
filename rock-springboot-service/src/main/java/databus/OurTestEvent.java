package databus;

/**
 * Created by lichuanjie on 2018/6/5.
 */
public class OurTestEvent {
    private final int message;

    public OurTestEvent(int message) {
        this.message = message;
    }

    public int getMessage() {
        return message;
    }

}
