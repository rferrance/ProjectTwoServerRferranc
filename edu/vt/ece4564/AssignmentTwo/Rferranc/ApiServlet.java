package edu.vt.ece4564.AssignmentTwo.Rferranc;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;

public class ApiServlet extends HttpServlet {
	 protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			 throws ServletException, IOException {
		 resp.setContentType("text/plain");
		 resp.getWriter().write("Api\n");
		 resp.getWriter().write("---------------------\n");
		 resp.getWriter().write("Usage:\n");
		 resp.getWriter().write("/data returns a JSON array of the datapoints as follows.\n");
		 resp.getWriter().write("{\"datapoints\":[{\"time\":(long time in milli), \"temperature\": (int temperature in degree C), \"humidity\": (int humidity in per cent), \"light\":(int light in lux)},...]}.\n");
		 resp.getWriter().write("/data?tqrt=scriptInjection&tqx=reqId%3A0&tq=chart returns a Google datatable of the data.\n");
		 resp.getWriter().write("/api gives information on how to use the server.\n");
		 resp.getWriter().write("/ gives a graph of the data.\n");
	 } 
}
