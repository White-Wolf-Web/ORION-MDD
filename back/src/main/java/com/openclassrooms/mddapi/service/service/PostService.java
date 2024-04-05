package com.openclassrooms.mddapi.service.service;

import com.openclassrooms.mddapi.dto.PostDto;

import java.util.List;

public interface PostService {
    List<PostDto> findAllPosts();
    PostDto createPost(PostDto postDto);
    PostDto findPostById(Long id);

}
