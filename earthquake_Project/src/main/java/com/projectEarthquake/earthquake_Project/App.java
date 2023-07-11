package com.projectEarthquake.earthquake_Project;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import com.google.gson.Gson;

public class App {

	public static void main(String[] args) throws IOException, InterruptedException {

		while (true) {
			Scanner myScanner = new Scanner(System.in);

			// Getting the country input
			System.out.println("Enter the Country: ");
			String place = myScanner.nextLine().trim().toLowerCase();

			// Making initials Uppercase characters
			place = initialsToUpperCase(place);
			System.out.println(place);

			// Getting the day input
			System.out.println("Enter the Days: ");
			int day = myScanner.nextInt();
			System.out.println("Given Day(s): " + day);

			System.out.println("Please wait. The process may take a while.");

			// Storing the return value in an ArrayList of a HashMap of a (String,String)
			// type
			ArrayList<HashMap<String, String>> return_of_getearthquake = getEarthquake(place, day);

			// This especially occurs if you crossed the limit of the allowed requests in a
			// certain time
			if (return_of_getearthquake == null) {
				System.out.println("Something went wrong. (Check the days entered again, you may try to minimize it)");
			} else if (return_of_getearthquake.isEmpty()) {
				System.out.println(
						"Something went wrong. (You either entered an unknown country or there is no earthquakes were recorded past "
								+ day + " day(s))");
			} else {
				for (HashMap<String, String> Object : return_of_getearthquake) {
					System.out.println(Object);
					System.out.println();
				}
			}
		}
	}

	// Function to get Earthquake data(Place of the Earthquake, Country, Magnitude,
	// Date and Time)
	// Inputs: country, number of days you want to check back
	public static ArrayList<HashMap<String, String>> getEarthquake(String place, int day)
			throws IOException, InterruptedException {
		ArrayList<String> us_states = new ArrayList<String>();
		ArrayList<HashMap<String, String>> eq_info_arraylist = new ArrayList<HashMap<String, String>>();

		String region = "";
		String trimmed_region = "";
		String country = "";
		String trimmed_country = "";
		Double magnitude = 0.0;
		String magnitude_string = "";
		String country_fitted = "";
		String url1 = "";

		try {
			// Gson instance to work with the JSON response
			Gson gson = new Gson();

			// Substracting the days entered from the present date
			String starttime = LocalDate.now().minusDays(day).toString();

			// Creating client instance
			HttpClient client = HttpClient.newHttpClient();

			// url of the request
			url1 = "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&starttime=" + starttime;

			// Building the http request
			HttpRequest request1 = HttpRequest.newBuilder().uri(new URI(url1)).GET().build();

			// Sending the http request with the built-in HttpClient library
			// This statement returns a string representation of the JSON response
			HttpResponse<String> response1 = client.send(request1, HttpResponse.BodyHandlers.ofString());

			if (response1.statusCode() == 400) {
				return null;
			}

			// Creating an instance from Fields class to reach the desired attributes from
			// JSON response
			Fields userFromJson = gson.fromJson(response1.body(), Fields.class);

			// Iterating over the response to find desired country's information
			for (Feature feature : userFromJson.getFeatures()) {
				// Creating a hashmap to save the Earthquake info
				HashMap<String, String> element_attributes = new HashMap<String, String>();

				// Getting the place info Ex: 152 km WNW of Tiksi, Russia
				String place_string = feature.getProperties().getPlace();

				if (place_string != null) {
					// Every place string have a common format like: "the region, country"
					// So I decided to get the string after the comma to get the country
					if (place_string.contains(",")) {
						int comma_index = place_string.indexOf(",");
						country = place_string.substring(comma_index + 1);
						trimmed_country = country.trim();
						trimmed_country = trimmed_country.toLowerCase();

						// If the string after the comma is a state of the US, country variable changes
						// to "united states"
						if (us_states.contains(trimmed_country)) {
							trimmed_country = "united states";
						}

						country_fitted = initialsToUpperCase(trimmed_country);

						// Getting the region from the backwards of the comma
						region = place_string.substring(0, comma_index);
						trimmed_region = region.trim();
					} else {
						// if string has no comma it takes the entire string
						country_fitted = place_string;
					}
				}
				// If the country on this feature is the same as input
				if (country_fitted.equals(place)) {

					element_attributes.put("Country", country_fitted);
					element_attributes.put("Place of the Earthquake", trimmed_region);

					// Converting the timestamp value into a date and time value and putting it in
					// the element_attributes hashmap
					Timestamp ts = new Timestamp(feature.getProperties().getTime());
					ts.toLocalDateTime().toLocalDate();
					element_attributes.put("Date and Time(UTC)", ts.toString());

					magnitude = feature.getProperties().getMag();
					magnitude_string = String.valueOf(magnitude);
					element_attributes.put("Magnitude", magnitude_string);

					// Putting the hashmap into the Earthquake Info Arraylist
					eq_info_arraylist.add(element_attributes);
				}

			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
		}

		return eq_info_arraylist;
	}

	// Function to make the initials Uppercase of every word in a String
	public static String initialsToUpperCase(String string) {
		char[] charArray = string.toCharArray();
		boolean foundSpace = true;

		for (int i = 0; i < charArray.length; i++) {

			// If the array element is a letter
			if (Character.isLetter(charArray[i])) {

				// Check space is present before the letter
				if (foundSpace) {

					// Change the letter into uppercase
					charArray[i] = Character.toUpperCase(charArray[i]);
					foundSpace = false;
				}
			}

			else {
				// If the new character is not character
				foundSpace = true;
			}
		}

		// Convert the char array to the string
		string = String.valueOf(charArray);
		return string;
	}
}
