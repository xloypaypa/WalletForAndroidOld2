package model.data.node;

import java.util.Date;

/**
 * Created by xlo on 2015/12/30.
 * it's the detail node
 */
public class DetailNode {

    private String event, id;
    private Date date;

    public DetailNode(String event, String id, Date date) {
        this.event = event;
        this.id = id;
        this.date = date;
    }

    public String getEvent() {
        return event;
    }

    public String getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }
}
