package kr.co.joneconsulrting.newmyrestfulservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import kr.co.joneconsulrting.newmyrestfulservice.bean.User;
import kr.co.joneconsulrting.newmyrestfulservice.dao.UserDaoService;
import kr.co.joneconsulrting.newmyrestfulservice.exception.UserNotFoundException;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@Tag(name = "user-controller", description = "일반 사용자 서비스를 위한 컨트롤러")
public class UserController {
    private UserDaoService service;

    public UserController(UserDaoService service) {
        this.service = service;
    }

    @GetMapping("/users")
    public List<User> retrieveAllUsers() {
        return service.findAll();
    }

//    @GetMapping("/users/{id}")
//    public User retrieveUser(@PathVariable int id) {
////        return service.findOne(id);
//        User user = service.findOne(id);
//
//        if (user == null) {
//            throw new UserNotFoundException(String.format("ID[%s] not found", id));
//        }
//
//        return user;
//    }

    @Operation(summary = "사용자 정보 조회 API", description = "사용자 ID를 이용하여 사용자 상세 정보 조회를 합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK !!",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = User.class)))),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST !!"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND !!"),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR !!"),
    })
    @GetMapping("/users/{id}")
    public EntityModel<User> retrieveUser(
            @Parameter(description = "사용자 ID", required = true, example = "1") @PathVariable int id) {
        User user = service.findOne(id);

        if (user == null) {
            throw new UserNotFoundException("id-" + id);
        }

        EntityModel entityModel = EntityModel.of(user);

        WebMvcLinkBuilder linkTo = linkTo(methodOn(this.getClass()).retrieveAllUsers());
        entityModel.add(linkTo.withRel("all-users")); // http://127.0.0.1:8080/users

        try {
            return entityModel;
        } catch (Exception ex) {
            throw new RuntimeException();
        }
    }

//    @PostMapping("/users")
//    public void createUser(@RequestBody User user) {
//        User savedUser = service.save(user);
//    }
    @PostMapping("/users")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
        User savedUser = service.save(user);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable int id) {
        User user = service.deleteById(id);

        if (user == null) {
            throw new UserNotFoundException(String.format("ID[%s] not found", id));
        }
    }
}
