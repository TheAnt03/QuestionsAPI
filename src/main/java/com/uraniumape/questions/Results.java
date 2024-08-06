package com.uraniumape.questions;

import java.util.LinkedHashMap;

public class Results {
    private final LinkedHashMap<String, Result> questionResults;

    public Results(LinkedHashMap<String, Question> questions) {
        questionResults = new LinkedHashMap<>();

        questions.forEach((key, value) -> {
            questionResults.put(key, new Result(value.getQuestion(), value.getAnswer()));
        });
    }

    public Result get(String key) {
        return questionResults.get(key);
    }

    public Result get(int index) {
        String key = (String) questionResults.keySet().toArray()[index];
        return questionResults.get(key);
    }
}
