package net.eoutech.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class EuHttpGet {

	public static String reqHttp(String url) {
		String rsp = "fail";
		try {
			HttpURLConnection urlConnection = (HttpURLConnection) (new URL(url))
					.openConnection();
			urlConnection.setDoInput(true);
			urlConnection.setDoOutput(true);
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					urlConnection.getInputStream()));

			while ((rsp = reader.readLine()) != null) {
				return rsp;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rsp;
	}
}
