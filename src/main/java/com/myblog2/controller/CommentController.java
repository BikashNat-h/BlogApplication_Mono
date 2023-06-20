package com.myblog2.controller;

import com.myblog2.payload.CommentDto;
import com.myblog2.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/postss")
public class CommentController {

    private CommentService commentService;

    private ModelMapper modelMapper;
    public CommentController(CommentService commentService, ModelMapper modelMapper) {
        this.commentService = commentService;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/{postId}/comment")
    public ResponseEntity<Object> createComment(@Valid @PathVariable(value = "postId") long postId,
                                                    @Valid @RequestBody CommentDto commentDto){

        CommentDto dto = commentService.createComment(postId, commentDto);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @GetMapping("/{postId}/comment/{commentId}")
    public ResponseEntity<CommentDto> getCommentById(@PathVariable(value = "postId") long postId,
                                                     @PathVariable(value = "commentId") long commentId){
        CommentDto dto = commentService.getCommentById(postId, commentId);
        return new ResponseEntity<>(dto,HttpStatus.OK);
    }

    @PutMapping("/{postId}/comment/{commentId}")
    public ResponseEntity<CommentDto> getAllComments(@PathVariable(value = "postId") long postId,
                                           @PathVariable(value = "commentId") long commentId,
                                           @RequestBody CommentDto commentDto){
        CommentDto dto = commentService.updateComment(postId, commentId, commentDto);
        return new ResponseEntity<>(dto,HttpStatus.OK);
    }

    @DeleteMapping("/{postId}/comment/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable(value = "postId") long postId,
                                                @PathVariable(value = "commentId") long commentId){
        CommentDto dto = commentService.deleteComment(postId, commentId);
        return ResponseEntity.ok("comment is deleted ...");
    }
}
