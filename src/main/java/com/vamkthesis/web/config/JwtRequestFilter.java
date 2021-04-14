package com.vamkthesis.web.config;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vamkthesis.web.api.output.ResponseEntityBuilder;
import com.vamkthesis.web.dto.MyUserDTO;
import com.vamkthesis.web.entity.TokenEntity;
import com.vamkthesis.web.exception.ClientException;
import com.vamkthesis.web.exception.CustomException;
import com.vamkthesis.web.exception.TokenExpiredException;
import com.vamkthesis.web.repository.ITokenRepository;
import com.vamkthesis.web.service.impl.JwtUserService;
import io.jsonwebtoken.Claims;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Component
@Order(1)
public class JwtRequestFilter extends OncePerRequestFilter {
    @Autowired
    private JwtUserService jwtUserService;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    ITokenRepository tokenRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        logger.info(String.format("%s  %s", request.getMethod(), request.getRequestURI()));
        String req = request.getRequestURI();

        final String requestTokenHeader = request.getHeader("Authorization");
        if (req.startsWith("/ws")){
            chain.doFilter(request, response);
            return;
        }

        String username = null;
        String jwtToken = null;
        TokenEntity tokenEntity = null;
        logger.info(String.format("%s  %s", request.getMethod(), request.getRequestURI()));
        try {
            if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
                jwtToken = requestTokenHeader.substring(7);

                if (jwtTokenUtil.isTokenExpired(jwtToken)) {
                    if (!request.getRequestURI().contains("/api/auth"))
                        throw new TokenExpiredException();
                }
                String secretToken = jwtToken.substring(jwtToken.lastIndexOf(".")+1);
                tokenEntity = tokenRepository.findOneBySecret(secretToken);

                if (tokenEntity.getEnvoke() == 1 ){
                    throw new ClientException("your Token was expired");
                }
//                username = jwtTokenUtil.getUsernameFromToken(jwtToken);

                Claims claims = jwtTokenUtil.getAllClaimsFromToken(jwtToken);
                MyUserDTO myUserDTO = new ModelMapper().map(claims, MyUserDTO.class);

                List<GrantedAuthority> authorities = new ArrayList<>();
                for (String role : myUserDTO.getRoles()){
                    authorities.add(new SimpleGrantedAuthority("ROLE_"+role));
                }
                Authentication auth = new UsernamePasswordAuthenticationToken(myUserDTO, null, authorities);
                SecurityContextHolder.getContext().setAuthentication(auth);
            } else {
                logger.warn("JWT Token does not begin with Bearer String");
            }
            chain.doFilter(request, response);
        } catch (Exception ex) {
            HttpStatus status = ex instanceof CustomException ? ((CustomException) ex).code : HttpStatus.INTERNAL_SERVER_ERROR;
            response.setStatus(status.value());
            ResponseEntity responseEntityBuilder = ResponseEntityBuilder.getBuilder()
                    .setCode(status)
                    .setMessage(ex.getMessage())
                    .build();
            response.getWriter().write(convertObjectToJson(responseEntityBuilder.getBody()));
        }
    }

    public String convertObjectToJson(Object object) throws JsonProcessingException {
        if (object == null) {
            return null;
        }
        ObjectMapper mapper = new ObjectMapper();
        return  mapper.writerWithDefaultPrettyPrinter().writeValueAsString(object);
    }
}
