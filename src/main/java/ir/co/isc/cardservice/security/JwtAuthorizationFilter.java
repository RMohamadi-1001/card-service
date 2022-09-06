package ir.co.isc.cardservice.security;//package com.isc.cardservice.security;
//
//import io.jsonwebtoken.*;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
//import org.springframework.util.StringUtils;
//
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.util.List;
//import java.util.stream.Collectors;
//
//public class JwtAuthorizationFilter extends BasicAuthenticationFilter {
//    private static final Logger log = LoggerFactory.getLogger(JwtAuthorizationFilter.class);
//
//
//    public JwtAuthorizationFilter(AuthenticationManager authenticationManager) {
//        super(authenticationManager);
//    }
//
//
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
//                                    FilterChain filterChain) throws IOException, ServletException {
//        UsernamePasswordAuthenticationToken authentication = getAuthentication(request);
//        if (authentication == null) {
//            filterChain.doFilter(request, response);
//            return;
//        }
//
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//        filterChain.doFilter(request, response);
//    }
//
//    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
//        String token = request.getHeader("Authorization");
//        if (!StringUtils.isEmpty(token) && token.startsWith("Bearer ")) {
//            try {
//                byte[] signingKey = "n2r5u8x/A%D*G-KaPdSgVkYp3s6v9y$B&E(H+MbQeThWqZq4t7w!z%C*F-J@NcRf".getBytes();
//
//                Jws<Claims> parsedToken = Jwts.parser()
//                        .setSigningKey(signingKey)
//                        .parseClaimsJws(token.replace("Bearer ", ""));
//
//                String username = parsedToken
//                        .getBody()
//                        .getSubject();
//
//                List<GrantedAuthority> authorities = ((List<?>) parsedToken.getBody()
//                        .get("rol")).stream()
//                        .map(authority -> new SimpleGrantedAuthority((String) authority))
//                        .collect(Collectors.toList());
//
//                if (!StringUtils.isEmpty(username)) {
//                    return new UsernamePasswordAuthenticationToken(username, null, authorities);
//                }
//            } catch (ExpiredJwtException exception) {
//                log.warn("Request to parse expired JWT : {} failed : {}", token, exception.getMessage());
//            } catch (UnsupportedJwtException exception) {
//                log.warn("Request to parse unsupported JWT : {} failed : {}", token, exception.getMessage());
//            } catch (MalformedJwtException exception) {
//                log.warn("Request to parse invalid JWT : {} failed : {}", token, exception.getMessage());
//            } catch (SignatureException exception) {
//                log.warn("Request to parse JWT with invalid signature : {} failed : {}", token, exception.getMessage());
//            } catch (IllegalArgumentException exception) {
//                log.warn("Request to parse empty or null JWT : {} failed : {}", token, exception.getMessage());
//            }
//        }
//
//        return null;
//    }
//
//}
