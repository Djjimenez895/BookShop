package com.pluralsight.bookstore.model;

import javax.persistence.Entity;
import java.util.Date;

@Entity
public class Book {

    private Long id;

    private String title;

    private String description;

    private Float unitCost;

    private String isbn;

    private Date publicationDate;

    private Integer nbOfPages;

    private String imageUrl;
}
