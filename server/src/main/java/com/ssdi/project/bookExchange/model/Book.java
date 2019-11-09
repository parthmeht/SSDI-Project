package com.ssdi.project.bookExchange.model;

import lombok.*;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="id")
    private int id;
    @Column(name = "title")
    private String title;
    @Column (name ="author")
    private String author;
    @Column (name ="price")
    private Double price;
    @Column (name="is_listed")
    private Boolean isListed;
    @ManyToOne (cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;
}
