package com.util;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Pinger {
	private final static HttpClient httpClient = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_2)
            .build();
	
	public static void pingClient() throws IOException, InterruptedException {
		HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create("https://volthread-front.herokuapp.com/ping"))
                .setHeader("User-Agent", "Java 11 HttpClient Bot")
                .build();
		httpClient.send(request, HttpResponse.BodyHandlers.ofString());

	}
}
