package com.diyiliu.support.shiro.filter;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * Description: FormLoginFilter
 * Author: DIYILIU
 * Update: 2016-02-19 9:34
 */
public class FormLoginFilter extends FormAuthenticationFilter {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private String loginUrl;
    private String successUrl;

    private String username;
    private String password;
    private String rememberMe;

    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response) throws Exception {

        logger.debug("用户登录 ... ");

        AuthenticationToken token = createToken(request, response);
        if (token == null) {
            String msg = "createToken method implementation returned null. A valid non-null AuthenticationToken " +
                    "must be created in order to execute a login attempt.";
            throw new IllegalStateException(msg);
        }
        try {
            Subject subject =  SecurityUtils.getSubject();
            subject.login(token);

            return onLoginSuccess(token, subject, request, response);
        } catch (AuthenticationException e) {
            return onLoginFailure(token, e, request, response);
        }
    }

    @Override
    protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) {

        boolean rememberMe = isRememberMe(request);
        String host = getHost(request);

        return new UsernamePasswordToken(getUsername(request), getPassword(request), rememberMe, host);
    }

    @Override
    public String getHost(ServletRequest request) {
        return request.getRemoteHost();
    }
    @Override
    public String getLoginUrl() {
        return loginUrl;
    }
    @Override
    public String getSuccessUrl() {
        return successUrl;
    }
    @Override
    public String getUsernameParam() {
        return username;
    }
    @Override
    public String getPasswordParam() {
        return password;
    }
    @Override
    public String getRememberMeParam() {
        return rememberMe;
    }

    public void setLoginUrl(String loginUrl) {
        this.loginUrl = loginUrl;
    }
    public void setSuccessUrl(String successUrl) {
        this.successUrl = successUrl;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setRememberMe(String rememberMe) {
        this.rememberMe = rememberMe;
    }
}
