package hu.bme.aut.springdata.springdata_lab.controller;

import hu.bme.aut.springdata.springdata_lab.entity.Document;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface DocumentRepository extends JpaRepository<Document, Long> {
    @Query("SELECT d FROM Document d WHERE d.id = :id")
    @EntityGraph(attributePaths = {"documentContent"})
    Optional<Document> findByIdFetchDocumentContent(Long id);
}