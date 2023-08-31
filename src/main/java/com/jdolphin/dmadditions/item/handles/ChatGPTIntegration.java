package com.jdolphin.dmadditions.item.handles;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class ChatGPTIntegration {
	private static final String API_KEY = "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";
	private static final String API_URL = "https://api.openai.com/v1/chat/completions";

	public static String askQuestion(String question) {
		try {
			String encodedQuestion = URLEncoder.encode(question, "UTF-8");

			URL url = new URL(API_URL);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Authorization", "Bearer " + API_KEY);
			connection.setRequestProperty("Content-Type", "application/json");
			connection.setDoOutput(true);

			String data = "{\"messages\": [{\"role\": \"system\", \"content\": \"You are a helpful assistant.\"}, {\"role\": \"user\", \"content\": \"" + encodedQuestion + "\"}]}";

			connection.getOutputStream().write(data.getBytes());

			BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			StringBuilder response = new StringBuilder();
			String line;
			while ((line = reader.readLine()) != null) {
				response.append(line);
			}
			reader.close();

			return response.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "Error getting response.";
	}

	public static void main(String[] args) {
		// Example usage
		String question = "Tell me about Minecraft.";
		String answer = askQuestion(question);
		System.out.println("Answer: " + answer);
	}
}
