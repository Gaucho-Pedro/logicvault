package org.artel.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.artel.dto.UserDto;
import org.artel.entity.User;
import org.artel.service.UserService;
import org.artel.util.MappingUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class UserController {

    UserService userService;
    MappingUtil mappingUtil;

    @PutMapping("/{userId}/password")
    public ResponseEntity<UserDto> changePassword(@PathVariable Long userId, @RequestParam String oldPassword, @RequestParam String newPassword) {
        return ResponseEntity.ok(mappingUtil.toDto(userService.changePassword(userId, oldPassword, newPassword), UserDto.class));
    }

    @PatchMapping
    public ResponseEntity<UserDto> changeUser(@RequestBody UserDto userDto) {
        return ResponseEntity.ok(mappingUtil.toDto(userService.update(mappingUtil.toEntity(userDto, User.class)), UserDto.class));
    }
}
