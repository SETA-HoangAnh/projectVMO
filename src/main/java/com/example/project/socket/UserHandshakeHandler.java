package com.example.project.socket;

import com.sun.security.auth.UserPrincipal;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.util.MultiValueMap;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;
import org.springframework.web.util.UriComponentsBuilder;

import java.security.Principal;
import java.util.Map;

public class UserHandshakeHandler extends DefaultHandshakeHandler {

    protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler, Map<String, Object> attributes) {
        MultiValueMap<String, String> queryParams = UriComponentsBuilder.fromUri(request.getURI()).build().getQueryParams();
        String userId = queryParams.getFirst("userId");
        return new UserPrincipal(userId);
    }
}
