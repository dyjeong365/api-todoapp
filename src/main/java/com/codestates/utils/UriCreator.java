package com.codestates.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UriCreator {

    public static URI createUri(String defaultUrl, long resourceId) {
        return UriComponentsBuilder
                .newInstance()
                .path(defaultUrl + "/{resource-id}")
                .buildAndExpand(resourceId)
                .toUri();
    }
}
