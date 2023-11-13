package com.example.ProductReviewsWebApp.models;

import jakarta.persistence.*;

/**
 * The Review class that contains all the information in a review
 */
@Entity
public class Review {
    @Id
    @GeneratedValue
    private long id;

    private int rating;

    private String comment;


    @ManyToOne(targetEntity = Product.class)
    private Product reviewedProduct;

    @ManyToOne
    private Client client;

    private boolean forTesting;

    /**
     * Default constructor
     */
    public Review () {}

    /**
     * Constructor for the Review class
     * @param rating the int of the rating for the product
     * @param comment the String of the review comment
     */
    public Review(int rating, String comment) {
        this.rating = rating;
        this.comment = comment;
    }

    public Review(Client client, Product product, int rating, String comment) {
        this.client = client;
        this.reviewedProduct = product;
        this.rating = rating;
        this.comment = comment;
    }

    /**
     * Gets the id of the review
     * @return the long id
     */
    public long getId() {
        return id;
    }

    /**
     * Gets the rating of the product
     * @return the int of the product rating
     */
    public int getRating() {
        return rating;
    }

    /**
     * Gets the comment of the product review
     * @return the String of the product review comment
     */
    public String getComment() {
        return comment;
    }

    /**
     * Sets the review id
     * @param id the long of the review id
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Sets the rating of the product
     * @param rating the int of the product rating
     */
    public void setRating(int rating) {
        this.rating = rating;
    }

    /**
     * Sets the product review comment
     * @param comment the String of the product review comment
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     * Set the review as a testing review only.
     */
    public void setForTesting() {
        forTesting = true;
    }

    /**
     * Check if review is for testing or not.
     * @return True if test review.
     */
    public boolean isForTesting() {
        return forTesting;
    }
}