/********************** 版权声明 *************************
 * 文件名: RenameSignatureAdapter.java
 * 包名: priv.primo.asmuse
 * 版权:	杭州华量软件  architectstudy
 * 职责: 
 ********************************************************
 *
 * 创建者：Primo  创建时间：2019/8/22
 * 文件版本：V1.0
 *
 *******************************************************/
package priv.primo.asmuse;

import jdk.internal.org.objectweb.asm.signature.SignatureReader;
import jdk.internal.org.objectweb.asm.signature.SignatureVisitor;
import jdk.internal.org.objectweb.asm.signature.SignatureWriter;

import java.util.HashMap;
import java.util.Map;

import static jdk.internal.org.objectweb.asm.Opcodes.ASM5;

public class RenameSignatureAdapter extends SignatureVisitor {
    private SignatureVisitor sv;
    private Map<String, String> renaming;

    private String oldName;

    public RenameSignatureAdapter(SignatureVisitor sv,
                                  Map<String, String> renaming) {
        super(ASM5);
        this.sv = sv;
        this.renaming = renaming;
    }

    @Override
    public void visitFormalTypeParameter(String name) {
        sv.visitFormalTypeParameter(name);
    }

    @Override
    public SignatureVisitor visitClassBound() {
        sv.visitClassBound();
        return this;
    }

    @Override
    public SignatureVisitor visitInterfaceBound() {
        sv.visitInterfaceBound();
        return this;
    }

    @Override
    public void visitClassType(String name) {
        oldName = name;
        String newName = renaming.get(oldName);
        sv.visitClassType(newName == null ? name : newName);
    }

    @Override
    public void visitInnerClassType(String name) {
        oldName = oldName + "." + name;
        String newName = renaming.get(oldName);
        sv.visitInnerClassType(newName == null ? name : newName);

    }

    @Override
    public void visitTypeArgument() {
        sv.visitTypeArgument();
    }

    @Override
    public SignatureVisitor visitTypeArgument(char wildcard) {
        sv.visitTypeArgument(wildcard);
        return this;
    }

    @Override
    public void visitEnd() {
        sv.visitEnd();
    }

    public static void main(String[] args) {
        String s = "Ljava/util/HashMap<TK;TV;>.HashIterator<TK;>;";
        Map<String, String> renaming = new HashMap<String, String>();
        renaming.put("java/util/HashMap", "A");
        renaming.put("java/util/HashMap.HashIterator", "B");
        SignatureWriter sw = new SignatureWriter();
        SignatureVisitor sa = new RenameSignatureAdapter(sw, renaming);
        SignatureReader sr = new SignatureReader(s);
        sr.acceptType(sa);
        System.out.println(sw.toString());

    }
}
