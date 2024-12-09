package com.self.web;

import com.self.service.posts.PostsService;
import com.self.web.dto.PostsResponseDto;
import com.self.web.dto.PostsSaveRequestDto;
import com.self.web.dto.PostsUpdateRequestDto;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class PostsApiController {

    private final PostsService postsService;

    @GetMapping("/posts/{id}")
    public PostsResponseDto findById(@PathVariable Long id){
        return postsService.findById(id);
    }

    @PostMapping("/posts")
    public Long save(@RequestBody PostsSaveRequestDto postsSaveRequestDto) {
        return postsService.save(postsSaveRequestDto);
    }

    @PutMapping("/posts/{id}")
    public Long update(
            @PathVariable Long id ,
            @RequestBody PostsUpdateRequestDto postsUpdateRequestDto
    ){
        return postsService.update(id, postsUpdateRequestDto);
    }

    @DeleteMapping("/posts/{id}")
    public Long delete(@PathVariable Long id) {
        postsService.delete(id);
        return id;
    }
}
