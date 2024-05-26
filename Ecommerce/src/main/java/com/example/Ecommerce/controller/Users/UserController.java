package com.example.Ecommerce.controller.Users;



import com.example.Ecommerce.common.responseStatus.ResponseData;
import com.example.Ecommerce.common.responseStatus.ResponseError;
import com.example.Ecommerce.dto.UsersDto.UserUpdateRequestDTO;
import com.example.Ecommerce.model.User;
import com.example.Ecommerce.service.User.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Tag(name = "Users Controller")
public class UserController {

    @Autowired
    private final UserService userService;


    @Operation(summary = "Test API Users Controller")
    @GetMapping
    public ResponseEntity<String> sayHello(){
        return ResponseEntity.ok("Hello Users");
    }

    @Operation(summary = "Get Info User")
    @GetMapping("/info")
    public ResponseEntity<Object> getUserInfo() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            User user = (User) authentication.getPrincipal();

            if (user == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new ResponseError(401, "Unauthorized"));
            }

            return ResponseEntity.ok(new ResponseData<>(201, "User information retrieved successfully", user));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseError(500, "Internal Server Error"));
        }
    }

    @Operation(summary = "Update Info User")
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<?> updateUser(
            @PathVariable Long id,
            @RequestBody UserUpdateRequestDTO request,
            @RequestParam(value = "avatar", required = false) MultipartFile avatar
    ) {
        try {
            ResponseEntity<User> responseEntity = userService.updateUser(id, request, avatar);
            if (responseEntity.getStatusCode() == HttpStatus.OK) {
                User updatedUser = responseEntity.getBody();
                return ResponseEntity.ok(updatedUser);
            } else {
                return ResponseEntity.status(responseEntity.getStatusCode()).body(responseEntity.getBody());
            }
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Failed to update user: " + e.getMessage());
        }
    }
}
