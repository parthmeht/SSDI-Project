package com.ssdi.project.bookExchange.Vo;

import lombok.*;

@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MessageVo {

    private int id;
    private String title;
    private String body;
    private int senderId;
    private String senderName;
    private int receiverId;
    private String createdDate;

}
