package main.api.response;

import lombok.Data;

@Data
public class CommentAdditionError {
    private boolean result;
    private CommentError errors;

//    public CommentAdditionError(boolean result) {
//        this.result = setResult(result);
//        this.errors = setErrors();
//    }
//
//    public boolean setResult(boolean result) {
//        return result;
//    }
//
//    public ErrorCommentResponse setErrors() {
//        return new ErrorCommentResponse("Текст комментария не задан или слишком короткий");
//    }
//
//    class ErrorCommentResponse {
//        private final String text;
//        public ErrorCommentResponse(String text) {
//            this.text = setText(text);
//        }
//        public String setText(String text) {
//            return text;
//        }
//    }
}