package hu.bme.aut.springdata.springdata_lab.controller;

import hu.bme.aut.springdata.springdata_lab.entity.Document;
import hu.bme.aut.springdata.springdata_lab.entity.DocumentContent;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;

@RestController
@RequestMapping("/api/documents")
public class DocumentController {
    @Autowired
    private DocumentRepository documentRepository;

    private static final Logger logger = LoggerFactory.getLogger(DocumentController.class);

    @PostMapping
    public ResponseEntity<CreateDocumentResponse> create(@RequestBody CreateDocumentRequest req) {
        var doc = new Document(req.getTitle(), req.getDescription(), java.util.UUID.randomUUID().toString());
        var docContent = new DocumentContent(req.getContentType(), Base64.getDecoder().decode(req.getContentBase64()));
        doc.setDocumentContent(docContent);
        documentRepository.save(doc);
        return ResponseEntity.ok(new CreateDocumentResponse(doc.getId()));
    }

    @GetMapping("{id}/download")
    public ResponseEntity<byte[]> download(@PathVariable Long id,
                                           @RequestHeader(value = "If-None-Match", required = false, defaultValue = "") String ifNoneMatch) {

        var docOpt = documentRepository.findById(id);
        if (docOpt.isEmpty()) return ResponseEntity.notFound().build();

        if (docOpt.get().getEtag().equals(ifNoneMatch)) {
            logger.warn("File not modified");
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        }

        var doc = documentRepository.findByIdFetchDocumentContent(id).get();

        return ResponseEntity.ok()
                .header("Content-Type", doc.getDocumentContent().getContentType())
                .header("Cache-Control", "max-age=60") // Cache for 1 minute[cite: 1]
                .header("ETag", doc.getEtag())
                .body(doc.getDocumentContent().getPayload());
    }

    @PostMapping(value = "/multipart", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public void createMultipart(
            @RequestParam String title,
            @RequestParam String description,
            @RequestParam MultipartFile file,
            HttpServletResponse response) throws IOException {
        try {
            var etag = java.util.UUID.randomUUID().toString();
            var doc = new Document(title, description, etag);
            var docContent = new DocumentContent(null, file.getContentType(), file.getBytes());
            doc.setDocumentContent(docContent);
            documentRepository.save(doc);

            String responseBody = "<html><body><p>Saved with ID " + doc.getId() + "</p></body></html>";
            response.getWriter().write(responseBody);
        } catch (Exception e) {
            response.getWriter().write("<html><body><p>Saving failed</p></body></html>");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateBinaryContent(@PathVariable Long id, @RequestBody byte[] newContent) {
        var docOpt = documentRepository.findByIdFetchDocumentContent(id);
        if (docOpt.isEmpty()) return ResponseEntity.notFound().build();

        Document doc = docOpt.get();

        doc.getDocumentContent().setPayload(newContent);
        doc.setEtag(java.util.UUID.randomUUID().toString());

        documentRepository.save(doc);
        return ResponseEntity.noContent().build();
    }
}