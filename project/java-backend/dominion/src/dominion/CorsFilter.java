package dominion;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

/* Controleer web.xml voor de Cors Filter...

  Verplicht: Staat onder "filter-mapping" de waarde van "url-pattern " op "/Server"?
  
  Optioneel: Voeg de init-param tags toe aan config om te configureren welke 
  			 domeinen toegang hebben. (Voor debug is het * of geen init-param.)
   			OPGELET:
   			init-param "domains" ontbreekt	-> All domains allowed for CORS
   			init-param "domains" waarde "*"	->  All domains allowed for CORS
   			init-param "domains" leeg 		-> No CORS
   			init-param "domains" comma separated domains -> Listed domains allowed...
   
  <filter>
    <display-name>CorsFilter</display-name>
    <filter-name>CorsFilter</filter-name>
    <filter-class>dominion.CorsFilter</filter-class>
    <init-param>
    	<param-name>domains</param-name>
    	<param-value>*</param-value>
    </init-param>
  </filter>
  <filter-mapping>
    <filter-name>CorsFilter</filter-name>
    <url-pattern>/Server</url-pattern>
  </filter-mapping> 

*/

public class CorsFilter implements Filter {
	// CHANGE TO FALSE IN RELEASE!!!!!
	private final static boolean DEFAULT_CORS = false;

	private String[] domains;

	public CorsFilter() {
	}

	public void destroy() {
	}

	private String doFilterOrigin(HttpServletRequest request) {
		String headerValue = request.getHeader("origin");
		if (this.domains.length == 1) {
			if (this.domains[0].equals(""))
				return null;
			else if (this.domains[0].equals("*"))
				return "*";
			else if (headerValue.startsWith(this.domains[0]))
				return headerValue;
			return null;
		} else {
			for (int i = 1; i < domains.length; i++)
				if (headerValue.startsWith(domains[i]))
					return headerValue;
			return null;
		}
	}

	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		// ServerUtil.debugPrintRequest(request);

		String origin = doFilterOrigin(request);
		if (origin != null) {
			response.addHeader("Access-Control-Allow-Origin", origin);
			response.addHeader("Access-Control-Allow-Methods", "GET,POST");
			response.addHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
			if (request.getMethod().equals("OPTIONS")) {
				response.setStatus(HttpServletResponse.SC_OK);
				return;
			}
		}
		chain.doFilter(request, response);
	}

	public void init(FilterConfig config) throws ServletException {
		String param = config.getInitParameter("domains");
		if (param != null) {
			this.domains = param.split(",");
			if (this.domains[0].equals("*"))
				System.out.println("\n    >>> WARNING: CORS is enabled by default for all!    <<<");
			System.out.println("    >>>          See init-param in web.xml.             <<<\n");
		} else {
			System.out.println("\n    >>> WARNING: CORS filter init-param is missing!    <<<\n");
			if (DEFAULT_CORS) {
				System.out.println("\n    >>> WARNING: CORS is enabled by default!    <<<");
				System.out.println("    >>> Please disable or configure in release. <<<\n");
				this.domains = null;
			} else {
				this.domains = new String[] { "" };
			}
		}
	}
}