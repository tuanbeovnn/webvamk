package com.vamkthesis.web.service.impl;


import com.vamkthesis.web.api.input.BannerInput;
import com.vamkthesis.web.convert.Converter;
import com.vamkthesis.web.dto.MyUserDTO;
import com.vamkthesis.web.entity.BannerEntity;
import com.vamkthesis.web.entity.UserEntity;
import com.vamkthesis.web.paging.PageList;
import com.vamkthesis.web.repository.IBannerRepository;
import com.vamkthesis.web.repository.UserRepository;
import com.vamkthesis.web.service.IBannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class BannerService implements IBannerService {
    @Autowired
    private IBannerRepository bannerRepository;
    @Autowired
    private UserRepository userRepository;

    @Secured("ROLE_ADMIN")
    @Override
    public BannerInput save(BannerInput input) {
        MyUserDTO myUserDTO = (MyUserDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();// lay thong tin user
        BannerEntity bannerEntity = Converter.toModel(input, BannerEntity.class);
        String image = String.join(";", input.getImage());
        bannerEntity.setImage(image);
        UserEntity userEntity = userRepository.findById(myUserDTO.getId()).get();
        bannerEntity.setUser(userEntity);
        bannerEntity = bannerRepository.save(bannerEntity);
        return Converter.toModel(bannerEntity, BannerInput.class);
    }

    @Override
    public void delete(Long id) {
        bannerRepository.deleteById(id);
    }

    @Override
    public List<BannerInput> findAllByPosition(int position) {
        MyUserDTO myUserDTO = (MyUserDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<BannerEntity> bannerEntities = bannerRepository.findAllByPosition(position);
        List<BannerInput> bannerInput = Converter.toList(bannerEntities, BannerInput.class);
        return bannerInput;
    }

    @Override
    public PageList<BannerInput> findAll(Pageable pageable) {
        List<BannerEntity> bannerEntity = bannerRepository.findAll();
        List<BannerInput> bannerInputs = Converter.toList(bannerEntity, BannerInput.class);
        PageList<BannerInput> pageList = new PageList<>();
        pageList.setList(bannerInputs);
        pageList.setSuccess(true);
        pageList.setPageSize(pageable.getPageSize());
        pageList.setCurrentPage(pageable.getPageNumber());
        return pageList;
    }



}
