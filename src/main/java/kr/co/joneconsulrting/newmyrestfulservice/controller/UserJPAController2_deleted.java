package kr.co.joneconsulrting.newmyrestfulservice.controller;

import jakarta.validation.Valid;
import kr.co.joneconsulrting.newmyrestfulservice.bean.Post;
import kr.co.joneconsulrting.newmyrestfulservice.bean.User;
import kr.co.joneconsulrting.newmyrestfulservice.exception.UsersAndCountResponse;
import kr.co.joneconsulrting.newmyrestfulservice.exception.UserNotFoundException;
import kr.co.joneconsulrting.newmyrestfulservice.repository.PostRepository;
import kr.co.joneconsulrting.newmyrestfulservice.repository.UserRepository;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@RestController
@RequestMapping("/jpa2")
public class UserJPAController2 {
    private UserRepository userRepository;
    private PostRepository postRepository;

    public UserJPAController2(UserRepository userRepository, PostRepository postRepository){
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }

    // /jpa/users
    @GetMapping("/users")
    public List<User> retrieveAllUsers(){
        return userRepository.findAll();
    }

    // ResponseEntity를 사옹한 정답 코드
    @GetMapping("/usersAndCount")
    public UsersAndCountResponse retrieveAllUsersAndCount(){
        List<User> users = userRepository.findAll();
        int count = users.size();
//        System.out.println(new UsersAndCountResponse(count, users));

        return new UsersAndCountResponse(count, users);
//        List<User> users = userRepository.findAll();
//        int count = users.size();
//
//        UsersAndCountResponse result = UsersAndCountResponse.builder()
//                .count(users.isEmpty() ? 0 : users.size())
//                .users(users)
//                .build();
//
//        EntityModel entityModel = EntityModel.of(result);
//        WebMvcLinkBuilder linkTo = linkTo(methodOn(this.getClass()).retrieveAllUsers());
//        entityModel.add(linkTo.withSelfRel());
//
//        return ResponseEntity.ok(entityModel);
    }

    // jpa/users/{id}
    @GetMapping("/users/{id}")
    public ResponseEntity retrieveUsersById(@PathVariable int id){
        Optional<User> user = userRepository.findById(id);

        if (!user.isPresent()){
            throw new UserNotFoundException("id - " + id);
        }

        EntityModel entityModel = EntityModel.of(user.get());

        WebMvcLinkBuilder lintTo = linkTo(methodOn(this.getClass()).retrieveAllUsers());
        entityModel.add(lintTo.withRel("all-users"));

        return ResponseEntity.ok(entityModel);
    }


    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable int id){
        userRepository.deleteById(id);
    }

    // /jpa/users
    @PostMapping("/users")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user){
        if(user.getJoinDate() == null)
            user.setJoinDate(new Date());
        User savedUser = userRepository.save(user);
        // USER CREATED
        // /users/4

        // 생성된 User의 URI를 저장 후 반환하기
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @GetMapping("/users/{id}/posts")
    public List<Post> retrieveAllPostByUser(@PathVariable int id){
        Optional<User> user = userRepository.findById(id);
        if(!user.isPresent()){
            throw new UserNotFoundException("id-" + id + " user not found");
        }
        return user.get().getPosts();
    }

    @PostMapping("/users/{id}/posts")
    public ResponseEntity<Post> createPost(@PathVariable int id, @RequestBody Post post) {
        Optional<User> userOptional = userRepository.findById(id);
        if (!userOptional.isPresent()) {
            throw new UserNotFoundException("id-" + id);
        }

        User user = userOptional.get();

        post.setUser(user);

        postRepository.save(post);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(post.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }
}
