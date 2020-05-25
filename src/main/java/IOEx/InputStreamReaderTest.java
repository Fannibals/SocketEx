package IOEx;

import org.junit.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class InputStreamReaderTest {
    @Test
    public void test1() throws IOException {
        FileInputStream fis = new FileInputStream("README.txt");

        InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);

        char[] cbuf = new char[20];
        int len;

        while((len = isr.read(cbuf))!=-1){
            String str = new String(cbuf,0,len);
            System.out.println(str);
        }

        isr.close();
    }

    @Test
    public void test2() throws IOException {
        FileInputStream fis = new FileInputStream("README.txt");
        FileOutputStream fos = new FileOutputStream("README_GBK.txt");

        InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
        OutputStreamWriter osw = new OutputStreamWriter(fos,"gbk");

        char[] cbuf = new char[20];
        int len;

        while((len = isr.read(cbuf))!=-1){
            osw.write(cbuf,0,len);
        }

        isr.close();
        osw.close();
    }


}
