package de.bula.report.pattern;

import lombok.*;
import org.springframework.data.annotation.Id;

import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Pattern {
    @Id
    @Getter
    String id;
    /**
     * vendor name
     */
    @Getter
    @Setter
    String name;

    /**
     * what the tables look like
     */
    @Getter
    @Setter
    List<TableHeader> headers;
}
