package ua.kpi.security;

public class IncasationEvent {
    private String date;
    private String cashier;
    private Integer value;

    public IncasationEvent(String date, String cashier, Integer value) {
        this.date = date;
        this.cashier = cashier;
        this.value = value;
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

}
