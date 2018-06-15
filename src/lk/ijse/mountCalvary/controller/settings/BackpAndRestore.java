package lk.ijse.mountCalvary.controller.settings;

import lk.ijse.mountCalvary.db.DBConnection;

import java.util.Map;

public class BackpAndRestore {
    private static Map<String, String> dbDetails = DBConnection.getDbDetails();

    public static boolean writeBackup(String path) throws Exception {
        System.out.println("A1");
        Runtime runtime = Runtime.getRuntime();
        System.out.println("A2");
        Process runPro = runtime.exec(
                String.format("mysqldump -u%s -p%s --database %s -r %s", dbDetails.get("user"), dbDetails.get("password"), dbDetails.get("db"), path));
//                String.format("mysqldump %s -h %s -u %s -p %s -r%s",
//                        dbDetails.get("db"),
//                        dbDetails.get("ip"),
//                        dbDetails.get("user"),
//                        dbDetails.get("password"),
//                        path));
        System.out.println("A3 :: " + path);
        return runPro.waitFor() == 0;
    }

    public static boolean restoreBackup(String path) throws Exception {
        Runtime runtime = Runtime.getRuntime();
        String[] exCmd = new String[]{
                "mysql",
                "--user=" + dbDetails.get("user"),
                "--password=" + dbDetails.get("password"),
                dbDetails.get("db"),
                "-e",
                " source " + path
        };
        Process runTimeProcess = Runtime.getRuntime().exec(exCmd);
        return runTimeProcess.waitFor() == 0;
    }

}
