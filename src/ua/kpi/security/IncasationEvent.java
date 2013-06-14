package ua.kpi.security;

public class IncasationEvent {
    private String date;
    private String cashier;
    private Integer value;
    private String eventType;

    public IncasationEvent(String date, String cashier, Integer value, String eventType) {
        this.date = date;
        this.cashier = cashier;
        this.value = value;
        this.eventType = eventType;
    }

    public String getDate() {
        return date;
    }

    public String getCashier() {
        return cashier;
    }

    public Integer getValue() {
        return value;
    }

    public String getEventType() {
        return eventType;
    }
}
