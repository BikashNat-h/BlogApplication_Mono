package com.myblog2.service;

import com.myblog2.entity.Comment;
import com.myblog2.entity.Post;
import com.myblog2.exception.BlogApiException;
import com.myblog2.exception.ResourceNotFoundException;
import com.myblog2.payload.CommentDto;
import com.myblog2.repository.CommentRepository;
import com.myblog2.repository.PostRepository;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService{

    private ModelMapper modelMapper;

    private PostRepository postRepository;

    private CommentRepository commentRepository;

    public CommentServiceImpl(ModelMapper modelMapper, PostRepository postRepository, CommentRepository commentRepository) {
        this.modelMapper = modelMapper;
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
    }


    @Override
    public CommentDto createComment(long postId, CommentDto commentDto) {
        Comment comment = mapToEntity(commentDto);
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("post", "id", postId)
        );
        comment.setPost(post);
        Comment newComment = commentRepository.save(comment);
        CommentDto dto = mapToDto(newComment);
        return dto;
    }

    @Override
    public CommentDto getCommentById(long postId, long commentId) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("post", "id", postId)
        );

        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new ResourceNotFoundException("comment", "id", commentId)
        );

        if(comment.getPost().getPostId() != post.getPostId()){
            throw new BlogApiException(HttpStatus.BAD_REQUEST,"Comment is not exists for this post");
        }

        return mapToDto(comment);
    }

    @Override
    public CommentDto updateComment(long postId, long commentId, CommentDto commentDto) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("post", "id", postId)
        );

        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new ResourceNotFoundException("comment", "id", commentId)
        );

        if(comment.getPost().getPostId() != post.getPostId()){
            throw new BlogApiException(HttpStatus.BAD_REQUEST,"Comment is not exists for this post");
        }

        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());
        comment.setBody(commentDto.getBody());

        commentRepository.save(comment);
        return mapToDto(comment);
    }

    @Override
    public CommentDto deleteComment(long postId, long commentId) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("post", "id", postId)
        );

        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new ResourceNotFoundException("comment", "id", commentId)
        );

        if(comment.getPost().getPostId() != post.getPostId()){
            throw new BlogApiException(HttpStatus.BAD_REQUEST,"Comment is not exists for this post");
        }

        commentRepository.delete(comment);
        return null;
    }

    CommentDto mapToDto(Comment comment) {
        CommentDto commentDto = modelMapper.map(comment, CommentDto.class);
        return commentDto;
    }

    Comment mapToEntity(CommentDto commentDto) {
        Comment comment = modelMapper.map(commentDto, Comment.class);
        return comment;
    }
}
