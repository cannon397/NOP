package com.cannon.nop.interfaces.config.filter;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UriFilter {
    private static final List<String> uriList = new ArrayList<>(List.of(
            "/api/nop/v1/organizer",
            "/api/nop/v1/organizer/"
    ));


    public boolean isExcludedUri(String requestUri) {
        for (String targetUri : uriList){
            if(requestUri.equals(targetUri)){
                return true;
            }
        }
        return false;
    }
}
