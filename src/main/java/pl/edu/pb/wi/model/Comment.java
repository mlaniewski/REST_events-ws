package pl.edu.pb.wi.model;

import java.util.Date;

public class Comment {
    private String content;
    private Date date;

    public Comment(String content) {
        this.content = content;
        this.date = new Date();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "content='" + content + '\'' +
                ", date=" + date +
                '}';
    }
}
