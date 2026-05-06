package hu.bme.aut.springdata.springdata_lab.entity;

import jakarta.persistence.*;

@Entity
public class Document {

    @Id
    @GeneratedValue
    private Long id;

    private String title;

    private String description;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private DocumentContent documentContent;

    private String etag;

    public Document(String title, String description, String etag) {
        this.title = title;
        this.description = description;
        this.etag = etag;
    }

    public Long getId() {
        return id;
    }

    public String getEtag() {
        return etag;
    }

    public void setEtag(String etag) {
        this.etag = etag;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public DocumentContent getDocumentContent() {
        return documentContent;
    }

    public void setDocumentContent(DocumentContent documentContent) {
        this.documentContent = documentContent;
    }

    public Document() {
    }

    public Document(String title, String description, DocumentContent documentContent) {
        this.title = title;
        this.description = description;
        this.documentContent = documentContent;
    }

    /*public Document(Long id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
    }*/
}