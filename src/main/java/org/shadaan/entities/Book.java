package org.shadaan.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Book {

    public Book(int id, String name, String author, int numberOfPages) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.numberOfPages = numberOfPages;
    }

    @Id
    private int id;

    private String  name;

    private String author;

    private int numberOfPages;
}
