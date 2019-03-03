package de.bula.report;

import de.bula.report.pattern.Pattern;
import de.bula.report.persistance.PatternRepository;
import org.springframework.beans.factory.annotation.Autowired;

@org.springframework.stereotype.Service
public class Service {

    private PatternRepository patternRepository;


    public Service(@Autowired PatternRepository patternRepository) {
        this.patternRepository = patternRepository;
    }

    public void createPattern(Pattern pattern){
        patternRepository.insert(pattern);
    }
}
