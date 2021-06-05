package pl.edu.pb.wi.model.db;

import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.xml.bind.annotation.XmlRootElement;

@Document(collection = "Rating")
@XmlRootElement
public class Rating {
    @Id
    @BsonProperty(value = "_id")
    private ObjectId _id;
    private Double rating;
    private String userId;
    private String eventId;

    public Rating() {
    }

    public Rating(Double rating, String userId, String eventId) {
        this._id = new ObjectId();
        this.rating = rating;
        this.userId = userId;
        this.eventId = eventId;
    }

    public ObjectId get_id() {
        return _id;
    }

    public void set_id(ObjectId _id) {
        this._id = _id;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }
}
