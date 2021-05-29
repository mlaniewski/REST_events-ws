package pl.edu.pb.wi.service.impl;

import org.springframework.stereotype.Service;
import pl.edu.pb.wi.model.Comment;
import pl.edu.pb.wi.model.Message;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MessageService {
    private Map<Long, Message> messages = new HashMap<>();

    public MessageService() {
        Message m1 = new Message(1L,
                "Pierwsza wiadomosc",
                "Jacek",
                new Comment("Comment Jacek 1"),
                new Comment("Comment Jacek 2"),
                new Comment("Comment Jacek 3"));
        Message m2 = new Message(2L,
                "Druga wiadomosc",
                "Marek",
                new Comment("Comment Marek 1"),
                new Comment("Comment Marek 2"));
        Message m3 = new Message(3L,
                "Trzecia wiadomosc",
                "Ewa",
                new Comment("Comment Ewa 1"));

        messages.put(1L, m1);
        messages.put(2L, m2);
        messages.put(3L, m3);
    }

    public List<Message> getAllMessages() {
        return new ArrayList<>(messages.values());
    }

    public Message getMessage(Long id) {
        return messages.get(id);
    }

    public Message createMessage(Message message) {
        message.setId(messages.size() + 1);
        messages.put(message.getId(), message);
        return message;
    }

    public Message updateMessage(Long id, Message message) {
        if (!messages.containsKey(id)) {
            return null;
        }
        Message msg = messages.get(id);
        msg.setAuthor(message.getAuthor());
        msg.setCreated(message.getCreated());
        msg.setMessage(message.getMessage());
        messages.put(id, msg);
        return msg;
    }

    public void deleteMessage(Long id) {
        messages.remove(id);
    }
}
