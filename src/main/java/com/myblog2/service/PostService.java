package com.myblog2.service;

import com.myblog2.payload.PostDto;
import com.myblog2.payload.PostResponse;

public interface PostService {
    PostDto createPost(PostDto postDto);

    PostDto getPostById(long postId);

    PostDto updatePost(long postId, PostDto postDto);

    PostDto deletePost(long postId);

    PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir);
}
