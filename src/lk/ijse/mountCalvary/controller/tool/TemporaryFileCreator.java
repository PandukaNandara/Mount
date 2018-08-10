package lk.ijse.mountCalvary.controller.tool;

import java.io.*;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by IntelliJ IDEA.
 *
 * @author pandu
 * Date: 7/12/2018
 * Time: 6:12 PM
 */
public final class TemporaryFileCreator {
    private File tempFile;
    private Charset charset = StandardCharsets.UTF_8;

    public TemporaryFileCreator(String filePath, String prefix, String suffix) throws Exception {

        File originalFile = new File(getClass().getResource(filePath).toURI());
        tempFile = File.createTempFile(prefix, suffix);

        copyFile(originalFile, tempFile);
    }

    private void copyFile(File sourceFile, File destFile) throws Exception {
//        Files.copy( sourceFile.toPath(), destFile.toPath());
        try (FileChannel source = new FileInputStream(sourceFile.getAbsolutePath()).getChannel();
             FileChannel destination = new FileOutputStream(destFile).getChannel()) {
            destination.transferFrom(source, 0, source.size());
        }

    }

    public void readFile(File file) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader(file));
        try {
            StringBuilder sb = new StringBuilder();
            String line = "";
            do {
                sb.append(line);
                sb.append(System.lineSeparator());
                System.out.println(line);
                line = br.readLine();
            } while (line != null);
            String everything = sb.toString();
        } finally {
            br.close();
        }

    }

    public void putParameter(String parameter, String value) throws Exception {
        Path path = Paths.get(tempFile.toURI());

        String content = new String(Files.readAllBytes(path), charset);
        content = content.replaceAll(parameter, value);
        Files.write(path, content.getBytes(charset));
    }

    public boolean over() {
        return tempFile.delete();
    }

    public File getTempFile() {
        return tempFile;
    }

    @Override
    protected void finalize() {
        if (tempFile != null) tempFile.delete();
    }
}
