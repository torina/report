package de.bula.report.pattern;

import lombok.*;
import org.springframework.data.annotation.Id;

import java.util.Arrays;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TableHeader {
    @Id
    @Getter
    String id;
    /**
     * vendor name
     */
    @Getter
    @Setter
    List<String> columns;

    public static TableHeader convertCommaSeparatedStringToTable(String columns){
        return TableHeader.builder().columns(Arrays.asList(columns.split("\\s*,\\s*"))).build();
    }

    @Override
    public String toString() {
        return columns.toString();
    }
}
