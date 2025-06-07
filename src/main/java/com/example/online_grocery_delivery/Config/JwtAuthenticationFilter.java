package com.example.online_grocery_delivery.Config;

import com.example.online_grocery_delivery.Services.UserDetailsImp;
import com.example.online_grocery_delivery.Utill.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserDetailsImp userDetailsImp;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
        throws ServletException, IOException{
        String path = request.getServletPath();
        if (path.equals("/login") || path.equals("/register")) {
            filterChain.doFilter(request, response);
            return;
        }
        String authHeader = request.getHeader("Authorization");
        String token = null;
        String userName = null;

        //extract token from header
        if (authHeader != null && authHeader.startsWith("Bearer ")){
             token = authHeader.substring(7);

            try{
                userName = jwtUtil.extractUserName(token);
                String role = jwtUtil.extractRole(token);
                System.out.println("Authenticated user : " + userName + " with role: " + role);
            }catch (Exception e){
                System.out.println("Invalid token: "+e.getMessage());
            }
        }
        //validate token and authentication
        if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null){
            UserDetails userDetails = userDetailsImp.loadUserByUsername(userName);

            if (jwtUtil.validateToken(token,userDetails)){
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());

                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request,response);
    }
}
