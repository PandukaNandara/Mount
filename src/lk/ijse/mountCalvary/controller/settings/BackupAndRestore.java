package lk.ijse.mountCalvary.controller.settings;

import lk.ijse.mountCalvary.db.DBConnection;
import org.apache.commons.io.FileUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 *
 * @author pandu
 * Date: 8/10/2018
 * Time: 9:15 PM
 */

public final class BackupAndRestore {
    protected static Map<String, String> dbDetails;
    private static int BUFFER = Short.MAX_VALUE;
    private static String ip;
    private static String port;
    private static String user;
    private static String password;
    private static String db;

    /**
     * The following method is required {@link FileUtils}.
     * Download the jar file @see
     * <a href="http://www-us.apache.org/dist//commons/io/binaries/commons-io-2.6-bin.zip">commons-io</a>
     */
    protected static boolean writeBackup(File file) throws Exception {
        readProperties();
        Runtime runtime = Runtime.getRuntime();
        Process runPro = runtime.exec(
                "mysqldump --host=" + ip +
                        " --port=" + port +
                        " --user=" + user +
                        " --password=" + password +
                        " " + db
        );
        InputStream in = runPro.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        StringBuffer query = new StringBuffer();

        int count;
        char[] cbuf = new char[BUFFER];
        while ((count = br.read(cbuf, 0, BUFFER)) != -1)
            query.append(cbuf, 0, count);
        br.close();
        in.close();

        FileUtils.writeLines(file, Collections.singleton(query));
        return runPro.waitFor() == 0;
    }

    protected static boolean restoreBackup(String path) throws Exception {
        readProperties();
        String[] executeCmd = new String[]{
                "mysql",
                "--user=" + user,
                "--password=" + password,
                db,
                "-e",
                " source " + path};
        Process runtimeProcess = Runtime.getRuntime().exec(executeCmd);
        return runtimeProcess.waitFor() == 0;
    }

    private static void readProperties() throws Exception {
        if (dbDetails != null) return;

        dbDetails = DBConnection.getInstance().getDbDetails();
        ip = dbDetails.get("ip");
        port = dbDetails.get("port");
        user = dbDetails.get("user");
        password = dbDetails.get("password");
        db = dbDetails.get("db");
    }
}
