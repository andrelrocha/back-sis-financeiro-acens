package jr.acens.api.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jr.acens.api.domain.user.DTO.UserDTO;
import jr.acens.api.domain.user.DTO.UserLoginDTO;
import jr.acens.api.domain.user.DTO.UserResetPassDTO;
import jr.acens.api.domain.user.DTO.UserReturnLoginDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import jr.acens.api.infra.security.TokenJwtDto;
import jr.acens.api.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    @Transactional
    public ResponseEntity performLogin(@RequestBody @Valid UserLoginDTO data) {
        TokenJwtDto tokenJwt = userService.performLogin(data);
        return ResponseEntity.ok(tokenJwt);
    }

    @PostMapping("/forgot_password")
    @Transactional
    public ResponseEntity forgotPassword(@RequestBody UserReturnLoginDTO data) {
        var stringSuccess= userService.forgotPassword(data);
        return ResponseEntity.ok(stringSuccess);
    }

    @PostMapping("/reset_password")
    @Transactional
    public ResponseEntity resetPassword(@RequestBody UserResetPassDTO data) {
        var stringSuccess= userService.resetPassword(data);
        return ResponseEntity.ok(stringSuccess);
    }



    @PostMapping("/create")
    @Transactional
    public ResponseEntity createUser(@RequestBody @Valid UserDTO data, UriComponentsBuilder uriBuilder) {
        var newUser = userService.createUser(data);
        var uri = uriBuilder.path("/users/{id}").buildAndExpand(newUser.id()).toUri();
        return ResponseEntity.created(uri).body(newUser);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

}
