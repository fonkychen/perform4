package cn.aolc.group.performance.security;

import java.lang.reflect.Method;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.util.ReflectionUtils;

public class KpiPersistentTokenBasedRememberMeService extends PersistentTokenBasedRememberMeServices{
	
	private String domain;
	
	private Boolean useSecureCookie;
	
	private Method setHttpOnlyMethod;
	
	public KpiPersistentTokenBasedRememberMeService(String key, UserDetailsService userDetailsService,PersistentTokenRepository tokenRepository) {
		super(key, userDetailsService,tokenRepository);	   
		setHttpOnlyMethod=ReflectionUtils.findMethod(Cookie.class,"setHttpOnly", boolean.class);
	}
	
	protected void setCookie(String[] tokens, int maxAge, HttpServletRequest request, HttpServletResponse response) {
		String cookieValue = encodeCookie(tokens);
        Cookie cookie = new Cookie(getCookieName(), cookieValue);
        cookie.setMaxAge(maxAge);
        cookie.setPath(getCookiePath(request));

        if(maxAge < 1) {
            cookie.setVersion(1);
        }

        if (useSecureCookie == null) {
            cookie.setSecure(request.isSecure());
        } else {
            cookie.setSecure(useSecureCookie);
        }

        if(setHttpOnlyMethod != null) {
            ReflectionUtils.invokeMethod(setHttpOnlyMethod, cookie, Boolean.TRUE);
        } else if (logger.isDebugEnabled()) {
            logger.debug("Note: Cookie will not be marked as HttpOnly because you are not using Servlet 3.0 (Cookie#setHttpOnly(boolean) was not found).");
        }
        
        if(domain!=null){
        	cookie.setDomain(domain);
        }
        response.addCookie(cookie);
	}
	
	private String getCookiePath(HttpServletRequest request) {
		String contextPath = request.getContextPath();
		return contextPath.length() > 0 ? contextPath : "/";
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public Boolean getUseSecureCookie() {
		return useSecureCookie;
	}

	public void setUseSecureCookie(Boolean useSecureCookie) {
		this.useSecureCookie = useSecureCookie;
	}
}
