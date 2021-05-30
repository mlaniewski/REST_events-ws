package pl.edu.pb.wi.model.db;

import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import pl.edu.pb.wi.model.Link;

import javax.xml.datatype.XMLGregorianCalendar;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "Event")
public class Event {

    public enum Type {
        SPORT,
        CULTURAL;

        public String value() {
            return name();
        }

        public static Type fromValue(String v) {
            return valueOf(v);
        }
    }
    @Id
    @BsonProperty(value = "_id")
    private ObjectId _id;
    private String econst;
    private String name;
    private Type type;
    private XMLGregorianCalendar date;
    private int week;
    private int month;
    private int year;
    private String description;
    @Transient
    private List<Link> links = new ArrayList<>();

    public Event() {
    }

    public Event(String econst, String name, Type type, XMLGregorianCalendar date, int week, int month, int year, String description) {
        this._id = new ObjectId();
        this.econst = econst;
        this.name = name;
        this.type = type;
        this.date = date;
        this.week = week;
        this.month = month;
        this.year = year;
        this.description = description;
    }

    public ObjectId get_id() {
        return _id;
    }

    public void set_id(ObjectId _id) {
        this._id = _id;
    }

    public String getEconst() {
        return econst;
    }

    public void setEconst(String econst) {
        this.econst = econst;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public XMLGregorianCalendar getDate() {
        return date;
    }

    public void setDate(XMLGregorianCalendar date) {
        this.date = date;
    }

    public int getWeek() {
        return week;
    }

    public void setWeek(int week) {
        this.week = week;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void addLink(String uri, String rel) {
        links.add(new Link(uri, rel));
    }

    public List<Link> getLinks() {
        return links;
    }

    public void setLinks(List<Link> links) {
        this.links = links;
    }
}
