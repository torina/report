package de.bula.report.vaadin;

import com.vaadin.flow.data.binder.Result;
import com.vaadin.flow.data.binder.ValueContext;
import com.vaadin.flow.data.converter.Converter;
import de.bula.report.pattern.TableHeader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class TableHeaderConverter implements Converter<String, List<TableHeader>> {
    @Override
    public Result<List<TableHeader>> convertToModel(String value, ValueContext context) {
        Result<List<TableHeader>> table = convertToList(value, context);
        return table.flatMap(val -> {
            if (val == null) {
                return Result.ok(null);
            } else {
                return Result.ok(val);
            }
        });
    }

    private Result<List<TableHeader>> convertToList(String value, ValueContext context) {
        if (value == null) {
            return Result.ok(null);
        }

        // Remove leading and trailing white space
        value = value.trim();

        if (value.isEmpty()) {
            return Result.error("Empty value: " + context);
        }

        //semicolon separated TableHeaders
        List<String> splitted = Arrays.asList(value.split(";"));
        final List<TableHeader> result = new ArrayList<>();

        //one value is present
        if (splitted.isEmpty()) {
            // Convert "" to the empty value
            result.add(TableHeader.convertCommaSeparatedStringToTable(value));
        } else {
            //comma-separated Columns
            for (String s : splitted) {
                result.add(TableHeader.builder().columns(Arrays.asList(s.split("\\s*,\\s*"))).build());
            }
        }

        return Result.ok(result);
    }


    //todo where to get errorMessageProvider
    /**
     * Gets the error message to use when conversion fails.
     *
     * @param context The value context for generating the error message
     * @return the error message
     */
    protected String getErrorMessage(ValueContext context) {
        return "" + context;
    }

    @Override
    public String convertToPresentation(List<TableHeader> value, ValueContext context) {
        if (value == null) {
            return null;
        }
        return rolloutTables(value);
    }

    private String rolloutTables(List<TableHeader> tables) {
        return tables.stream()
                .map(table -> String.join(",", table.getColumns())
        ).collect(Collectors.joining(";"));
    }
}
