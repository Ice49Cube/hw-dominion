
import java.io.*;
import java.util.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
//classes from the jackson library
//import org.codehaus.jackson.JsonParseException;
//import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
//import org.codehaus.jackson.map.SerializationConfig;


/**
 * Servlet implementation class dominion
 */
public class dominion extends HttpServlet {
	private static final long serialVersionUID = 1L;
	//private int count;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public dominion() {
        super();
        // TODO Auto-generated constructor stub
        //count = 0;
        
    }
    
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		System.out.println("inside do get");
		
		try{
			BufferedReader reader = request.getReader();
			String line; 
			while((line = reader.readLine()) != null)
				System.out.println("Print line: " + line);
		} 
		catch (Exception e) 
		{
			
		}
		
		
		/*ArrayList<String> parameterNames = new ArrayList<String>();
		Enumeration enumeration = request.getParameterNames();
		while(enumeration.hasMoreElements()){
			String parameterName = (String)enumeration.nextElement();
			System.out.println(parameterName);
		}*/

		
		// TODO Auto-generated method stub

		
		//String message = "{\"counts\": " + count + "}";
		//count++;
		
		
		/*
		//converting a java object to JSON and response it to user
		User userlist = new User();
		ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(userlist);
        
		response.setContentType("text/json");
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.getWriter().append(json);
		
		//System.out.println(request.getContentLength());
		
		
		try{
			System.out.println(request.getParameterValues(""));
			System.out.println(request.getHeaderNames());
			System.out.println(request.getQueryString());
			System.out.println(request.getParameterNames());
			
			
			BufferedReader reader = request.getReader();
			String line; 
			while((line = reader.readLine()) != null)
				System.out.println(line);
		} catch (Exception e) {		}*/
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		doGet(request, response);
		
    }
}
