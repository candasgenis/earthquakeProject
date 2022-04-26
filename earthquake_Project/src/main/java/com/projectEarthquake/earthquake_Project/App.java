package com.projectEarthquake.earthquake_Project;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import com.google.gson.Gson;



public class App 
{
	public static void main(String[] args) throws IOException, InterruptedException {
		
		while (true) {
			Scanner myScanner = new Scanner(System.in);  
			System.out.println("Enter the Country: ");
		    String place = myScanner.nextLine().trim().toLowerCase();
		    String place_fitted = place.substring(0, 1).toUpperCase() + place.substring(1);
		    System.out.println("Country is: " + place_fitted);
		    
		    System.out.println("Enter the Days: ");
		    int day = myScanner.nextInt();  
		    System.out.println("Days are: " + day);
		    
		    ArrayList<HashMap<String, String>> return_of_getearthquake = getEarthquake(place_fitted, day);
		    
		    if (return_of_getearthquake == null) {
		    	System.out.println("Something went wrong. (Check the days entered again)");
			} else if(return_of_getearthquake.isEmpty()){
				System.out.println("Something went wrong. (You either entered an unknown place or there is no earthquakes were recorded past "+ day + " day(s))");
			}else {
				for(HashMap<String, String> Object: return_of_getearthquake) {
					System.out.println(Object);
					System.out.println();
				}
			}
		}		
	}
	
	public static ArrayList<HashMap<String, String>> getEarthquake(String place, int day) throws IOException, InterruptedException {
		
		ArrayList<HashMap<String, String>> eq_info_arraylist = new ArrayList<HashMap<String, String>>();

		String region = "";
		String trimmed_region = "";
		String country = "";
		String trimmed_country = "";
		Double magnitude = 0.0;
		String magnitude_string = "";
		String country_fitted = "";
		
		try {
			
			String starttime = LocalDate.now().minusDays(day).toString();
			String url = "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&starttime=" + starttime;
			
			HttpClient client = HttpClient.newHttpClient();
			
			HttpRequest request = HttpRequest.newBuilder()
					  .uri(new URI(url))
					  .GET()
					  .build();
			
			HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
			
			if (response.statusCode() == 400) {
				return null;
			}

			Gson gson = new Gson();
			
			Fields userFromJson = gson.fromJson(response.body(), Fields.class);

			for(Feature feature:userFromJson.getFeatures()){
				HashMap<String, String> element_attributes = new HashMap<String, String>();
				
				String place_string = feature.getProperties().getPlace();
		        
		        if (place_string != null) {
		        	if (place_string.contains(",")) {
		        		int comma_index = place_string.indexOf(",");
			        
			        	region = place_string.substring(0, comma_index);
			        	trimmed_region = region.trim();
			        	
			        	country = place_string.substring(comma_index + 1);
			        	trimmed_country = country.trim();
			        	trimmed_country = trimmed_country.toLowerCase();
			        	
			        	country_fitted = trimmed_country.substring(0, 1).toUpperCase() + trimmed_country.substring(1);
			        	//System.out.println(country_fitted);
			        	
			        	
			        	if (country_fitted.equals(place)) {
			        		
			        		element_attributes.put("Country or State", country_fitted);
			        		element_attributes.put("Place of the Earthquake", trimmed_region);
			        		
			        		Timestamp ts=new Timestamp(feature.getProperties().getTime());
					        ts.toLocalDateTime().toLocalDate();
					        element_attributes.put("Date and Time", ts.toString());
					        
					        magnitude = feature.getProperties().getMag();
			                magnitude_string = String.valueOf(magnitude);
			                element_attributes.put("Magnitude", magnitude_string);
			                
			                eq_info_arraylist.add(element_attributes);
						}
			        	
					}else {
						country_fitted = place_string;
						if (country_fitted.equals(place)) {
			        		
			        		element_attributes.put("Country", country_fitted);
			        		element_attributes.put("Place of the Earthquake", trimmed_region);
			        		
			        		
			        		Timestamp ts=new Timestamp(feature.getProperties().getTime());
					        ts.toLocalDateTime().toLocalDate();
					        
					        element_attributes.put("Date and Time (UTC)", ts.toString().trim());
					        
					        magnitude = feature.getProperties().getMag();
			                magnitude_string = String.valueOf(magnitude);
			                element_attributes.put("Magnitude", magnitude_string);
			                eq_info_arraylist.add(element_attributes);
						}
						
						
					}
		        	
				}
    
			}

		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return eq_info_arraylist;
	}
}
