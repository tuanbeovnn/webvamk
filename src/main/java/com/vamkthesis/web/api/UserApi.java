package com.vamkthesis.web.api;

import com.vamkthesis.web.dto.UserDto;
import com.vamkthesis.web.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserApi {
    @Autowired
    private IUserService userService;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity findById(@PathVariable("id") Long id) {
        UserDto dto = userService.findUserById(id);
        if (dto != null) {
            return ResponseEntity.ok(dto);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
