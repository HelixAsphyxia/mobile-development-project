package com.example.finalproject.choosers;

public class QuestionChooserFactory {
    public static QuestionChooser create(String type, int size) {
        switch (type) {
            case "Random":
                return new RandomQuestionChooser(size);
            case "First to last":
                return new OrderedQuestionChooser(size);
        }

        throw new RuntimeException("Could not create class for QuestionChooser");
    }
}
