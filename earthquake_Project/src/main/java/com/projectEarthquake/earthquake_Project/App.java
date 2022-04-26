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
import com.neovisionaries.i18n.CountryCode;

import org.json.*;
//import com.fasterxml.jackson.dataformat.xml.*;



public class App 
{	
	
	public static void main(String[] args) throws IOException, InterruptedException {
		
		
		while (true) {
			Scanner myScanner = new Scanner(System.in);  
			System.out.println("Enter the Country: ");
		    String place = myScanner.nextLine().trim().toLowerCase();
		    //String place_fitted = place.substring(0, 1).toUpperCase() + place.substring(1);
		    //System.out.println("Given Country: " + place_fitted);
		 // stores each characters to a char array
		    
		    place = initialsToUpperCase(place);
		    System.out.println(place);
		    System.out.println("Enter the Days: ");
		    int day = myScanner.nextInt();  
		    System.out.println("Given Day(s): " + day);
		    System.out.println("Please wait. The process may take a while.");
		    
		    ArrayList<HashMap<String, String>> return_of_getearthquake = getEarthquake(place, day);
		    
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
		ArrayList<String> us_states = new ArrayList<String>(); //I'm sorry for this solution
		us_states.add("ıd");
		us_states.add("hı");
		us_states.add("ga");
		us_states.add("fl");
		us_states.add("dc");
		us_states.add("de");
		us_states.add("ct");
		us_states.add("co");
		us_states.add("ca");
		us_states.add("ar");
		us_states.add("az");
		us_states.add("ak");
		us_states.add("al");
		us_states.add("alabama");
		us_states.add("alaska");
		us_states.add("arizona");
		us_states.add("arkansas");
		us_states.add("california");
		us_states.add("colorado");
		us_states.add("connecticut");
		us_states.add("delaware");
		us_states.add("district of columbia");
		us_states.add("florida");
		us_states.add("georgia");
		us_states.add("hawaii");
		us_states.add("ıdaho");
		us_states.add("ıllinois");
		us_states.add("ındiana");
		us_states.add("ıowa");
		us_states.add("kansas");
		us_states.add("kentucky");
		us_states.add("louisiana");
		us_states.add("maine");
		us_states.add("maryland");
		us_states.add("massachusetts");
		us_states.add("michigan");
		us_states.add("minnesota");
		us_states.add("mississippi");
		us_states.add("missouri");
		us_states.add("montana");
		us_states.add("nebraska");
		us_states.add("nevada");
		us_states.add("new hampshire");
		us_states.add("new jersey");
		us_states.add("new mexico");
		us_states.add("new york");
		us_states.add("north carolina");
		us_states.add("north dakota");
		us_states.add("ohio");
		us_states.add("oklahoma");
		us_states.add("oregon");
		us_states.add("pennsylvania");
		us_states.add("rhode ısland");
		us_states.add("south carolina");
		us_states.add("south dakota");
		us_states.add("tennessee");
		us_states.add("texas");
		us_states.add("u.s. virgin ıslands");
		us_states.add("utah");
		us_states.add("vermont");
		us_states.add("virginia");
		us_states.add("washington");
		us_states.add("west virginia");
		us_states.add("wisconsin");
		us_states.add("wyoming");
		us_states.add("ıl");
		us_states.add("ın");
		us_states.add("ıa");
		us_states.add("ks");
		us_states.add("ky");
		us_states.add("la");
		us_states.add("me");
		us_states.add("md");
		us_states.add("ma");
		us_states.add("mı");
		us_states.add("mn");
		us_states.add("ms");
		us_states.add("mo");
		us_states.add("mt");
		us_states.add("ne");
		us_states.add("nv");
		us_states.add("nh");
		us_states.add("nj");
		us_states.add("nm");
		us_states.add("ny");
		us_states.add("nc");
		us_states.add("nd");
		us_states.add("oh");
		us_states.add("ok");
		us_states.add("or");
		us_states.add("pa");
		us_states.add("rı");
		us_states.add("sc");
		us_states.add("sd");
		us_states.add("tn");
		us_states.add("tx");
		us_states.add("vı");
		us_states.add("ut");
		us_states.add("vt");
		us_states.add("va");
		us_states.add("wa");
		us_states.add("wv");
		us_states.add("wı");
		us_states.add("wy");
		
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
			Gson gson = new Gson();
			country_code =  CountryCode.findByName(place).get(0).name();
			System.out.println(country_code);
			String starttime = LocalDate.now().minusDays(day).toString();
			HttpClient client = HttpClient.newHttpClient();
			
			/*url3 = "http://api.geonames.org/countryInfo?lang=en&country=" + country_code.trim() + "&username=candasgenis1";
			//System.out.println(url3);
			HttpRequest request3 = HttpRequest.newBuilder()
					  .uri(new URI(url3))
					  .GET()
					  .build();
			HttpResponse<String> response3 = client.send(request3, HttpResponse.BodyHandlers.ofString());
			JSONObject json = XML.toJSONObject(response3.body());   
	        String jsonString = json.toString(4);   
			CountryMain country_info = gson.fromJson(jsonString, CountryMain.class);
			
			System.out.println(country_info.getGeonames().getCountry().getEast());
			System.out.println(country_info.getGeonames().getCountry().getWest());
			System.out.println(country_info.getGeonames().getCountry().getNorth());
			System.out.println(country_info.getGeonames().getCountry().getSouth());*/
			url1 = "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&starttime=" + starttime;
			
			HttpRequest request1 = HttpRequest.newBuilder()
					  .uri(new URI(url1))
					  .GET()
					  .build();
			
			HttpResponse<String> response1 = client.send(request1, HttpResponse.BodyHandlers.ofString());
			
			if (response1.statusCode() == 400) {
				return null;
			}

			
			
			Fields userFromJson = gson.fromJson(response1.body(), Fields.class);

			for(Feature feature:userFromJson.getFeatures()){
				HashMap<String, String> element_attributes = new HashMap<String, String>();
				//List<Double> coordinates = feature.getGeometry().getCoordinates();
				//System.out.println("-------------------------------------------");
				String place_string = feature.getProperties().getPlace();
				//System.out.println(place_string);
				
				if (place_string != null) {
		        	if (place_string.contains(",")) {
		        		int comma_index = place_string.indexOf(",");
		        		
		        		country = place_string.substring(comma_index + 1);
			        	trimmed_country = country.trim();
			        	trimmed_country = trimmed_country.toLowerCase();
			        	System.out.println("Trimmed Country: " + trimmed_country);
			        	if (us_states.contains(trimmed_country)) {
							trimmed_country = "united states";
						}
			        	
			        	
			        	country_fitted = initialsToUpperCase(trimmed_country);
			        	System.out.println("Country Fitted: " + country_fitted);
			        	region = place_string.substring(0, comma_index);
			        	trimmed_region = region.trim();
		        	}else {
		        		country_fitted = place_string;
		        	}
				}
				

				
				
				//lng = String.valueOf(coordinates.get(0));
				//lat = String.valueOf(coordinates.get(1));
				//url2 = "http://api.geonames.org/countryCode?lat=" + lat + "&lng=" + lng + "&username=candasgenis1";
				//System.out.println("LONGTITUDE: " + lng);
				//System.out.println("LATITUDE: "+ lat);
				
				/*HttpRequest request2 = HttpRequest.newBuilder()
						  .uri(new URI(url2))
						  .GET()
						  .build();
				
				HttpResponse<String> response2 = client.send(request2, HttpResponse.BodyHandlers.ofString());
				country_code = response2.body().trim();*/
				//System.out.println(country_code); 
				//if (!country_code.equals("ERR:15:no country code found")) {
					/*url3 = "http://api.geonames.org/countryInfo?lang=en&country=" + country_code + "&username=candasgenis1";
					//System.out.println(url3);
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
							//System.out.println(country_name);
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
							//System.out.println(jsonString);
						}
					} catch (Exception e) {
						continue;
					}*/
					
					
					
				//} 
					if (country_fitted.equals(place)) {
		        		
						element_attributes.put("Country", country_fitted);
						//System.out.println(country_fitted);
		        		element_attributes.put("Place of the Earthquake", trimmed_region);
		        		
		        		Timestamp ts=new Timestamp(feature.getProperties().getTime());
				        ts.toLocalDateTime().toLocalDate();
				        element_attributes.put("Date and Time(UTC)", ts.toString());
				        
				        magnitude = feature.getProperties().getMag();
		                magnitude_string = String.valueOf(magnitude);
		                element_attributes.put("Magnitude", magnitude_string);
		                
		                eq_info_arraylist.add(element_attributes);
					}else {
						//System.out.println(country_fitted);
					}
				
				
				
		        
    
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			
		}

		return eq_info_arraylist;
	}
	
	public static String initialsToUpperCase(String string) {
		char[] charArray = string.toCharArray();
	    boolean foundSpace = true;

	    for(int i = 0; i < charArray.length; i++) {

	      // if the array element is a letter
	      if(Character.isLetter(charArray[i])) {

	        // check space is present before the letter
	        if(foundSpace) {

	          // change the letter into uppercase
	          charArray[i] = Character.toUpperCase(charArray[i]);
	          foundSpace = false;
	        }
	      }

	      else {
	        // if the new character is not character
	        foundSpace = true;
	      }
	    }

	    // convert the char array to the string
	    string = String.valueOf(charArray);
	    return string;
	}
}
