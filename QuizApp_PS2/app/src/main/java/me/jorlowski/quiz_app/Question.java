package me.jorlowski.quiz_app;

public class Question {
    private final int questionId;
    private final boolean trueAnswer;

    public Question(int questionId, boolean trueAnswer) {
        this.questionId = questionId;
        this.trueAnswer = trueAnswer;
    }

    public boolean isTrueAnswer() {
        return trueAnswer;
    }

    public int getQuestionId() {
        return questionId;
    }
}
