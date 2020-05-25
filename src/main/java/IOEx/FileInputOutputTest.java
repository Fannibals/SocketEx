package IOEx;

import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;


/**
 * 用fileInputOutput来读取文本文件的时候，可能会出现乱码的情况。
 * 这是因为有些字符用一个字节是存不下的，如中文，可能要用2-3个字节，
 *  - 但是如果byte的缓存为5，文本是三个字节的话，中间的那个会被分割成左右两份，因而产生乱码
 *  - 但是如果只是英文或者数字则ok
 */
public class FileInputOutputTest {
    @Test
    public void testFileInputStream(){
        // 1. init file
        File file = new File("README.txt");
        // 2. init streams
        FileInputStream fis = null;
        try {
             fis = new FileInputStream(file);
            // 3. operate data
            byte[] bytes = new byte[5];
            int len;
            while((len = fis.read(bytes))!= -1){
//                for (int i = 0; i < len; i++) {
//                    System.out.print(bytes[i]);
//                }
                String str = new String(bytes,0,len);
                System.out.print(str);
            }
            // 4. close streams
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if(fis != null) fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
