package com.man.ger.manager.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;


@JsonIgnoreProperties(ignoreUnknown = true)
public class CurrencyDto {

    @NotNull
    private Integer date;
    @NotNull
    private Double rateBuy;
    @NotNull
    private Double rateSell;
    @NotNull
    private Integer currencyCodeA;

    private Integer currencyCodeB;

    @Pattern(regexp ="^[0-9]+$", message = "not valid test")
    private String test;



    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public double getRateBuy() {
        return rateBuy;
    }

    public void setRateBuy(double rateBuy) {
        this.rateBuy = rateBuy;
    }

    public double getRateSell() {
        return rateSell;
    }

    public void setRateSell(double rateSell) {
        this.rateSell = rateSell;
    }

    public int getCurrencyCodeA() {
        return currencyCodeA;
    }

    public void setCurrencyCodeA(int currencyCodeA) {
        this.currencyCodeA = currencyCodeA;
    }

    public int getCurrencyCodeB() {
        return currencyCodeB;
    }

    public void setCurrencyCodeB(int currencyCodeB) {
        this.currencyCodeB = currencyCodeB;
    }

    public String getTest() {
        return test;
    }

    public void setTest(String test) {
        this.test = test;
    }
}
