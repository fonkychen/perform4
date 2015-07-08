package cn.aolc.group.performance.util.exception;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class SynchronizeOnSessionFilter implements Filter{

	
    public void init(FilterConfig arg0) throws ServletException
    {
    }

   
    public void destroy()
    {
    }

   
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException
    {
        HttpSession session = null;
        if (request instanceof HttpServletRequest)
        {
            session = ((HttpServletRequest)request).getSession(false);
        }
        if (session != null)
        {
            synchronized(session)
            {
                chain.doFilter(request, response);
            }
        }
        else
        {
            chain.doFilter(request, response);
        }
    }

}
