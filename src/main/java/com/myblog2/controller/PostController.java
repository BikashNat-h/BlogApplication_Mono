package com.myblog2.controller;

import com.myblog2.payload.PostDto;
import com.myblog2.payload.PostResponse;
import com.myblog2.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/postss")
public class PostController {

    private PostService postService;

    public PostController(PostService postService) {

        this.postService = postService;
    }


    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Object> createPost(@Valid @RequestBody PostDto postDto,
                                              BindingResult result){

        if (result.hasErrors()){
            return new ResponseEntity<>(result.getFieldError().getDefaultMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
        PostDto dto = postService.createPost(postDto);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }


    @PutMapping("/{postId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Object> updatePost(@PathVariable(value = "postId") long postId,
                                              @RequestBody PostDto postDto,
                                              BindingResult result){
        if (result.hasErrors()){
            return new ResponseEntity<>(result.getFieldError().getDefaultMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
        PostDto dto = postService.updatePost(postId, postDto);
        return new ResponseEntity<>(dto,HttpStatus.OK);
    }

    @DeleteMapping("/{postId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Object> deletePost(@PathVariable(value = "postId") long postId,
                                             BindingResult result){

        if (result.hasErrors()){
            return new ResponseEntity<>(result.getFieldError().getDefaultMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
        PostDto dto = postService.deletePost(postId);
        return new ResponseEntity<>("Post is deleted !!!",HttpStatus.OK);
    }


    @GetMapping("/{postId}")
    public ResponseEntity<PostDto> getPostById(@PathVariable(value = "postId") long postId){
        PostDto dto = postService.getPostById(postId);
        return new ResponseEntity<>(dto,HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<PostResponse> getAllPosts(
            @RequestParam
                    (value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam
                    (value = "pageSize", defaultValue = "2", required = false) int pageSize,
            @RequestParam
                    (value = "sortBy",defaultValue = "postId",required = false) String sortBy,
            @RequestParam
                    (value = "sortDir", defaultValue = "asc", required = false) String sortDir
    ){
        PostResponse allPosts = postService.getAllPosts(pageNo, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(allPosts,HttpStatus.OK);
    }
}
