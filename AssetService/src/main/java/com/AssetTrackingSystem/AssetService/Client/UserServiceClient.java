package com.AssetTrackingSystem.AssetService.Client;


import com.AssetTrackingSystem.AssetService.DTO.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Set;

@FeignClient(name = "user-service")
public interface UserServiceClient {

    @PostMapping("/user/batch")
    Map<Long, UserDTO> getUsersByIds(@RequestBody Set<Long> userIds, @RequestHeader("X-User-Id") String userId, @RequestHeader("X-User-Role") String role, @RequestHeader("X-User-Name") String name);

    @GetMapping("/user/{userId}")
    UserDTO getUserById(@PathVariable(value = "userId") Long id, @RequestHeader("X-User-Id") String userId, @RequestHeader("X-User-Role") String role, @RequestHeader("X-User-Name") String name);

}
