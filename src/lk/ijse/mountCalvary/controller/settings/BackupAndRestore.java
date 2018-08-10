package lk.ijse.mountCalvary.controller.settings;

import lk.ijse.mountCalvary.db.DBConnection;

import java.util.Map;

public final class BackupAndRestore {
    protected static Map<String, String> dbDetails = DBConnection.getDbDetails();

    protected static boolean writeBackup(String path) throws Exception {
        Runtime runtime = Runtime.getRuntime();
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

    protected static boolean restoreBackup(String path) throws Exception {
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
//    protected static void booleanbackUp2() throws Exception{
//        try {
//
//            /*NOTE: Getting path to the Jar file being executed*/
//            /*NOTE: YourImplementingClass-> replace with the class executing the code*/
//
//            CodeSource codeSource = BackupAndRestore.class.getProtectionDomain().getCodeSource();
//            File jarFile = new File(codeSource.getLocation().toURI().getPath());
//            String jarDir = jarFile.getParentFile().getPath();
//
//
//            /*NOTE: Creating Database Constraints*/
//            String dbName = "YourDBName";
//            String dbUser = "YourUserName";
//            String dbPass = "YourUserPassword";
//
//            /*NOTE: Creating Path Constraints for folder saving*/
//            /*NOTE: Here the backup folder is created for saving inside it*/
//            String folderPath = jarDir + "\\backup";
//
//            /*NOTE: Creating Folder if it does not exist*/
//            File f1 = new File(folderPath);
//            f1.mkdir();
//
//            /*NOTE: Creating Path Constraints for backup saving*/
//            /*NOTE: Here the backup is saved in a folder called backup with the name backup.sql*/
//            String savePath = "\"" + jarDir + "\\backup\\" + "backup.sql\"";
//
//            /*NOTE: Used to create a cmd command*/
//            String executeCmd = "mysqldump -u" + dbUser + " -p" + dbPass + " --database " + dbName + " -r " + savePath;
//
//            /*NOTE: Executing the command here*/
//            Process runtimeProcess = Runtime.getRuntime().exec(executeCmd);
//            int processComplete = runtimeProcess.waitFor();
//
//            /*NOTE: processComplete=0 if correctly executed, will contain other values if not*/
//            if (processComplete == 0) {
//                System.out.println("Backup Complete");
//            } else {
//                System.out.println("Backup Failure");
//            }
//
//        } catch (URISyntaxException | IOException | InterruptedException ex) {
//            JOptionPane.showMessageDialog(null, "Error at Backuprestore" + ex.getMessage());
//        }
//    }
}
