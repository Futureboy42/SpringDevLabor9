package hu.bme.aut.springdata.springdata_lab.controller;

import hu.bme.aut.springdata.springdata_lab.entity.Document;
import hu.bme.aut.springdata.springdata_lab.entity.DocumentContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;

@RestController
@RequestMapping("/api/documents")
public class DocumentController {
    @Autowired
    private DocumentRepository documentRepository;

    @PostMapping
    public ResponseEntity<CreateDocumentResponse> create(@RequestBody CreateDocumentRequest req) {
        var doc = new Document(req.getTitle(), req.getDescription());
        var docContent = new DocumentContent(req.getContentType(), Base64.getDecoder().decode(req.getContentBase64()));
        doc.setDocumentContent(docContent);
        documentRepository.save(doc);
        return ResponseEntity.ok(new CreateDocumentResponse(doc.getId()));
    }

    @GetMapping("{id}/download")
    public ResponseEntity<byte[]> download(@PathVariable Long id) {
        var docOpt = documentRepository.findByIdFetchDocumentContent(id);
        if (docOpt.isEmpty())
            return ResponseEntity.notFound().build();
        var doc = docOpt.get();
        return ResponseEntity.ok()
                .header("Content-Type", doc.getDocumentContent().getContentType())
                .body(doc.getDocumentContent().getPayload());
    }
}