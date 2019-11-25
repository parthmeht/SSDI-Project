package com.ssdi.project.bookExchange.model;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.util.Date;

@Entity
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="id")
    private int id;
    @Column(name = "title")
    private String title;
    @Column (name ="body")
    private String body;
    @ManyToOne (cascade = CascadeType.MERGE)
    @JoinColumn(name="sender_id", referencedColumnName="user_id")
    private User senderId;
    @ManyToOne (cascade = CascadeType.MERGE)
    @JoinColumn(name="receiver_id", referencedColumnName="user_id")
    private User receiverId;
    @CreatedDate
    @Column(name = "created_date")
    private Date createdDate;
}
