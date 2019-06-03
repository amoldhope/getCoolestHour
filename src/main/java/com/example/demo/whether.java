package com.example.demo;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RestController
public class whether {

	
	
	
	@GetMapping(value="/{citycode}")
	public String getWhether(@PathVariable("citycode") int citycode ) throws ParseException
	{
		 String minTempTime="";
		  String time="";
		String output="";
		String obj="";
		JSONObject jobj = null;
		System.out.println(citycode);
		//Method body
		//In the method body you should make a request to the openweather server with an api key which you can get by registering in the website. You can achieve this with Unirest library (it's the easiest way)
		
	 try {
		 
		// URL url=new URL("http://api.openweathermap.org/data/2.5/forecast/hourly?zip="+citycode+",us&appid=7a500e1d9460b369bffce00762e12ddd");
			URL url = new URL("http://api.openweathermap.org/data/2.5/forecast?zip="+citycode+"&appid=d2855228e3404e2bd7a316a4d012fcd9");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");

			if (conn.getResponseCode() != 200) {
				return "City not found";
				
				/*throw new RuntimeException("Failed : HTTP error code : "
						+ conn.getResponseCode());*/
			}

			Scanner sc = new Scanner(url.openStream());

			while(sc.hasNext())

			{

			output+=sc.nextLine();

			}

			
			System.out.println(output);

			sc.close();
			conn.disconnect();
			
			 JSONParser parse = new JSONParser();
			  jobj = (JSONObject)parse.parse(output); 
			  JSONArray arr = (JSONArray)jobj.get("list");
			//  JSONArray arr1=(JSONArray)jobj.get("city");
			  int i=arr.size();
			  double min=500;
			 
			  LocalDate today = LocalDate.now( ZoneId.of( "America/Montreal" ) ) ;
				 // Date today=new Date();
				  String td=today.toString();
				
			  
			  int cnt=0,index=0;
			  while(cnt<8)
			  {
				     
				 
				JSONObject jsonobj_1 = (JSONObject)arr.get(index);
				  
				   JSONObject obje=(JSONObject)jsonobj_1.get("main");
				   time=jsonobj_1.get("dt_txt").toString();
				   
				   if(time.contains(td))
				   {
					   index++;
					   
				   }
				   
				   else
				   {
					   
					   String s=obje.get("temp_min").toString();
						  double d=Double.parseDouble(s);
						  System.out.println(s+" " +time);
						  if(d<min)
						  {
							  
							  min=d;
							   
							   minTempTime=time;
							  
						  }
						  cnt++;
						  index++;
						   
				   }
				    
			  }
			  
			  /*for(int j=0;j<8;j++)
			  {	
				  JSONObject jsonobj_1 = (JSONObject)arr.get(j);
				  
				   JSONObject obje=(JSONObject)jsonobj_1.get("main");
				  String s=obje.get("temp_min").toString();
				  double d=Double.parseDouble(s);
				  if(d<min)
				  {
					  
					  min=d;
					   time=jsonobj_1.get("dt_txt").toString();
					   System.out.println("yes");
					  
				  }
				  System.out.println(s);
				  
				  
			  }//end for loop*/
			  System.out.println(minTempTime);
			  System.out.println(td);
			  
			  			  
			  
			  
			  
			  

		  } catch (MalformedURLException ex) {

			ex.printStackTrace();

		  } catch (IOException e) {

			e.printStackTrace();

		  }
	return "minimun tempreture for tommoro is onwords "+minTempTime+" for next 3 hours";

		}

	
	
}
