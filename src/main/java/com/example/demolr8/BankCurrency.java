package com.example.demolr8;

import java.io.Serial;
import java.io.Serializable;
import java.sql.Date;

public class BankCurrency implements Serializable {

    @Serial
    private final static long serialVersionUID = 4976987307641598477L;

    private long r030;
    private String txt;
    private double rate;
    private String cc;
    private String exchangeDate;

    public BankCurrency(long r030, String txt, double rate, String cc, String exchangeDate) {
        this.setR030(r030);
        this.setTxt(txt);
        this.setRate(rate);
        this.setCc(cc);
        this.setExchangeDate(exchangeDate);
    }

    public BankCurrency(int r030, String login, double password, String name, String regdate, String city) {

    }

    public long getR030() {
        return r030;
    }

    public void setR030(long r030) {
        this.r030 = r030;
    }

    public String getTxt() {
        return txt;
    }

    public void setTxt(String txt) {
        this.txt = txt;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public String getCc() {
        return cc;
    }

    public void setCc(String cc) {
        this.cc = cc;
    }

    public String getExchangeDate() {
        return exchangeDate;
    }

    public void setExchangeDate(String exchangeDate) {
        this.exchangeDate = exchangeDate;
    }


}

