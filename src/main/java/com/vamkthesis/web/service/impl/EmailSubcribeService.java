package com.vamkthesis.web.service.impl;

import com.vamkthesis.web.convert.Converter;
import com.vamkthesis.web.dto.EmailSubcribeDto;
import com.vamkthesis.web.entity.EmailSubcribeEntity;
import com.vamkthesis.web.repository.IEmailRepository;
import com.vamkthesis.web.service.IEmailSubcribeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmailSubcribeService implements IEmailSubcribeService {
    @Autowired
    private IEmailRepository emailRepository;

    @Override
    public EmailSubcribeDto save(EmailSubcribeDto emailSubcribeDto) {
        EmailSubcribeEntity emailSubcribeEntity = Converter.toModel(emailSubcribeDto, EmailSubcribeEntity.class);
        emailSubcribeEntity = emailRepository.save(emailSubcribeEntity);
        return Converter.toModel(emailSubcribeEntity, EmailSubcribeDto.class);
    }
}
