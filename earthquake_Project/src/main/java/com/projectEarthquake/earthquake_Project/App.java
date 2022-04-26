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
		ArrayList<String> us_states = new ArrayList<String>(); // I'm sorry for this solution,
		us_states.add("ıd"); // I had another solution on my commits but that increases the compile time
		us_states.add("hı"); // too much. The reason why I did this is because if the Earthquake happened
		us_states.add("ga"); // in the US, API returns the name of the state not the country. So I figured
		us_states.add("fl"); // it out like this. If API returns the abbreviation or the name of a state
		us_states.add("dc"); // the function checks this arrayList and makes it "United States".
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
