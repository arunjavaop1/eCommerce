package com.ecommerce.payload;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmailDetails {
    private String recipient;
    private String subject;
    private String msgBody;
    private String attachment;

    @Override
    public String toString() {
        return "EmailDetails{" +
                "recipient='" + recipient + '\'' +
                ", subject='" + subject + '\'' +
                ", msgBody='" + msgBody + '\'' +
                '}';
    }
}
