package com.example.Reservation.security;

import com.example.Reservation.entity.RoleModel;
import com.example.Reservation.entity.TokenInfo;
import com.example.Reservation.entity.User;
import com.example.Reservation.repository.UserRepo;
import com.example.Reservation.service.RoleService;
import com.example.Reservation.service.TokenInfoService;
import com.example.Reservation.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Log4j2
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authManager;

    private final HttpServletRequest httpRequest;

    private final TokenInfoService tokenInfoService;

    private final JwtTokenUtils jwtTokenUtils;
    private final UserRepo userRepo;
    private final UserService userService;
    private final RoleService roleService;

    public JWTResponseDto login(String login, String password) {
        Authentication authentication = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(login, password));

        log.debug("Valid userDetails credentials.");

        AppUserDetail userDetails = (AppUserDetail) authentication.getPrincipal();

        SecurityContextHolder.getContext().setAuthentication(authentication);
        log.debug("SecurityContextHolder updated. [login={}]", login);


        TokenInfo tokenInfo = createLoginToken(login, userDetails.getId());


        return JWTResponseDto.builder()
                .accessToken(tokenInfo.getAccessToken())
                .refreshToken(tokenInfo.getRefreshToken())
//                .user(userRepo.findByUserName(login).get())
                .user(userRepo.findByEmail(login).get())
                .build();
    }

    public JWTResponseDto signUp(String fullName, String phone, String userName,String email, String password) {
//        Optional<User> user = userRepo.findByUserName(userName);
        Optional<User> user = userRepo.findByEmail(email);
        if (user.isPresent()) {
            throw new UsernameNotFoundException("This UserName Already Exists");
//            return ResponseEntity.badRequest();
        }
        else{
            List<RoleModel> userRoles = new ArrayList<>();
            userRoles.add(roleService.findByName("user"));
            userService.save(new User(null, userName, email, password, phone, fullName, userRoles,true,true,true,true));
        }

        Authentication authentication = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password));

        log.debug("Valid userDetails credentials.");

        AppUserDetail userDetails = (AppUserDetail) authentication.getPrincipal();

        SecurityContextHolder.getContext().setAuthentication(authentication);
        log.debug("SecurityContextHolder updated. [login={}]", userName);


        TokenInfo  tokenInfo = createLoginToken(email, userDetails.getId());


        return JWTResponseDto.builder()
                .accessToken(tokenInfo.getAccessToken())
                .refreshToken(tokenInfo.getRefreshToken())
//                .user(userRepo.findByUserName(userName).get())
                .user(userRepo.findByEmail(email).get())
                .build();
    }

    public TokenInfo createLoginToken(String userName, Long userId) {
        String userAgent = httpRequest.getHeader(HttpHeaders.USER_AGENT);
        InetAddress ip = null;
        try {
            ip = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        String accessTokenId = UUID.randomUUID().toString();
        String accessToken = JwtTokenUtils.generateToken(userName, accessTokenId, false);
        log.info("Access token created. [tokenId={}]", accessTokenId);

        String refreshTokenId = UUID.randomUUID().toString();
        String refreshToken = JwtTokenUtils.generateToken(userName, refreshTokenId, true);
        log.info("Refresh token created. [tokenId={}]", accessTokenId);

        TokenInfo tokenInfo = new TokenInfo(accessToken, refreshToken);
        tokenInfo.setUser(new User(userId));
        tokenInfo.setUserAgentText(userAgent);
        tokenInfo.setLocalIpAddress(ip.getHostAddress());
        tokenInfo.setRemoteIpAddress(httpRequest.getRemoteAddr());
        // tokenInfo.setLoginInfo(createLoginInfoFromRequestUserAgent());
        return tokenInfoService.save(tokenInfo);
    }


    public AccessTokenDto refreshAccessToken(String refreshToken) {
        if (jwtTokenUtils.isTokenExpired(refreshToken)) {
            return null;
        }
        String userName = jwtTokenUtils.getUserNameFromToken(refreshToken);
        Optional<TokenInfo> refresh = tokenInfoService.findByRefreshToken(refreshToken);
        if (!refresh.isPresent()) {
            return null;
        }

        return new AccessTokenDto(JwtTokenUtils.generateToken(userName, UUID.randomUUID().toString(), false));

    }


    public void logoutUser(String refreshToken) {
        Optional<TokenInfo> tokenInfo = tokenInfoService.findByRefreshToken(refreshToken);
        if (tokenInfo.isPresent()) {
            tokenInfoService.deleteById(tokenInfo.get().getId());
        }
    }
}