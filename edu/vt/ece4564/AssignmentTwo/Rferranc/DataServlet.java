package edu.vt.ece4564.AssignmentTwo.Rferranc;


import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;

/*
 * Data server receives and keeps data from the app 
 * and gets pulled from the graph in main
 */
public class DataServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	String[][] humidity;
	String[][] temp;
	ArrayList<SensorData> sensorDataList = new ArrayList<SensorData>();
	ArrayList<String> postList = new ArrayList<String>();
	
	/*
	 * (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.setContentType("text/plain");
		if(req.toString().contains("GET /data?tqrt=scriptInjection&tqx=reqId%3A0&tq=chart")) {
			resp.getWriter().write(getChartData());
		} else {
			resp.getWriter().write(getJsonData());
		}
		
		
	} 
	
	/*
	 * Recieve data from app and process it
	 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
	        throws ServletException, IOException
	    {
			SensorData temp = new SensorData(req.getParameter("measuredfor"));
	        temp.setTemp(Integer.parseInt(req.getParameter("temp")));
	        temp.setHumidity(Integer.parseInt(req.getParameter("humi")));
	        temp.setTime(Long.parseLong(req.getParameter("time")));
	        temp.setLight(Integer.parseInt(req.getParameter("light")));
	        // Caps the list at 1000 to avoid crashing
	        if(sensorDataList.size() > 1000) {
	        	sensorDataList.remove(0);
	        }
	        sensorDataList.add(temp);
	    }
	
	/*
	 * Turns data array into chart data format and writes it to webpage
	 */
	private String getChartData() {
		String temp = "google.visualization.Query.setResponse( {'version':'0.6','reqId':'0','status':'ok', 'table':{";
		temp +="cols: [{id:'time', label:'Time', type:'date'}, {id:'temp', label:'Temperature (F)', type:'number'}, {id:'humi', label:'Humidity (%)', type:'number'}," +
				"{id:'light', label:'Light (Lx/10)', type:'number'},\n"; 
		temp += "], rows: [";
		Object[] dataList = sensorDataList.toArray();
		for(int i = 0;i < dataList.length; i++) {
			SensorData s = (SensorData) dataList[i];
			temp += "{c:[{v:" + "new Date(" + Long.toString(s.getTime()) + ")}, {v:"
					+ Integer.toString(s.getTemp()) + "}, {v:" +
					Integer.toString(s.getHumidity()) + "}, {v:" 
					+ Integer.toString(s.getLight()/10)+ "}]},";
		}
		if(temp.endsWith(",")) {
			temp = temp.substring(0, temp.length()-2);
		}
		temp += "}]}});";
		return temp;
	}
	
	/*
	 * Returns Json array of the data points, done for the api
	 */
	private String getJsonData() {
		String temp = "{\"datapoints\":[";
		Object[] dataList = sensorDataList.toArray();
		for(int i = 0;i < dataList.length; i++) {
			SensorData s = (SensorData) dataList[i];
			temp += "{\"time\":\"" + Long.toString(s.getTime()) + "\", \"temperature\":\""
					+ Integer.toString(s.getTemp()) + "\", \"humidity\":\"" +
					Integer.toString(s.getHumidity()) + "\", \"light\":\"" 
					+ Integer.toString(s.getLight())+ "\"},";
		}
		if(temp.endsWith(",")) {
			temp = temp.substring(0, temp.length()-2);
		}
		temp += "}]};";
		return temp;
	}
}
