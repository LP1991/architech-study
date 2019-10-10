/********************** 版权声明 *************************
 * 文件名: CopyFile.java
 * 包名: priv.primo.primo.priv.nio
 * 版权:	杭州华量软件  jtaxatest
 * 职责:
 ********************************************************
 *
 * 创建者：Primo  创建时间：2019/5/17
 * 文件版本：V1.0
 *
 *******************************************************/
package primo.priv.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class CopyFile {
    public static void main(String[] args)throws  Exception{
        String in = "src/main/java/priv/primo/primo.priv.nio/CopyFile.java";
        String out = "src/main/java/priv/primo/primo.priv.nio/CopyFile.java.copy";

        FileInputStream fis = new FileInputStream(in);
        FileOutputStream fos = new FileOutputStream(out);
        FileChannel fisChannel = fis.getChannel();
        FileChannel fosChannel = fos.getChannel();
        ByteBuffer buffer = ByteBuffer.allocate(1024);

        while(true){
//            buffer.clear();

            int r = fisChannel.read(buffer);

            if (r == -1){
                break;
            }

            buffer.flip();

            fosChannel.write(buffer);
            buffer.compact();
        }
    }
}
