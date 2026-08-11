package com.inventory_management.security.ratelimit;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class RateLimitFilter extends OncePerRequestFilter {

    private final ClientIpResolver clientIpResolver;
    private final RateLimitService rateLimitService;

    public RateLimitFilter(
            ClientIpResolver clientIpResolver,
            RateLimitService rateLimitService
    ) {
        this.clientIpResolver = clientIpResolver;
        this.rateLimitService = rateLimitService;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {

        String path = request.getRequestURI();

        return path.startsWith("/swagger-ui/")
                || path.startsWith("/v3/api-docs/");
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        String clientIp = clientIpResolver.resolve(request);

        boolean allowed = rateLimitService.isAllowed(clientIp);

        if (!allowed) {

            response.setStatus(429);
            response.setContentType("application/json");
            response.setHeader("Retry-After", "1");

            response.getWriter().write("""
                    {
                        "status": 429,
                        "error": "Too Many Requests",
                        "message": "Rate limit exceeded. Please try again later."
                    }
                    """);

            return;
        }

        filterChain.doFilter(request, response);
    }
}