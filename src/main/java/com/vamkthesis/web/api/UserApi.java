package com.vamkthesis.web.api;

import com.vamkthesis.web.api.input.UpdateRoleInput;
import com.vamkthesis.web.api.input.UserUpdateInput;
import com.vamkthesis.web.api.output.ResponseEntityBuilder;
import com.vamkthesis.web.api.output.UserOutput;
import com.vamkthesis.web.dto.UserDto;
import com.vamkthesis.web.paging.PageList;
import com.vamkthesis.web.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public PageList<UserOutput> findAllByRoles(Pageable pageable) {
        PageList<UserOutput> output = userService.findAllByRoles(pageable);
        return output;
    }


    @RequestMapping(value = "/update/{id}", method = RequestMethod.PUT)
    public ResponseEntity updateProfile(@RequestBody UserUpdateInput userDTO, @PathVariable("id") long id) {
        userDTO.setId(id);
        UserUpdateInput userDTO1 = userService.updateInfo(userDTO);
        return ResponseEntityBuilder.getBuilder().setMessage("Update user successfully").setDetails(userDTO1).build();
    }

    @PreAuthorize("hasRole('ROLE_STAFF') or hasRole('ROLE_ADMIN')")
    @DeleteMapping(value = "/remove/{id}")
    public ResponseEntity deleteProduct(@PathVariable("id") long id) {
        userService.deleteById(id);
        return ResponseEntityBuilder.getBuilder().setMessage("Delete product successfully").build();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") long id) {
        long[] ids = new long[]{id};
        userService.delete(ids);
    }

    @RequestMapping(value = "/updateRole/{id}", method = RequestMethod.PUT)
    public ResponseEntity updateRole(@RequestBody UpdateRoleInput roleInput, @PathVariable("id") long id) {
        roleInput.setId(id);
        UserOutput userOutput = userService.updateRole(id, roleInput);
        return ResponseEntityBuilder.getBuilder().setMessage("Update role successfully").setDetails(userOutput).build();
    }

    @RequestMapping(value = "/deactive/{id}", method = RequestMethod.POST)
    public ResponseEntity deActive(@PathVariable("id") long id) {
        userService.deactiveAccount(id);
        return ResponseEntityBuilder.getBuilder().setMessage("Update account successfully").build();
    }
}
