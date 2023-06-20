package com.myblog2.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostDto {
    private long postId;

    @NotEmpty
    @Size(min = 2, message = "post title should have at least 2 characters")
    private String title;

    @NotEmpty
    @Size(min = 2, message = "post description should have at least 2 characters")
    private String description;

    @NotEmpty
    @Size(min = 2, message = "post content should have at least 2 characters")
    private String content;
}
