package hu.bme.aut.springdata.springdata_lab.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;


@Entity
public class DocumentContent {
    @Id
    @GeneratedValue
    private Long id;

    private String contentType;

    @Lob
    private byte[] payload;

    public DocumentContent(String contentType, byte[] payload) {
        this.contentType = contentType;
        this.payload = payload;
    }

    public DocumentContent() {
    }

    public DocumentContent(Object o, String contentType, byte[] content) {
        this.contentType = contentType;
        this.payload = content;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public byte[] getPayload() {
        return payload;
    }

    public void setPayload(byte[] payload) {
        this.payload = payload;
    }
}