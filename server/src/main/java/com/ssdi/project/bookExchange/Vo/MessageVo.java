package com.ssdi.project.bookExchange.Vo;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.springframework.data.annotation.CreatedDate;

import com.ssdi.project.bookExchange.model.User;

public class MessageVo {

    private int id;

    private String title;

    private String body;

    private int senderId;

    private String senderName;

    private int receiverId;

    private Date createdDate;

    public MessageVo() {
        super();
    }

    public MessageVo(int id, String title, String body, int senderId, String senderName, int receiverId,
                     Date createdDate) {
        super();
        this.id = id;
        this.title = title;
        this.body = body;
        this.senderId = senderId;
        this.senderName = senderName;
        this.receiverId = receiverId;
        this.createdDate = createdDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public int getSenderId() {
        return senderId;
    }

    public void setSenderId(int senderId) {
        this.senderId = senderId;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public int getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(int receiverId) {
        this.receiverId = receiverId;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    @Override
    public String toString() {
        return "MessageVo [id=" + id + ", title=" + title + ", body=" + body + ", senderId=" + senderId
                + ", senderName=" + senderName + ", receiverId=" + receiverId + ", createdDate=" + createdDate
                + "]";
    }


}
