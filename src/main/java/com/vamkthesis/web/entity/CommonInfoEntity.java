//package com.vamkthesis.web.entity;
//
//import com.fasterxml.jackson.annotation.JsonIgnore;
//import com.fasterxml.jackson.annotation.JsonProperty;
//import lombok.Getter;
//import lombok.Setter;
//import org.apache.commons.lang3.StringUtils;
//import org.springframework.data.jpa.domain.support.AuditingEntityListener;
//
//import javax.persistence.Entity;
//import javax.persistence.EntityListeners;
//import javax.persistence.MappedSuperclass;
//
//@MappedSuperclass
//@EntityListeners(AuditingEntityListener.class)
//@Getter
//@Setter
//public class CommonInfoEntity extends BaseEntity {
//    private String name;
//    @JsonIgnore
//    private String image;
//    private String description;
//    private String status;
//    private double price;
//    private String code;
//    private double originalPrice;
//
//    @JsonProperty("image")
//    public String[] getImage() {
//        return StringUtils.isEmpty(image) ? new String[]{} : image.split(";");
//    }
//}
