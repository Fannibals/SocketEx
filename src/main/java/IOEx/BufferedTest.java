package IOEx;

import org.junit.Test;

import java.io.*;

public class BufferedTest {

    boolean DBG;
    long startTime, endTime;

    public void copyFile(String fromPath, String destPath){
        // 1. File
        File fromFile = new File(fromPath);
        File toFile = new File(destPath);

        FileInputStream fis = null;
        FileOutputStream fos = null;
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        try {
            // 2. stream creation

            // 2.1
             fis = new FileInputStream(fromFile);
             fos = new FileOutputStream(toFile);

             // 2.2
             bis = new BufferedInputStream(fis);
             bos = new BufferedOutputStream(fos);

            // 3. operation
            byte[] buffer = new byte[10];
            int len;
//            while ((len = fis.read(buffer))!=-1){
//                fos.write(buffer,0,len);
//            }
            while((len = bis.read(buffer))!= -1){
                bos.write(buffer,0,len);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                // 正常来说应该先关闭外层流再关闭内层流
                // 但是关闭了外层流，内层流也会自动被关闭
                if(bis != null) bis.close();
                if(bos != null) bos.close();

                //
//                if(fis != null) fis.close();
//                if(fos != null) fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    @Test
    public void BufferedIOTest(){
        DBG = true;
        if (DBG) {startTime = System.currentTimeMillis();}

        copyFile("Cat03.jpg","Cat03-cp.jpg");

        if (DBG) {
            endTime = System.currentTimeMillis();
            System.out.println(endTime - startTime);
        }

    }

}
