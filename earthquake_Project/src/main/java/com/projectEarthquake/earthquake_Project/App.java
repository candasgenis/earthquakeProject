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
import java.util.List;
import java.util.Scanner;
import com.google.gson.Gson;
import org.json.*;
//import com.fasterxml.jackson.dataformat.xml.*;



public class App 
{
	public static void main(String[] args) throws IOException, InterruptedException {
		
		while (true) {
			Scanner myScanner = new Scanner(System.in);  
			System.out.println("Enter the Country: ");
		    String place = myScanner.nextLine().trim().toLowerCase();
		    String place_fitted = place.substring(0, 1).toUpperCase() + place.substring(1);
		    System.out.println("Given Country: " + place_fitted);
		    
		    System.out.println("Enter the Days: ");
		    int day = myScanner.nextInt();  
		    System.out.println("Given Day(s): " + day);
		    
		    ArrayList<HashMap<String, String>> return_of_getearthquake = getEarthquake(place_fitted, day);
		    
		    if (return_of_getearthquake == null) {
		    	System.out.println("Something went wrong. (Check the days entered again, you may try to minimize it)");
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
		String lng = "";
		String lat = "";
		String url1 = "";
		String url2 = "";
		String url3 = "";
		String country_code = "";
		String country_name = "";
		
		
		try {
			
			String starttime = LocalDate.now().minusDays(day).toString();
			url1 = "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&starttime=" + starttime;
			 
			
			HttpClient client = HttpClient.newHttpClient();
			
			HttpRequest request1 = HttpRequest.newBuilder()
					  .uri(new URI(url1))
					  .GET()
					  .build();
			
			/*HttpRequest request2 = HttpRequest.newBuilder()
					  .uri(new URI(url2))
					  .GET()
					  .build();*/
			
			HttpResponse<String> response1 = client.send(request1, HttpResponse.BodyHandlers.ofString());
			
			if (response1.statusCode() == 400) {
				return null;
			}

			Gson gson = new Gson();
			
			Fields userFromJson = gson.fromJson(response1.body(), Fields.class);

			for(Feature feature:userFromJson.getFeatures()){
				HashMap<String, String> element_attributes = new HashMap<String, String>();
				List<Double> coordinates = feature.getGeometry().getCoordinates();
				System.out.println("-------------------------------------------");
				String place_string = feature.getProperties().getPlace();
				System.out.println(place_string);
				
				if (place_string != null) {
		        	if (place_string.contains(",")) {
		        		int comma_index = place_string.indexOf(",");
		        		
		        		country = place_string.substring(comma_index + 1);
			        	trimmed_country = country.trim();
			        	trimmed_country = trimmed_country.toLowerCase();
			        	
			        	country_fitted = trimmed_country.substring(0, 1).toUpperCase() + trimmed_country.substring(1);
			        	
			        	region = place_string.substring(0, comma_index);
			        	trimmed_region = region.trim();
		        	}else {
		        		country_fitted = place_string;
		        	}
				}
				
				
				
				lng = String.valueOf(coordinates.get(0));
				lat = String.valueOf(coordinates.get(1));
				url2 = "http://api.geonames.org/countryCode?lat=" + lat + "&lng=" + lng + "&username=candasgenis";
				System.out.println("LONGTITUDE: " + lng);
				System.out.println("LATITUDE: "+ lat);
				
				HttpRequest request2 = HttpRequest.newBuilder()
						  .uri(new URI(url2))
						  .GET()
						  .build();
				
				HttpResponse<String> response2 = client.send(request2, HttpResponse.BodyHandlers.ofString());
				country_code = response2.body().trim();
				System.out.println(country_code); 
				if (!country_code.equals("ERR:15:no country code found")) {
					url3 = "http://api.geonames.org/countryInfo?lang=en&country=" + country_code + "&username=candasgenis";
					System.out.println(url3);
					HttpRequest request3 = HttpRequest.newBuilder()
							  .uri(new URI(url3))
							  .GET()
							  .build();
					HttpResponse<String> response3 = client.send(request3, HttpResponse.BodyHandlers.ofString());
					//System.out.println(response3.body());
					JSONObject json = XML.toJSONObject(response3.body());   
			        String jsonString = json.toString(4);   
					CountryMain country_info = gson.fromJson(jsonString, CountryMain.class);
					try {
						if (country_info.getGeonames().getCountry().getCountryName() != null) {
							country_name = country_info.getGeonames().getCountry().getCountryName();
							country_name = country_name.trim();
							country_name = country_name.toLowerCase();
				        	
							country_name = country_name.substring(0, 1).toUpperCase() + country_name.substring(1);
							System.out.println(country_name);
							if (country_name.equals(place)) {
								element_attributes.put("Country", country_name);
								
								element_attributes.put("Place of the Earthquake", trimmed_region);

								Timestamp ts=new Timestamp(feature.getProperties().getTime());
						        ts.toLocalDateTime().toLocalDate();
						        element_attributes.put("Date and Time(UTC)", ts.toString());
						        
						        magnitude = feature.getProperties().getMag();
				                magnitude_string = String.valueOf(magnitude);
				                element_attributes.put("Magnitude", magnitude_string);
				                
				                eq_info_arraylist.add(element_attributes);
						        
							}else if(country_fitted.equals(place)) {
								element_attributes.put("Country", country_fitted);
								
								element_attributes.put("Place of the Earthquake", trimmed_region);

								Timestamp ts=new Timestamp(feature.getProperties().getTime());
						        ts.toLocalDateTime().toLocalDate();
						        element_attributes.put("Date and Time(UTC)", ts.toString());
						        
						        magnitude = feature.getProperties().getMag();
				                magnitude_string = String.valueOf(magnitude);
				                element_attributes.put("Magnitude", magnitude_string);
				                
				                eq_info_arraylist.add(element_attributes);
							}
						}else {
							System.out.println(jsonString);
						}
					} catch (Exception e) {
						continue;
					}
					
					
					
				} else {
					if (country_fitted.equals(place)) {
		        		
						element_attributes.put("Country", country_fitted);
						System.out.println(country_fitted);
		        		element_attributes.put("Place of the Earthquake", trimmed_region);
		        		
		        		Timestamp ts=new Timestamp(feature.getProperties().getTime());
				        ts.toLocalDateTime().toLocalDate();
				        element_attributes.put("Date and Time", ts.toString());
				        
				        magnitude = feature.getProperties().getMag();
		                magnitude_string = String.valueOf(magnitude);
		                element_attributes.put("Magnitude", magnitude_string);
		                
		                eq_info_arraylist.add(element_attributes);
					}else {
						System.out.println(country_fitted);
					}
				}
				
				
		        
		        /*if (place_string != null) {
		        	if (place_string.contains(",")) {
		        		int comma_index = place_string.indexOf(",");
			        
			        	
			        	
			        	
			        	//System.out.println(country_fitted);
			        	
			        	
			        	if (country_fitted.equals(place)) {
			        		
			        		
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
		        	
				}*/
    
			}

		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return eq_info_arraylist;
	}
}
