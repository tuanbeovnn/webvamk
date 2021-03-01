package com.vamkthesis.web.service.impl;


import com.vamkthesis.web.convert.Converter;
import com.vamkthesis.web.dto.RoleDto;
import com.vamkthesis.web.entity.RoleEntity;
import com.vamkthesis.web.repository.IRoleRepository;
import com.vamkthesis.web.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService implements IRoleService {
    @Autowired
    private IRoleRepository roleRepository;
    @Override
    public RoleDto save(RoleDto roleDto) {
        RoleEntity roleEntity = new RoleEntity();
        if (roleDto.getId() != null){
            RoleEntity oldBrand = roleRepository.findById(roleDto.getId()).get();
            roleEntity = Converter.toModel(roleDto, oldBrand.getClass());
        }else {
            roleEntity = Converter.toModel(roleDto,RoleEntity.class);
        }
        roleEntity = roleRepository.save(roleEntity);
        return Converter.toModel(roleEntity, RoleDto.class);
    }

    @Override
    public void deleteById(Long id) {
        roleRepository.deleteById(id);
    }

    @Override
    public List<RoleDto> findAll() {
        List<RoleEntity> roleEntity = roleRepository.findAll();
        List<RoleDto> roleDto = Converter.toList(roleEntity, RoleDto.class);
        return roleDto;
    }
}
