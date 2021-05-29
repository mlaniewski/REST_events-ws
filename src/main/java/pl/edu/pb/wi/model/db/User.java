package pl.edu.pb.wi.model.db;

import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "User")
public class User {
    @Id
    @BsonProperty(value = "_id")
    private ObjectId _id;
    private String uconst;
    private String username;
    private String password;
    private boolean admin;

    public User() {
    }

    public User(String uconst, String username, String password, boolean admin) {
        this._id = new ObjectId();
        this.uconst = uconst;
        this.username = username;
        this.password = password;
        this.admin = admin;
    }

    public ObjectId get_id() {
        return _id;
    }

    public void set_id(ObjectId _id) {
        this._id = _id;
    }

    public String getUconst() {
        return uconst;
    }

    public void setUconst(String uconst) {
        this.uconst = uconst;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }
}
