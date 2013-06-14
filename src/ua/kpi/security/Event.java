package ua.kpi.security;

import java.util.Date;

public class Event {
    private Date date;
    private String event;
    private Integer aid;
    private Integer pix;
    private Integer contract;

    public Event(Date date, String event, Integer aid, Integer pix, Integer contract) {
        this.date = date;
        this.event = event;
        this.aid = aid;
        this.pix = pix;
        this.contract = contract;
    }

    public Date getDate() {
        return date;
    }

    public String getEvent() {
        return event;
    }

    public Integer getAid() {
        return aid;
    }

    public Integer getPix() {
        return pix;
    }

    public Integer getContract() {
        return contract;
    }
}
