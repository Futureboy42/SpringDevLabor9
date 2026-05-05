package hu.bme.aut.springdata.springdata_lab.controller2;

import hu.bme.aut.springdata.springdata_lab.api.DocumentControllerApi;
import hu.bme.aut.springdata.springdata_lab.api.model.CreateDocumentRequest;
import hu.bme.aut.springdata.springdata_lab.api.model.CreateDocumentResponse;
import hu.bme.aut.springdata.springdata_lab.controller.DocumentRepository;
import hu.bme.aut.springdata.springdata_lab.entity.Document;
import hu.bme.aut.springdata.springdata_lab.entity.DocumentContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import java.util.Base64;

@RestController
public class DocumentController2 implements DocumentControllerApi {
    //@Autowired
    //private DocumentRepository documentRepository;

    @Override
    public ResponseEntity<CreateDocumentResponse> create(CreateDocumentRequest req) {
        var doc = new Document(req.getTitle(), req.getDescription());
        var docContent = new DocumentContent(req.getContentType(), Base64.getDecoder().decode(req.getContentBase64()));
        doc.setDocumentContent(docContent);
        //documentRepository.save(doc);
        return DocumentControllerApi.super.create(req);
    }

    @Override
    public ResponseEntity<byte[]> download(Long id) {
        return DocumentControllerApi.super.download(id);
    }
}
