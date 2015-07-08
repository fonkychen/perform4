package cn.aolc.group.performance.security;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

public class KpiAuthenticationEntryPoint extends LoginUrlAuthenticationEntryPoint{
	private final RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
	
	
	@Value("${server.proto}://${server.host}:${server.port}")
	private String server;
	
	public KpiAuthenticationEntryPoint(String loginFormUrl){
		super(loginFormUrl);
	}
	
	public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException, ServletException {
		//logger.info("commence from "+request.getRequestURI());
		String redirectUrl = null;

        if (isUseForward()) {

            if (isForceHttps() && "http".equals(request.getScheme())) {
                // First redirect the current request to HTTPS.
                // When that request is received, the forward to the login page will be used.
                redirectUrl = buildHttpsRedirectUrlForRequest(request);
            }

            if (redirectUrl == null) {
                String loginForm = determineUrlToUseForThisRequest(request, response, authException);

              

                RequestDispatcher dispatcher = request.getRequestDispatcher(loginForm);

                dispatcher.forward(request, response);

                return;
            }
        } else {
            // redirect to login page. Use https if forceHttps true

            redirectUrl = buildRedirectUrlToLoginPage(request, response, authException);
            String newparam=redirectUrl.indexOf("?")>0?"&kpiredirect=":"?kpiredirect=";
           // String server=serverproto+"://"+serverhost+(serverport.equals(80)?"":serverport);
            newparam=newparam+URLEncoder.encode(server+request.getRequestURI(),"UTF-8");
            redirectUrl=redirectUrl+newparam;

        }

        redirectStrategy.sendRedirect(request, response, redirectUrl);

	}

}
