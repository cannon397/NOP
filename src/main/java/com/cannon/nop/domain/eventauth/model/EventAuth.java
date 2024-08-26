package com.cannon.nop.domain.eventauth.model;


import com.cannon.nop.interfaces.config.exception.ApiException;
import com.cannon.nop.interfaces.config.exception.ErrorCode;
import com.cannon.nop.util.Util;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@RedisHash(value = "EventAccessManagement")
@Getter
@ToString
public class EventAuth implements Serializable {
    @Id
    private String adminUrlUUID;
    private String adminKeyUUID;
    private String eventUrlUUID;


    @Builder
    public EventAuth(String adminKeyUUID,
                     String adminUrlUUID,
                     String eventUrlUUID) {
        if(eventUrlUUID == null){throw new NullPointerException("eventUrlUUID is null");}
        this.adminKeyUUID = adminKeyUUID;
        this.adminUrlUUID = adminUrlUUID;
        this.eventUrlUUID = eventUrlUUID;
    }
    EventAuth(){

    }
    public void createAdmin(){
         adminKeyUUID = adminKeyUUID != null ? adminKeyUUID : Util.getUUID();
         adminUrlUUID = adminUrlUUID != null ? adminUrlUUID : Util.getUUID();
    }


    public void isAdmin(String adminKeyUUID){
        if(!this.adminKeyUUID.equals(adminKeyUUID)){
            throw new ApiException(ErrorCode.INTERNAL_ERROR);
        }
    }
}
