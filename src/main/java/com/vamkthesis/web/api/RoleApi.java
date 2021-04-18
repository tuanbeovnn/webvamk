package com.vamkthesis.web.api;


import com.vamkthesis.web.api.output.ResponseEntityBuilder;
import com.vamkthesis.web.dto.RoleDto;
import com.vamkthesis.web.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/role")
public class RoleApi {
    @Autowired
    private IRoleService roleService;

    @RequestMapping(value="/save", method = RequestMethod.POST)
    public RoleDto createRole(@RequestBody RoleDto roleDto) {
        return roleService.save(roleDto);
    }


    @RequestMapping(value = "/remove/{id}", method = RequestMethod.DELETE)
    public void removeById(@RequestParam long id){
        roleService.deleteById(id);
    }


    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity update(@RequestBody RoleDto roleDto, @PathVariable("id") long id) {
        roleDto.setId(id);
        RoleDto roleDto1 = roleService.save(roleDto);
        return ResponseEntityBuilder.getBuilder().setDetails(roleDto1).setMessage("Update role successfully").build();
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResponseEntity showListRoles(@ModelAttribute RoleDto roleDto){
        List<RoleDto> roleDtos = roleService.findAll();
        return ResponseEntityBuilder.getBuilder().setDetails(roleDtos).setMessage("Get role list successfully").build();
    }
}
