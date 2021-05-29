package pl.edu.pb.wi.model;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@XmlRootElement
public class Message {
    private long id;
    private String message;
    private Date created;
    private String author;
    private List<Comment> comments = new ArrayList<>();
    private List<Link> links = new ArrayList<>();

    public Message() { }

    public Message(long id, String message, String author, Comment... comments) {
        this.id = id;
        this.message = message;
        this.created = new Date();
        this.author = author;
        this.comments = Arrays.asList(comments);

    }

    public long getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    public Date getCreated() {
        return created;
    }

    public String getAuthor() {
        return author;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public List<Link> getLinks() {
        return links;
    }

    public void setLinks(List<Link> links) {
        this.links = links;
    }

    public void addLink(String uri, String rel) {
        links.add(new Link(uri, rel));
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", message='" + message + '\'' +
                ", created=" + created +
                ", author='" + author + '\'' +
                '}';
    }
}
