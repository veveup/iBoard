package com.veveup.domain;

import java.io.Serializable;
import java.util.Date;

public class Message implements Serializable {
    private Integer id;
    private String author;
    private String content;
    private String date;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", author='" + author + '\'' +
                ", content='" + content + '\'' +
                ", date=" + date +
                '}';
    }
}
