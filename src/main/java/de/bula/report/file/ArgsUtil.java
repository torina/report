package de.bula.report.file;

import lombok.Builder;

import java.util.Map;

@Builder
public class ArgsUtil {
    private Map<String, Strategy> commandLineLetterToMeaning;

//    static {
//        commandLineLetterToMeaning.put("g", Strategy.GUESS);
//    }

//    private ArgsUtil(){}

    public static String[] buildArgs(String filePath) {

//        File f = new File()
        String[] res = {"-g", "-o", "src/main/resources/static/result.json", filePath};

        return res;
    }

    public enum Strategy {
        GUESS
    }
}
