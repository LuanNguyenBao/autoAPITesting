package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public class PropertyReader {

    public String readGlobalProp(String arg) throws IOException {
        Path path = Paths.get("/src/test/resources", "global.properties");
        String dir = System.getProperty("user.dir");
        String absolutePath = dir.concat("\\").concat(path.toString());
        InputStream file = new FileInputStream(absolutePath);
        Properties prop = new Properties();
        try {
            prop.load(file);
        }
        finally {
            file.close();
        }
        return prop.getProperty(arg);
    }
}
