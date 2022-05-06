package com.nnk.springboot.domain;

import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * Representation of the structure of a {@link Rating}.
 */
@Entity
@Table(name = "rating")
public class Rating {
    // NOTE: Map columns in data table RATING with corresponding java fields

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Moodys Rating is mandatory")
    @Size(min = 3, max = 50, message = "Moodys Rating must be between 3 and 50 characters long")
    private String moodysRating;

    @NotBlank(message = "Sand PRating is mandatory")
    @Size(min = 3, max = 50, message = "Sand PRating must be between 3 and 50 characters long")
    private String sandPRating;

    @NotBlank(message = "Fitch Rating is mandatory")
    @Size(min = 3, max = 50, message = "Fitch Rating must be between 3 and 50 characters long")
    private String fitchRating;

    @NotNull(message = "Order is mandatory")
    @Digits(integer = 9, fraction = 0, message = "Order must be a integer")
    @Positive(message = "Order must be greater than 0")
    private Integer orderNumber;

    public Rating() {

    }

    public Rating(String moodysRating, String sandPRating, String fitchRating, Integer orderNumber) {

        this.moodysRating = moodysRating;
        this.sandPRating  = sandPRating;
        this.fitchRating  = fitchRating;
        this.orderNumber  = orderNumber;
    }

    public Integer getId() {

        return id;
    }

    public void setId(Integer id) {

        this.id = id;
    }

    public Integer getOrderNumber() {

        return orderNumber;
    }

    public void setOrderNumber(Integer orderNumber) {

        this.orderNumber = orderNumber;
    }

    public String getMoodysRating() {

        return moodysRating;
    }

    public void setMoodysRating(String moodysRating) {

        this.moodysRating = moodysRating;
    }

    public String getSandPRating() {

        return sandPRating;
    }

    public void setSandPRating(String sandPRating) {

        this.sandPRating = sandPRating;
    }

    public String getFitchRating() {

        return fitchRating;
    }

    public void setFitchRating(String fitchRating) {

        this.fitchRating = fitchRating;
    }
}
