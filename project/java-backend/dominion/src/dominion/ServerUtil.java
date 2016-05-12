package dominion;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

public class ServerUtil {

	/**
	 * Prints out the request for debug and test purposes. TODO Remove in
	 * release.
	 * 
	 * @param request
	 */
	public static void printRequest(HttpServletRequest request) {
		System.out.println(request.getMethod() + " " + request.getRequestURI() + " " + request.getProtocol());
		@SuppressWarnings("unchecked")
		Enumeration<String> headerNames = request.getHeaderNames();
		while (headerNames.hasMoreElements()) {
			String headerName = headerNames.nextElement();
			String headerValue = request.getHeader(headerName);
			System.out.println(headerName + ": " + headerValue);
		}
	}
}
