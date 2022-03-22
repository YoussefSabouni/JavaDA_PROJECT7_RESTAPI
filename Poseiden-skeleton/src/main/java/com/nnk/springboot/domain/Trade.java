package com.nnk.springboot.domain;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.sql.Timestamp;


@Entity
@Table(name = "trade")
public class Trade {
    // DONE: Map columns in data table TRADE with corresponding java fields

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer tradeId;

    @NotBlank(message = "Account is mandatory")
    @Size(min = 3, max = 50, message = "Account must be between 3 and 50 characters long")
    private String account;

    @NotBlank(message = "Type is mandatory")
    @Size(min = 3, max = 50, message = "Type must be between 3 and 50 characters long")
    private String type;

    @NotNull(message = "BuyQuantity is mandatory")
    @Digits(integer = 9, fraction = 2, message = "BuyQuantity is a decimal with 9 integers max. and 2 digits after the decimal point")
    @Positive(message = "BuyQuantity must be greater than 0")
    private Double buyQuantity;

    private Double    sellQuantity;
    private Double    buyPrice;
    private Double    sellPrice;
    private String    benchmark;
    private Timestamp tradeDate;
    private String    security;
    private String    status;
    private String    trader;
    private String    book;
    private String    creationName;
    private Timestamp creationDate = new Timestamp(System.currentTimeMillis());
    private String    revisionName;
    private Timestamp revisionDate = new Timestamp(System.currentTimeMillis());
    private String    dealName;
    private String    dealType;
    private String    sourceListId;
    private String    side;

    public Trade() {

    }

    public Trade(String account, String type) {

        this.account = account;
        this.type    = type;
    }

    public Integer getTradeId() {

        return tradeId;
    }

    public void setTradeId(Integer tradeId) {

        this.tradeId = tradeId;
    }

    public String getAccount() {

        return account;
    }

    public void setAccount(String account) {

        this.account = account;
    }

    public String getType() {

        return type;
    }

    public void setType(String type) {

        this.type = type;
    }

    public Double getBuyQuantity() {

        return buyQuantity;
    }

    public void setBuyQuantity(Double buyQuantity) {

        this.buyQuantity = buyQuantity;
    }

    public Double getSellQuantity() {

        return sellQuantity;
    }

    public void setSellQuantity(Double sellQuantity) {

        this.sellQuantity = sellQuantity;
    }

    public Double getBuyPrice() {

        return buyPrice;
    }

    public void setBuyPrice(Double buyPrice) {

        this.buyPrice = buyPrice;
    }

    public Double getSellPrice() {

        return sellPrice;
    }

    public void setSellPrice(Double sellPrice) {

        this.sellPrice = sellPrice;
    }

    public String getBenchmark() {

        return benchmark;
    }

    public void setBenchmark(String benchmark) {

        this.benchmark = benchmark;
    }

    public Timestamp getTradeDate() {

        return tradeDate;
    }

    public void setTradeDate(Timestamp tradeDate) {

        this.tradeDate = tradeDate;
    }

    public String getSecurity() {

        return security;
    }

    public void setSecurity(String security) {

        this.security = security;
    }

    public String getStatus() {

        return status;
    }

    public void setStatus(String status) {

        this.status = status;
    }

    public String getTrader() {

        return trader;
    }

    public void setTrader(String trader) {

        this.trader = trader;
    }

    public String getBook() {

        return book;
    }

    public void setBook(String book) {

        this.book = book;
    }

    public String getCreationName() {

        return creationName;
    }

    public void setCreationName(String creationName) {

        this.creationName = creationName;
    }

    public Timestamp getCreationDate() {

        return creationDate;
    }

    public void setCreationDate(Timestamp creationDate) {

        this.creationDate = creationDate;
    }

    public String getRevisionName() {

        return revisionName;
    }

    public void setRevisionName(String revisionName) {

        this.revisionName = revisionName;
    }

    public Timestamp getRevisionDate() {

        return revisionDate;
    }

    public void setRevisionDate(Timestamp revisionDate) {

        this.revisionDate = revisionDate;
    }

    public String getDealName() {

        return dealName;
    }

    public void setDealName(String dealName) {

        this.dealName = dealName;
    }

    public String getDealType() {

        return dealType;
    }

    public void setDealType(String dealType) {

        this.dealType = dealType;
    }

    public String getSourceListId() {

        return sourceListId;
    }

    public void setSourceListId(String sourceListId) {

        this.sourceListId = sourceListId;
    }

    public String getSide() {

        return side;
    }

    public void setSide(String side) {

        this.side = side;
    }
}
