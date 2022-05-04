package com.epam.clients;
import com.epam.dto.UserDto;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@FeignClient(name="user")
@LoadBalancerClient(name="user")
public interface UserClient {
    @GetMapping("/users")
    ResponseEntity<List<UserDto>> getUser();
    @GetMapping("/users/{username}")
    ResponseEntity<UserDto> getUserByUsername(@PathVariable(value = "username") String username);
    @DeleteMapping("/users/{username}")
    ResponseEntity<String> deleteUser(@PathVariable(value = "username") String username);
    @PutMapping("/users/{username}")
    ResponseEntity<String> updateUser(@PathVariable(value = "username") String username,@RequestBody UserDto userDto);
    @PostMapping("/users")
    ResponseEntity<UserDto> addUser(@RequestBody UserDto userDto);
}
