package com.nnk.springboot.domain;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.sql.Timestamp;

/**
 * Representation of the structure of a {@link BidList}.
 */
@Entity
@Table(name = "bidlist")
public class BidList {
    // DONE: Map columns in data table BIDLIST with corresponding java fields

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer bidListId;

    @NotBlank(message = "Account is mandatory")
    @Size(min = 3, max = 50, message = "Account must be between 3 and 50 characters long")
    private String account;

    @NotBlank(message = "Type is mandatory")
    @Size(min = 3, max = 50, message = "Type must be between 3 and 50 characters long")
    private String type;

    @NotNull(message = "BidQuantity is mandatory")
    @Digits(integer = 9, fraction = 2, message = "BidQuantity is a decimal with 9 integers max. and 2 digits after the decimal point")
    @Positive(message = "BidQuantity must be greater than 0")
    private Double    bidQuantity;
    private Double    askQuantity;
    private Double    bid;
    private Double    ask;
    private String    benchmark;
    private Timestamp bidListDate;
    private String    commentary;
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

    public BidList() {

    }

    public BidList(String account, String type, Double bidQuantity) {

        this.account     = account;
        this.type        = type;
        this.bidQuantity = bidQuantity;
    }

    public Integer getBidListId() {

        return bidListId;
    }

    public void setBidListId(Integer bidListId) {

        this.bidListId = bidListId;
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

    public Double getBidQuantity() {

        return bidQuantity;
    }

    public void setBidQuantity(Double bidQuantity) {

        this.bidQuantity = bidQuantity;
    }

    public Double getAskQuantity() {

        return askQuantity;
    }

    public void setAskQuantity(Double askQuantity) {

        this.askQuantity = askQuantity;
    }

    public Double getBid() {

        return bid;
    }

    public void setBid(Double bid) {

        this.bid = bid;
    }

    public Double getAsk() {

        return ask;
    }

    public void setAsk(Double ask) {

        this.ask = ask;
    }

    public String getBenchmark() {

        return benchmark;
    }

    public void setBenchmark(String benchmark) {

        this.benchmark = benchmark;
    }

    public Timestamp getBidListDate() {

        return bidListDate;
    }

    public void setBidListDate(Timestamp bidListDate) {

        this.bidListDate = bidListDate;
    }

    public String getCommentary() {

        return commentary;
    }

    public void setCommentary(String commentary) {

        this.commentary = commentary;
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
