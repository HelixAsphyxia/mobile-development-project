package com.example.finalproject.choosers;

public class OrderedQuestionChooser extends QuestionChooser {
    int currentQuestion;

    public OrderedQuestionChooser(int size) {
        super(size);

        currentQuestion = 0;
    }

    @Override
    public int nextQuestion() {
        return (currentQuestion++) % size;
    }

}
