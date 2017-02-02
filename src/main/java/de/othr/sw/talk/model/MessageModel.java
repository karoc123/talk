package de.othr.sw.talk.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 * Messages can be displayed on the website.
 * They only get deleted, after cleanOutMessages() is called.
 * They are not persisted on the database.
 */
@Named
@SessionScoped
public class MessageModel implements Serializable{

    private List<String> messages = new ArrayList<>();

    public List<String> getMessages() {
        return messages;
    }

    public void cleanOutMessages(){
        this.messages = new ArrayList<>();
    }
    
    public void setMessages(List<String> messages) {
        this.messages = messages;
    }
    
    public void addMessage(String m){
        messages.add(m);
    }
}