package net.giuli.clevercloud;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class HelloWorldServlet
 */
@WebServlet("/HelloWorldServlet")
public class HelloWorldServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private Properties _properties;
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public HelloWorldServlet() {
		super();
		_properties = System.getProperties();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter writer = response.getWriter();

		writer.println("<html>");
		writer.println("<head><title>Hello World Servlet</title></head>");
		writer.println("<body>");
		writer.println("	<h1>Hello World from a Servlet!</h1>");
		
		for(Map.Entry<Object,Object>  entry : _properties.entrySet()){
			writer.println("<p>" + entry.getKey() + " = "  + entry.getValue() + "</p>");
		}
		writer.println("	<h1>Network properties:</h1>");
		for(Map.Entry<String,Object>  entry : collectNetworkProperties().entrySet()){
			writer.println("<p>" + entry.getKey() + " = "  + entry.getValue() + "</p>");
		}
		
		writer.println("<body>");
		writer.println("</html>");

		writer.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	private Map<String,Object> collectNetworkProperties() throws SocketException {
		
		Map<String,Object> result = new HashMap<String,Object>();
		
		Enumeration<NetworkInterface> nets = NetworkInterface.getNetworkInterfaces();
		
		ArrayList<NetworkInterface> list = Collections.list(nets);
		for (int i = 0; i < list.size(); i++) {
			NetworkInterface netint = list.get(i);
			result.put("Name " + i, netint.getName());
			Enumeration<InetAddress> inetAddresses = netint.getInetAddresses();
			String addresses = "";
			for (InetAddress inetAddress : Collections.list(inetAddresses)) {
				if(inetAddress instanceof Inet4Address) {
					addresses += inetAddress.toString() + " ";
				}
			}
			result.put("InetAddresses " + i, addresses);
		}
		
		return result;
}

}
