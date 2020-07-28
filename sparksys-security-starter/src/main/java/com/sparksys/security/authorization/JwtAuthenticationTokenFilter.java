package com.sparksys.security.authorization;

import com.sparksys.core.entity.AuthUserInfo;
import com.sparksys.core.base.api.ResponseResultUtils;
import com.sparksys.core.utils.StringHandleUtils;
import com.sparksys.jwt.entity.JwtUserInfo;
import com.sparksys.jwt.service.JwtTokenService;
import com.sparksys.security.entity.AuthUserDetail;
import com.sparksys.security.properties.SecurityProperties;
import com.sparksys.security.resource.IgnoreStaticResource;
import com.sparksys.security.service.AbstractAuthSecurityService;

import com.sparksys.user.service.IAuthUserInfoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * description: JWT登录授权过滤器
 *
 * @author zhouxinlei
 * @date 2020-05-24 13:34:44
 */
@Slf4j
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {


    @Resource
    private SecurityProperties securityProperties;

    @Resource
    private AbstractAuthSecurityService abstractSecurityAuthDetailService;

    @Resource
    private IAuthUserInfoService globalUserService;

    @Resource
    private JwtTokenService jwtTokenService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain chain) {
        try {
            log.info("请求地址：{}", request.getRequestURI());
            log.info("securityProperties：{}", securityProperties.getIgnoreUrls());
            List<String> ignoreUrls = securityProperties.getIgnoreUrls();
            if (!StringHandleUtils.isIgnore(ignoreUrls, request.getRequestURI())) {
                String accessToken = ResponseResultUtils.getAuthHeader(request);
                if (StringUtils.isNotEmpty(accessToken)) {
                    JwtUserInfo jwtUserInfo = jwtTokenService.verifyTokenByHmac(accessToken);
                    String username = jwtUserInfo.getUsername();
                    log.info("checking username:{}", username);
                    AuthUserInfo authUser = globalUserService.getUserInfo(accessToken);
                    if (StringUtils.equals(authUser.getAccount(), username)) {
                        AuthUserDetail authUserDetail = abstractSecurityAuthDetailService.getAuthUserDetail(username);
                        UsernamePasswordAuthenticationToken authentication =
                                new UsernamePasswordAuthenticationToken(authUserDetail, null, authUserDetail.getAuthorities());
                        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        log.info("authenticated user:{}", username);
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    }
                }
            }
            chain.doFilter(request, response);
        } catch (Exception e) {
            log.error(e.getMessage());
            ResponseResultUtils.unauthorized(response);
        }
    }
}