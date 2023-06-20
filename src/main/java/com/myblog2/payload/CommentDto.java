package com.myblog2.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {
    private long commentId;
    private String name;

    @Email(message = "Email format not valid")
    private String email;
    private String body;
}
