package gob.pe.devida.ppptcd.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

/**
 * File created by Linygn Escudero$ on 20/12/23$
 */
public class FileUtil {

    public String upLoadFiles(String rootPath, String folder, MultipartFile file, String namefile) {
        String route;
        String SAVE_DIR = folder;
        String pathSeparador = File.separator;
        String pathFtp = rootPath;
        String savePath = pathFtp + SAVE_DIR;

        try {
            MultipartFile filePart = file;
            String fileName = filePart.getOriginalFilename();//getSubmittedFileName();
            String fileType = getFileExtension(fileName);
            InputStream filecontent = filePart.getInputStream();
            String NameFileEnd = namefile + "." + fileType;
            String PathFinal = savePath + NameFileEnd;
            Path finalRoute = Paths.get(PathFinal);

            File directory = new File(pathFtp + folder);
            if (!directory.exists()) {
                directory.mkdirs();
            }
            Files.copy(filecontent, finalRoute, StandardCopyOption.REPLACE_EXISTING);
            route = SAVE_DIR + NameFileEnd;
        } catch(IOException excepcion){
            System.out.println(excepcion.getMessage());
            route = "ERROR :: " + excepcion.getMessage();
        }
        return route;
    }

    public static String getFileExtension(String fullName) {
        String fileName = new File(fullName).getName();
        int dotIndex = fileName.lastIndexOf('.');
        return (dotIndex == -1) ? "" : fileName.substring(dotIndex + 1);
    }
}
