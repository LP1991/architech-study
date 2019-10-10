/********************** 版权声明 *************************
 * 文件名: BufferAccess.java
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

import java.nio.ByteBuffer;

public class BufferAccess {
    public static void main(String[] args) {
        ByteBuffer bb = ByteBuffer.allocate(10);
        printBuffer(bb);

        bb.put((byte)'H').put((byte)'e').put((byte)'l').put((byte)'l').put((byte)'o');
        printBuffer(bb);

        bb.flip();
        printBuffer(bb);

        System.out.println((char)bb.get()+","+(char)bb.get());
        printBuffer(bb);

        bb.compact();
        printBuffer(bb);

        bb.flip();
        System.out.println((char)bb.get()+","+(char)bb.get());
        printBuffer(bb);
    }

    private static void printBuffer(ByteBuffer bb) {
        String array = "";
        for (int i = 0; i < bb.array().length; i++) {
            array += (char)bb.array()[i];
        }
        String sout = String.format("limit:%d, position:%d, capacity:%d, buffer:%s",
                bb.limit(),bb.position(),bb.capacity(), array);

        System.out.println(sout);

    }
}
