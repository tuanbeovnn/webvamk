package com.vamkthesis.web.api;

import com.vamkthesis.web.api.input.UserUpdateInput;
import com.vamkthesis.web.api.output.ResponseEntityBuilder;
import com.vamkthesis.web.dto.UserDto;
import com.vamkthesis.web.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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


    @RequestMapping(value = "/update/{id}", method = RequestMethod.PUT)
    public ResponseEntity updateProfile(@RequestBody UserUpdateInput userDTO, @PathVariable("id") long id) {
        userDTO.setId(id);
        UserUpdateInput userDTO1 = userService.updateInfo(userDTO);
        return ResponseEntityBuilder.getBuilder().setMessage("Update user successfully").setDetails(userDTO1).build();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") long id) {
        long[] ids = new long[]{id};
        userService.delete(ids);
    }
}
