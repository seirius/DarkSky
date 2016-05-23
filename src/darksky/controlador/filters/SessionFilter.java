package darksky.controlador.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import darksky.controlador.helpers.SessionHandler;

public class SessionFilter implements Filter {

    public SessionFilter() {
    }

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		SessionHandler sessionHandler = new SessionHandler((HttpServletRequest) request);
		if (sessionHandler.isInit())
			chain.doFilter(request, response);
		else {
			HttpServletResponse httpResponse = (HttpServletResponse) response;
			httpResponse.sendRedirect("/DarkSky/vista/pagina-principal.jsp");
		}
	}

	public void init(FilterConfig fConfig) throws ServletException {
	}

}
