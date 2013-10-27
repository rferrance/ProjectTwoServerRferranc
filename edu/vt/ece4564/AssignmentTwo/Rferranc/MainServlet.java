package edu.vt.ece4564.AssignmentTwo.Rferranc;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class MainServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static String address = "127.0.0.1";
	
	/*
	 * Main for main serverlet, starts the server and runs the graph
	 */
	public static void main(String args[]) throws Exception {
		Server server = new Server(8080);
		WebAppContext context = new WebAppContext();
		context.setWar("war");
		context.setContextPath("/");
		server.setHandler(context);
		// Get the IP address of the server
		address = InetAddress.getLocalHost().toString();
		address = address.substring(address.indexOf('/') + 1);
		try {
			server.start();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			server.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		resp.setContentType("text/html");
		resp.getWriter().write(buildHtml());
	} 
	
	/*
	 * Builds the HTML for the graph to be displayed
	 */
	private String buildHtml() {
		StringBuilder htmlString = new StringBuilder();
		htmlString.append("<html>\n");
		htmlString.append("  <head>\n");
		htmlString.append("    <script type=\"text/javascript\" src=\"https://www.google.com/jsapi\"></script>\n");
		htmlString.append("    <script type=\"text/javascript\">\n");
		htmlString.append("      google.load(\"visualization\", \"1\", {packages:[\"corechart\"]});\n");
		htmlString.append("      google.setOnLoadCallback(drawChart);\n");
		htmlString.append("		 var query; \n");
		htmlString.append("		 var data; \n");
		htmlString.append("		 var chart; \n");
		htmlString.append("		 var options; \n");
		htmlString.append("		 function handleQueryResponse(response) { \n");
		htmlString.append("		 	if (response.isError()) { \n");
		htmlString.append("		 		alert('Error in query: ' + response.getMessage() + ' ' + response.getDetailedMessage()); \n");
		htmlString.append("		 		return; \n");
		htmlString.append("		 	}\n");
		htmlString.append("		 	else{ \n");
		htmlString.append("		 		chart.draw(response.getDataTable(),options); \n");
		htmlString.append("		 	} \n");
		htmlString.append("		 } \n");
		htmlString.append("		 function drawChart(){ \n");
		htmlString.append("		 	var data = new google.visualization.DataTable( { \n");
		htmlString.append("		 	cols: [{id:'time', label:'Time', type:'date'}, {id:'temp', label:'Temperature (F)', type:'number'}, {id:'humi', label:'Humidity (%)', type:'number'},\n"); 
		htmlString.append("		 		{id:'light', label:'Light (Lx/10)', type:'number'},], rows: [] }, 0.6); \n");
		htmlString.append("		 	options = { title:'Humidity, Temperature, and Light Intensity over time', 'vAxis.title':'Humidity (%)', 'hAxis.title':'Time'}; \n");
		htmlString.append("		 	chart = new google.visualization.LineChart(document.getElementById('chart_div')); \n");
		htmlString.append("		 	chart.draw(data, options); \n");
		htmlString.append("		 	query = new google.visualization.Query('http://");
		htmlString.append(address + ":8080/data?tqrt=scriptInjection&tqx=reqId:0'); \n");
		htmlString.append("		 	query.setQuery('chart'); \n");
		htmlString.append("		 	query.setRefreshInterval(5); \n");
		htmlString.append("		 	query.send(handleQueryResponse); \n");
		htmlString.append("		 } \n");
		htmlString.append("    </script>\n");
		htmlString.append("  </head>\n");
		htmlString.append("  <body>\n");
		htmlString.append("    <div id=\"chart_div\" style=\"width: 900px; height: 500px;\"></div>\n");
		htmlString.append("  </body>\n");
		htmlString.append("</html>\n");
		return htmlString.toString();
	}
	
	/*
	 * Method to get data from other part of servlet
	 */
	private String getData() throws IOException {
		URL url = new URL("localhost:8080/data");
		URLConnection con = url.openConnection();
		Pattern p = Pattern.compile("text/html;\\s+charset=([^\\s]+)\\s*");
		Matcher m = p.matcher(con.getContentType());
		/* If Content-Type doesn't match this pre-conception, choose default and 
		 * hope for the best. */
		String charset = m.matches() ? m.group(1) : "ISO-8859-1";
		Reader r = new InputStreamReader(con.getInputStream(), charset);
		StringBuilder buf = new StringBuilder();
		while (true) {
		  int ch = r.read();
		  if (ch < 0)
		    break;
		  buf.append((char) ch);
		}
		String str = buf.toString();
		return str;
	}
}
