package com.example.finalproject.choosers;

public class RandomQuestionChooser extends QuestionChooser {

    public RandomQuestionChooser(int size) {
        super(size);
    }

    @Override
    public int nextQuestion() {
        return (int) (Math.random() * size);
    }

}
