/********************** 版权声明 *************************
 * 文件名: AddFieldAdapter.java
 * 包名: priv.primo.asmuse
 * 版权:	杭州华量软件  architectstudy
 * 职责: 
 ********************************************************
 *
 * 创建者：Primo  创建时间：2019/8/21
 * 文件版本：V1.0
 *
 *******************************************************/
package priv.primo.asmuse;

import org.objectweb.asm.*;

import java.io.FileOutputStream;
import java.io.IOException;

import static org.objectweb.asm.Opcodes.*;

public class AddFieldAdapter extends ClassVisitor {

    private int fAcc;
    private String fName;
    private String fDesc;
    private boolean isFieldPresent;

    public AddFieldAdapter(ClassVisitor classVisitor, int fAcc,String fName,String fDesc) {
        super(ASM4,classVisitor);
        this.fAcc = fAcc;
        this.fName = fName;
        this.fDesc = fDesc;
        isFieldPresent = false;
    }

    @Override
    public FieldVisitor visitField(int access, String name, String descriptor, String signature, Object value) {
        if (access == fAcc && name.equals(fName) && descriptor.equals(fDesc)){
            isFieldPresent = true;
        }
        return super.visitField(access, name, descriptor, signature, value);
    }

    @Override
    public void visitEnd() {
        if (!isFieldPresent){
            FieldVisitor fv = cv.visitField(fAcc,fName,fDesc,null,null);
            if (fv!=null){
                fv.visitEnd();
            }
        }
        super.visitEnd();
    }

    public static void main(String[] args) throws IOException {
        ClassReader cr =  new ClassReader("priv/primo/asmuse/TestClass");

        ClassWriter cw = new ClassWriter(0);

        AddFieldAdapter addFieldAdapter = new AddFieldAdapter(cw,ACC_PROTECTED+ACC_STATIC+ACC_FINAL
        ,"son", Type.getDescriptor(String.class));

        cr.accept(addFieldAdapter,0);
        byte[] bytes = cw.toByteArray();
        FileOutputStream fos = new FileOutputStream("D:/data/TestClass.class");
        fos.write(bytes);
        fos.close();
    }
}
