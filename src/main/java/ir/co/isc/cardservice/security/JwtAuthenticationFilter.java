package ir.co.isc.cardservice.security;//package com.isc.cardservice.security;
//
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
//import io.jsonwebtoken.security.Keys;
//import org.apache.tomcat.util.codec.binary.Base64;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//
//import javax.servlet.FilterChain;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.UnsupportedEncodingException;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//import java.util.StringTokenizer;
//import java.util.stream.Collectors;
//
//public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
//
//    private final AuthenticationManager authenticationManager;
//    UserDetailsService userDetailsService;
//    public static final String TOKEN_HEADER = "Authorization";
//    public static final String TOKEN_PREFIX = "Bearer ";
//    public static final String TOKEN_TYPE = "JWT";
//
//
//    public JwtAuthenticationFilter(AuthenticationManager authenticationManager, UserDetailsService userDetailsService) {
//        this.authenticationManager = authenticationManager;
//        this.userDetailsService = userDetailsService;
//
//        setFilterProcessesUrl("/api/authenticate");
//    }
//
//    @Override
//    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
//        String authHeader = request.getHeader("Authorization");
//        if (authHeader != null) {
//            StringTokenizer st = new StringTokenizer(authHeader);
//            if (st.hasMoreTokens()) {
//                String basic = st.nextToken();
//
//                if (basic.equalsIgnoreCase("Basic")) {
//                    try {
//                        String credentials = new String(Base64.decodeBase64(st.nextToken()), "UTF-8");
//                        int p = credentials.indexOf(":");
//                        if (p != -1) {
//                            String _username = credentials.substring(0, p).trim();
//                            String _password = credentials.substring(p + 1).trim();
//                            String username = _username;
//                            String password = _password;
//                            List<GrantedAuthority> authority = new ArrayList<>();
//                            authority.add(new SimpleGrantedAuthority("admin"));
//                            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
//                            if (userDetails != null) {
//                                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());
//                                return authenticationManager.authenticate(usernamePasswordAuthenticationToken);
//                            }
//                        }
//                    } catch (UnsupportedEncodingException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        }
//        return null;
//    }
//
//    @Override
//    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
//                                            FilterChain filterChain, Authentication authentication) {
//        User user = ((User) authentication.getPrincipal());
//
//        List<String> roles = user.getAuthorities()
//                .stream()
//                .map(GrantedAuthority::getAuthority)
//                .collect(Collectors.toList());
//
//        byte[] signingKey = "n2r5u8x/A%D*G-KaPdSgVkYp3s6v9y$B&E(H+MbQeThWqZq4t7w!z%C*F-J@NcRf".getBytes();
//
//        String token = Jwts.builder()
//                .signWith(Keys.hmacShaKeyFor(signingKey), SignatureAlgorithm.HS512)
//                .setHeaderParam("typ", TOKEN_TYPE)
//                .setSubject(user.getUsername())
//                .setExpiration(new Date(System.currentTimeMillis() + 864000000))
//                .claim("rol", roles)
//                .compact();
//
//        response.addHeader(TOKEN_HEADER, TOKEN_PREFIX + token);
//    }
//}
