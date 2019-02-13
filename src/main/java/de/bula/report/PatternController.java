package de.bula.report;


import de.bula.report.pattern.Pattern;
import de.bula.report.pattern.TableHeader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

//@RestController
public class PatternController {

    @Autowired
    Service patternService;

//    @RequestMapping("/")
    public String index() {
        List<String> header = new ArrayList<>();
        header.add("");
//        header.add(new TableHeader());
        patternService.createPattern(Pattern.builder().name("Vendor 1 Kaka").headers(header).build());
        return "Greetings from Spring Boot!";
    }
}
