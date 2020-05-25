package IOEx;

import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author Ethan Shin
 */
public class Pictest {

    public static final int MAGICNUM = 13;

    // encryption of the pic
    @Test
    public void encryption(){
        FileInputStream fis = null;
        FileOutputStream fos = null;
        try {
            fis = new FileInputStream("Cat03.jpg");
            fos = new FileOutputStream("Cat03-ept.jpg");

            byte[] buffer = new byte[512];

            int len;
            while((len = fis.read(buffer))!= -1){
                for (int i = 0; i < len; i++) {
                    int afterEncp = buffer[i] ^ MAGICNUM;
                    fos.write(afterEncp);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fis != null) fis.close();
                if (fos != null) fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // decryption of the pic
    @Test
    public void decryption(){
        FileInputStream fis = null;
        FileOutputStream fos = null;
        try {
            fis = new FileInputStream("Cat03-ept.jpg");
            fos = new FileOutputStream("Cat03-revert.jpg");

            byte[] buffer = new byte[512];

            int len;
            while((len = fis.read(buffer))!= -1){
                for (int i = 0; i < len; i++) {
                    int afterEncp = buffer[i] ^ MAGICNUM;
                    fos.write(afterEncp);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fis != null) fis.close();
                if (fos != null) fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void test(){
        int b = 10;
        System.out.println(Integer.toBinaryString(b));
        System.out.println(Integer.toBinaryString(MAGICNUM));
        System.out.println(Integer.toBinaryString(b ^ MAGICNUM));
        System.out.println(Integer.toBinaryString(b ^ MAGICNUM ^ MAGICNUM));
    }



}
