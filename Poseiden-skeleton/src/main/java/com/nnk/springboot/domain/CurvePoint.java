package com.nnk.springboot.domain;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.sql.Timestamp;


@Entity
@Table(name = "curvepoint")
public class CurvePoint {
    // DONE: Map columns in data table CURVEPOINT with corresponding java fields

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "CurvePointId is mandatory")
    @Digits(integer = 9, fraction = 0, message = "CurvePointId is a integer")
    @Positive(message = "CurvePointId must be greater than 0")
    private Integer curveId;

    private Timestamp asOfDate;

    @NotNull(message = "Term is mandatory")
    @Digits(integer = 9, fraction = 2, message = "Term is a decimal with 9 integers max. and 2 digits after the decimal point")
    @Positive(message = "Term must be greater than 0")
    private Double term;

    @NotNull(message = "Value is mandatory")
    @Digits(integer = 9, fraction = 2, message = "Value is a decimal with 9 integers max. and 2 digits after the decimal point")
    @Positive(message = "Value must be greater than 0")
    private Double value;

    private Timestamp creationDate = new Timestamp(System.currentTimeMillis());

    public CurvePoint() {

    }

    public CurvePoint(Integer curveId, Double term, Double value) {

        this.curveId = curveId;
        this.term    = term;
        this.value   = value;
    }

    public Integer getId() {

        return id;
    }

    public void setId(Integer id) {

        this.id = id;
    }

    public Integer getCurveId() {

        return curveId;
    }

    public void setCurveId(Integer curveId) {

        this.curveId = curveId;
    }

    public Timestamp getAsOfDate() {

        return asOfDate;
    }

    public void setAsOfDate(Timestamp asOfDate) {

        this.asOfDate = asOfDate;
    }

    public Double getTerm() {

        return term;
    }

    public void setTerm(Double term) {

        this.term = term;
    }

    public Double getValue() {

        return value;
    }

    public void setValue(Double value) {

        this.value = value;
    }

    public Timestamp getCreationDate() {

        return creationDate;
    }

    public void setCreationDate(Timestamp creationDate) {

        this.creationDate = creationDate;
    }
}
