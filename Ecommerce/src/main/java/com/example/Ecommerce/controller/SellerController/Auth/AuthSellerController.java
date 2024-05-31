package com.example.Ecommerce.controller.SellerController.Auth;

import com.example.Ecommerce.common.responseStatus.ResponseData;
import com.example.Ecommerce.dto.SellerDto.Mapper.SellerMapper;
import com.example.Ecommerce.dto.SellerDto.Response.InfoSeller.InfoSellerDTO;
import com.example.Ecommerce.model.User;
import com.example.Ecommerce.repository.UserRepository;
import com.example.Ecommerce.service.Auth.JWT.AuthenticationService;
import com.example.Ecommerce.service.User.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/sellers")
@RequiredArgsConstructor
@Tag(name = "Seller Auth Controller")
public class AuthSellerController {

    @Autowired
    private final AuthenticationService authenticationService;

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Operation(summary = "Get info Seller")
    @GetMapping("/info")
    public ResponseEntity<?> getUserInfo(Principal principal) {
        try {
            User user = authenticationService.getUserByUsername(principal.getName());
            InfoSellerDTO infoSellerDTO = SellerMapper.infoSellerDTO(user);

            return ResponseEntity.ok(new ResponseData<>(200, "Retrieve seller information successfully", infoSellerDTO));
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseData<>(404, "Seller information does not exist", null));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = userService.findUserById(id);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }
}
