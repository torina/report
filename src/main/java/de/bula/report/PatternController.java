package de.bula.report;


import de.bula.report.pattern.Pattern;
import de.bula.report.pattern.TableHeader;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

//@RestController
public class PatternController {

    @Autowired
    Service patternService;

//    @RequestMapping("/")
    public String index() {
        List<String> headers = new ArrayList<>();
        List<TableHeader> tables = new ArrayList<>();
        headers.add("Ololo, tratata, amama, ulala");
        headers.add("col1, col2, col3, col4");
        tables.add(TableHeader.builder().columns(headers).build());
//        headers.add(new TableHeader());
        patternService.createPattern(Pattern.builder().name("Vendor 1 Kaka").headers(tables).build());
        return "Greetings from Spring Boot!";
    }
}
