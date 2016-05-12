package dominion;

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;

import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.*;

import dominion.game.*;
import dominion.routing.*;

/**
 * Servlet implementation class Server
 */
public final class Server extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private Routing routing;
	private GameEngine engine;

	/**
	 * @throws ClassNotFoundException
	 * @throws NullPointerException
	 * @see HttpServlet#HttpServlet()
	 */
	public Server() throws NullPointerException, ClassNotFoundException {
		super();
	}

	/**
	 * @see Servlet#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			// ServerUtil.debugPrintRequest(request);
			// todo: Check server flood...
			if (this.validateRequestFormat(request)) {
				sendResult(routing, response, routing.execute(request.getReader()));
			} else {
				sendResult(routing, response, new ErrorResult(400, new ErrorInfo("Bad Request")) {
				});
			}
		} catch (Exception e) {
			if (routing != null)
				sendResult(routing, response, new ErrorResult(500, new ErrorInfo("Server Error")) {
				});
			else
				throw e;
		}
	}

	private void sendResult(Routing routing, HttpServletResponse response, ResultBase result)
			throws JsonGenerationException, JsonMappingException, IOException {
		response.setStatus(result.getCode());
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");
		routing.writeResult(response.getWriter(), result);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		try {
			this.engine = new GameEngine();
			this.routing = new Routing(this.engine);
		} catch (Exception ex) {
			throw new ServletException(ex);
		}
	}

	/**
	 * Validates an incoming request.
	 * 
	 * @param request
	 *            The incoming request.
	 * @return Returns true if the request was valid, else false.
	 */
	private boolean validateRequestFormat(HttpServletRequest request) {
		if (!request.getMethod().equals("POST"))
			return false;
		if (!request.getContentType().startsWith("application/json"))
			return false;
		return true;
	}
}
