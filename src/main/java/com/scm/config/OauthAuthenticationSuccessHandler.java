package com.scm.config;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.scm.entities.Providers;
import com.scm.entities.User;
import com.scm.helper.AppConstant;
import com.scm.repositories.UserRepository;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class OauthAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private UserRepository userRepository;

   Logger logger =  LoggerFactory.getLogger(OauthAuthenticationSuccessHandler.class);

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
                
        logger.info("OauthAuthenticationSuccessHandler");
        // response.sendRedirect("/home");
        
                var oAuth2AuthenticationToken=(OAuth2AuthenticationToken)authentication;

                String authorizedClientRegistrationId = oAuth2AuthenticationToken.getAuthorizedClientRegistrationId();

                DefaultOAuth2User oAuthUser = (DefaultOAuth2User)authentication.getPrincipal();

                User user = new User();
                user.setUserId(UUID.randomUUID().toString());
                user.setRoleList(List.of(AppConstant.ROLE_USER));
                user.setEmailVerified(true);
                user.setEnabled(true);
                user.setPassword("password");



                if(authorizedClientRegistrationId.equalsIgnoreCase("google")){
                    //add google attributes

                    user.setEmail(oAuthUser.getAttribute("email").toString());
                    user.setProfilePic(oAuthUser.getAttribute("picture").toString());
                    user.setName(oAuthUser.getAttribute("name").toString());
                    user.setProviderUserId(oAuthUser.getName());
                    user.setProvider(Providers.GOOGLE);
                    user.setAbout("This account is created using Google");


                }else if(authorizedClientRegistrationId.equalsIgnoreCase("github")){
                    //add github attributes

                    String email = oAuthUser.getAttribute("email") !=null ? oAuthUser.getAttribute("email").toString() : oAuthUser.getAttribute("login").toString()+"@gmail.com";
                    String picture = oAuthUser.getAttribute("avatar_url").toString();
                    String name = oAuthUser.getAttribute("login").toString();
                    String providerId = oAuthUser.getName();

                    user.setEmail(email);
                    user.setProfilePic(picture);
                    user.setName(name);
                    user.setProviderUserId(providerId);
                    user.setProvider(Providers.GITHUB);
                    user.setAbout("This account is created using Github");
                }

                // now save the user
                User user1 = userRepository.findByEmail(user.getEmail()).orElse(null);
                if(user1 == null){
                    userRepository.save(user);
                }

        /* 
        DefaultOAuth2User user = (DefaultOAuth2User)authentication.getPrincipal();
        String name = user.getAttribute("name").toString();
        String email = user.getAttribute("email").toString();
        String picture = user.getAttribute("picture").toString();

        // Create a User and save into the database.
        User user1 = new User();
        user1.setUserId(UUID.randomUUID().toString());
        user1.setName(name);
        user1.setEmail(email);
        user1.setPassword("password");
        user1.setProfilePic(picture);
        user1.setProvider(Providers.GOOGLE);
        user1.setEnabled(true);
        user1.setEmailVerified(true);
        user1.setProviderUserId(user.getName());
        user1.setRoleList(List.of(AppConstant.ROLE_USER));
        user1.setAbout("This account is created using Google");

        User user2 = userRepository.findByEmail(email).orElse(null);
        if (user2 == null) {
            userRepository.save(user1);
            
        }
        */
        new DefaultRedirectStrategy().sendRedirect(request, response, "/user/profile");
    }


}
