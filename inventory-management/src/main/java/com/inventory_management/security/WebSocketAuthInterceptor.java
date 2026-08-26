package com.inventory_management.security;

import lombok.RequiredArgsConstructor;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.messaging.support.ChannelInterceptor;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class WebSocketAuthInterceptor
        implements ChannelInterceptor {


    private final JwtService jwtService;

    private final UserDetailsService userDetailsService;


    @Override
    public Message<?> preSend(
            Message<?> message,
            MessageChannel channel
    ) {

        StompHeaderAccessor accessor =
                StompHeaderAccessor.wrap(message);


        /*
         * ============================================
         * STOMP CONNECT
         * ============================================
         */

        if (StompCommand.CONNECT.equals(
                accessor.getCommand()
        )) {

            String authorization =
                    accessor.getFirstNativeHeader(
                            "Authorization"
                    );


            if (
                    authorization == null ||
                            !authorization.startsWith("Bearer ")
            ) {

                throw new IllegalArgumentException(
                        "Missing or invalid Authorization header"
                );

            }


            String token =
                    authorization.substring(7);


            String username =
                    jwtService.extractUsername(token);


            UserDetails userDetails =
                    userDetailsService.loadUserByUsername(
                            username
                    );


            if (
                    !jwtService.isTokenValid(
                            token,
                            userDetails
                    )
            ) {

                throw new IllegalArgumentException(
                        "Invalid or expired JWT token"
                );

            }


            Authentication authentication =
                    new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );


            /*
             * Attach authenticated user
             * to WebSocket/STOMP session
             */

            accessor.setUser(authentication);


            /*
             * Store user ID in session
             * for later WebSocket messages
             */

            CustomUserDetails customUserDetails =
                    (CustomUserDetails) userDetails;


            accessor.getSessionAttributes().put(
                    "userId",
                    customUserDetails
                            .getUser()
                            .getUserId()
            );

            /*
             * Keep header updates mutable and rebuild
             * the message so STOMP user/session data
             * is retained for later routing.
             */

            accessor.setLeaveMutable(true);

            return MessageBuilder.createMessage(
                    message.getPayload(),
                    accessor.getMessageHeaders()
            );

        }


        /*
         * ============================================
         * RETURN MESSAGE
         * ============================================
         */

        return message;

    }

}