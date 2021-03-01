package com.vamkthesis.web.service;


import com.vamkthesis.web.dto.RoleDto;

import java.util.List;

public interface IRoleService {
    RoleDto save(RoleDto roleDto);
    void deleteById(Long id);
    List<RoleDto> findAll();
}
