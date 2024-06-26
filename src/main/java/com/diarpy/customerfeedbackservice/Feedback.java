package com.diarpy.customerfeedbackservice;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * @author Mack_TB
 * @since 10/05/2024
 * @version 1.0.4
 */

@Document(collection = "feedback")
public class Feedback {
    @Id
    private String id;
    private Integer rating;
    @Field(write = Field.Write.ALWAYS)
    private String feedback; // Optional
    @Field(write = Field.Write.ALWAYS)
    private String customer; // Optional
    private String product;
    private String vendor;

    public Feedback() {
    }

    public Feedback(String id, Integer rating, String feedback, String customer, String product, String vendor) {
        this.id = id;
        this.rating = rating;
        this.feedback = feedback;
        this.customer = customer;
        this.product = product;
        this.vendor = vendor;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    @Override
    public String toString() {
        return "Feedback{" +
                "rating=" + rating +
                ", customer='" + customer + '\'' +
                ", product='" + product + '\'' +
                ", vendor='" + vendor + '\'' +
                '}';
    }
}