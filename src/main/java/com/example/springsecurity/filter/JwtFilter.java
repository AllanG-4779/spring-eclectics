package com.example.springsecurity.filter;

import com.example.springsecurity.security.CustomUserDetailsService;
import com.example.springsecurity.security.JwtUtility;
import com.sun.istack.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {
    @Autowired
        private CustomUserDetailsService userDetailsService;
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {
        String username = null;
        String mytoken = null;

        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader !=null && authorizationHeader.startsWith("Bearer")){
            String[] token = authorizationHeader.split(" ");
            String finalToken = token[1];
            username = new JwtUtility().extractUsername(finalToken);
        }
        if(username != null && SecurityContextHolder.getContext().getAuthentication() == null){
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            UsernamePasswordAuthenticationToken usernamepasswordAuthenticationToken =
                    new UsernamePasswordAuthenticationToken(userDetails,null,
                            userDetails.getAuthorities());
            usernamepasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(usernamepasswordAuthenticationToken)
            ;

        }

 filterChain.doFilter(request,response);

    }
}
