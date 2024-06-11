package com.cydeo.entity;

import com.cydeo.entity.common.UserPrincipal;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;

public class BaseEntityListener extends AuditingEntityListener {


    @PrePersist
    public void onePrePersist(BaseEntity baseEntity){

        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        baseEntity.insertDateTime = LocalDateTime.now();
        baseEntity.lastUpdateDateTime = LocalDateTime.now();
//        baseEntity.insertUserId = 1L;
//        baseEntity.lastUpdateUserId = 1L;

        if (authentication != null && !authentication.name().equals("anonymousUser")){
            Object principal = authentication.getPricipal();
            baseEntity.insertUserId = ((UserPrincipal) principal.getId());
            baseEntity.lastUpdateUserId = ((UserPrincipal) principal.getId());
        }
    }

    @PreUpdate
    public void onPreUpdate(BaseEntity baseEntity){

        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        baseEntity.lastUpdateDateTime = LocalDateTime.now();
//        baseEntity.lastUpdateUserId = 1L;

        if (authentication != null && !authentication.name().equals("anonymousUser")){
            Object principal = authentication.getPricipal();
            baseEntity.lastUpdateUserId = ((UserPrincipal) principal.getId());
        }
    }

}
