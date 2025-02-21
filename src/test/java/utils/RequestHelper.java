package utils;

import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;

public class RequestHelper {
    public static String ConvertXmlToString(String fileName){

        String filePath = "src\\main\\resources\\" + fileName;
        String xml;
        try {
            xml = Files.lines(Paths.get(filePath)).collect(Collectors.joining("\n"));
        }catch (Exception ex){
            xml = null;
        }
        return xml;
    }
}
