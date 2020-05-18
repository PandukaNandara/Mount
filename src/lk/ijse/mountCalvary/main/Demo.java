package lk.ijse.mountCalvary.main;

import sun.misc.Unsafe;

import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Field;
import java.net.URISyntaxException;
import java.util.Random;

/**
 * Created by IntelliJ IDEA.
 *
 * @author pandu
 * Date: 8/14/2018
 * Time: 7:36 PM
 */

abstract class C {

}

//class D extends C{
//
//}

public class Demo extends C {

    private static Unsafe unsafe;

    static {
        try {
            Field field = Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            unsafe = (Unsafe) field.get(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    {
        try {
            File file = new File(getClass().getResource("").toURI());

        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) throws Exception {
        new Demo();

//        String s = new String(new char[]{'1', '0'});
//        System.out.println(addressOf(s));
//        System.out.println(addressOf(s.intern()));
//        System.out.println(addressOf("10"));
//        System.out.println(addressOf("10"));
//        System.out.println(addressOf("10"));

    }

    public static long addressOf(Object o) throws Exception {
        Object[] array = new Object[]{o};

        long baseOffset = unsafe.arrayBaseOffset(Object[].class);
        int addressSize = unsafe.addressSize();
        long objectAddress;
        switch (addressSize) {
            case 4:
                objectAddress = unsafe.getInt(array, baseOffset);
                break;
            case 8:
                objectAddress = unsafe.getLong(array, baseOffset);
                break;
            default:
                throw new Error("unsupported address size: " + addressSize);
        }

        return (objectAddress);
    }

    public static void printBytes(long objectAddress, int num) {
        for (long i = 0; i < num; i++) {
            int cur = unsafe.getByte(objectAddress + i);
            System.out.print((char) cur);
        }
        System.out.println();
    }
//        File f1=new File("NewFile.txt");
//        System.out.println("f1 is exist : "+f1.exists());
//        boolean isCreated = f1.createNewFile(); //throws IOException
//        System.out.println("is created  : "+isCreated);
//        System.out.println("f1 is exist : "+f1.exists());
//
////        Connection conn = MSDBConnection.getInstance().getConnection("D:\\Other Document\\2018 Jan_HAPUGALA MCHS.mdb");
////
//////        ResultSet rst = CrudUtil.executeQuery(
////
////        ResultSet rst = MSCrudUtil.executeQuery(conn,
////                "select * from tMchsAdmissions limit 1");
//////        DatabaseMetaData meta = conn.getMetaData();
//////        ResultSet rst = meta.getSchemas();
////        System.out.println(rst.next());
////        System.out.println(rst.getString("Gender1"));
////        while (rst.next()) {
////            String table = rst.getString(1);
////            System.out.println(table + " <-> " + rst.getString(2) + " <-> " + rst.getString(3) + " <-> " + rst.getString(4));
//////            ResultSet rst2 =  MSCrudUtil.executeQuery(conn,"desc " + table);
//////            while (rst2.next())
//////                System.out.println("\t" + rst2.getString(1));
////        }
}
