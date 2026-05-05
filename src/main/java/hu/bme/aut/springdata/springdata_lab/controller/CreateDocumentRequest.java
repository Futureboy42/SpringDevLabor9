package hu.bme.aut.springdata.springdata_lab.controller;

import java.util.Base64;

public class CreateDocumentRequest {
    private String title;
    private String description;
    private String contentType;
    private byte[] content;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public CreateDocumentRequest() {
    }

    public CreateDocumentRequest(String title, String description, String contentType, byte[] content) {
        this.title = title;
        this.description = description;
        this.contentType = contentType;
        this.content = content;
    }

    public byte[] getContentBase64() {
        return Base64.getEncoder().encode(content);
    }
}
