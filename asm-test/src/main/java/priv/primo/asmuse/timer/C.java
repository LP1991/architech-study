package priv.primo.asmuse.timer;

import jdk.internal.org.objectweb.asm.Type;
import jdk.internal.org.objectweb.asm.util.ASMifier;
import jdk.internal.org.objectweb.asm.util.Textifier;

public class C {
    public void m() throws Exception{
        Thread.sleep(100);
        Thread.sleep(101);
    }

    public static void main(String[] args) throws Exception {
//        Textifier.main(new String[]{"priv.primo.asmuse.timer.C"});
        System.out.println(Type.LONG_TYPE.getDescriptor());
        System.out.println(Type.getDescriptor(String.class));
        System.out.println(String.class.isArray());
    }
}
