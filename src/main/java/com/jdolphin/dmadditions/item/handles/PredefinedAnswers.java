package com.jdolphin.dmadditions.item.handles;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.List;

public class PredefinedAnswers {
	private static final String CONFIG_FILE_PATH = "predefined_answers.json";

	private static List<QuestionAnswerPair> predefinedPairs;

	static {
		try {
			Gson gson = new Gson();
			Type listType = new TypeToken<List<QuestionAnswerPair>>() {}.getType();
			FileReader reader = new FileReader(CONFIG_FILE_PATH);
			predefinedPairs = gson.fromJson(reader, listType);
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String getAnswerForQuestion(String question) {
		for (QuestionAnswerPair pair : predefinedPairs) {
			if (pair.getQuestion().equalsIgnoreCase(question)) {
				return pair.getAnswer();
			}
		}
		return "I'm sorry, I don't have an answer for that question.";
	}

	private static class QuestionAnswerPair {
		private String question;
		private String answer;

		public String getQuestion() {
			return question;
		}

		public String getAnswer() {
			return answer;
		}
	}
}
