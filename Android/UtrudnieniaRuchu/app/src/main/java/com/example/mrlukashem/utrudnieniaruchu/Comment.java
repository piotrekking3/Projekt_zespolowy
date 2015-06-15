package com.example.mrlukashem.utrudnieniaruchu;

/**
 * Created by mrlukashem on 19.05.15.
 */
public class Comment {
    private String author;
    private String date;
    private String content;

    private Comment() {}

    public static Comment getInstance(String __author, String __date, String __content) {
        Comment _coment = new Comment();
        _coment.setAuthor(__author);
        _coment.setDate(__date);
        _coment.setContent(__content);

        return _coment;
    }

    public void setAuthor(String __author) {
        author = __author;
    }

    public void setDate(String __date) {
        date = __date;
    }

    public void setContent(String __content) {
        content = __content;
    }

    public String getAuthor() {
        return author;
    }

    public String getDate() {
        return date;
    }

    public String getContent() {
        return content;
    }
}
