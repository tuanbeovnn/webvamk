package com.vamkthesis.web.api;


import com.vamkthesis.web.dto.RoleDto;
import com.vamkthesis.web.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
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

    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/remove/{id}", method = RequestMethod.DELETE)
    public void removeById(@RequestParam long id){
        roleService.deleteById(id);
    }

    @Secured ("ROLE_ADMIN")
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public RoleDto update(@RequestBody RoleDto roleDto, @PathVariable("id") long id) {
        roleDto.setId(id);
        return roleService.save(roleDto);
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<RoleDto> showListRoles(@ModelAttribute RoleDto roleDto){
        return roleService.findAll();
    }
}
