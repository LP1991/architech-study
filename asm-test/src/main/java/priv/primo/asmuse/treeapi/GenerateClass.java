/********************** 版权声明 *************************
 * 文件名: GenerateClass.java
 * 包名: priv.primo.asmuse.treeapi
 * 版权:	杭州华量软件  architectstudy
 * 职责: 
 ********************************************************
 *
 * 创建者：Primo  创建时间：2019/8/22
 * 文件版本：V1.0
 *
 *******************************************************/
package priv.primo.asmuse.treeapi;

import jdk.internal.org.objectweb.asm.tree.ClassNode;
import jdk.internal.org.objectweb.asm.tree.FieldNode;
import jdk.internal.org.objectweb.asm.tree.MethodNode;

import static jdk.internal.org.objectweb.asm.Opcodes.*;

/**
 * ClassNode cn = new ClassNode(ASM4);
 * ClassReader cr = new ClassReader(...);
 * cr.accept(cn, 0); ... // here transform cn as you want
 * ClassWriter cw = new ClassWriter(0);
 * cn.accept(cw);
 * byte[] b = cw.toByteArray();
 *
 */
public class GenerateClass {
    public static void main(String[] args) {
        ClassNode cn = new ClassNode();
        cn.version = V1_5; cn.access = ACC_PUBLIC + ACC_ABSTRACT + ACC_INTERFACE;
        cn.name = "pkg/Comparable"; cn.superName = "java/lang/Object";
        cn.interfaces.add("pkg/Mesurable");
        cn.fields.add(new FieldNode(ACC_PUBLIC + ACC_FINAL + ACC_STATIC, "LESS", "I", null, new Integer(-1)));
        cn.fields.add(new FieldNode(ACC_PUBLIC + ACC_FINAL + ACC_STATIC, "EQUAL", "I", null, new Integer(0)));
        cn.fields.add(new FieldNode(ACC_PUBLIC + ACC_FINAL + ACC_STATIC, "GREATER", "I", null, new Integer(1)));
        cn.methods.add(new MethodNode(ACC_PUBLIC + ACC_ABSTRACT, "compareTo", "(Ljava/lang/Object;)I", null, null));

//        ClassNode cn = new ClassNode();
//        ClassReader cr = new ClassReader(...);
//        cr.accept(cn, 0);
//        ClassWriter cw = new ClassWriter(0); cn.accept(cw); byte[] b = cw.toByteArray();
    }
}
