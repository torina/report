package de.bula.report.persisitence;

import de.bula.report.pattern.Pattern;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PatternRepository extends MongoRepository<Pattern, String> {
    Pattern findByName(String name);
    List<Pattern> findAllByNameStartsWith(String start);
}
