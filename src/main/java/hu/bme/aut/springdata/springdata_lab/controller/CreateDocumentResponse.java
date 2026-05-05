package hu.bme.aut.springdata.springdata_lab.controller;

public class CreateDocumentResponse {
    private Long id;

    public CreateDocumentResponse() {
    }

    public CreateDocumentResponse(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}