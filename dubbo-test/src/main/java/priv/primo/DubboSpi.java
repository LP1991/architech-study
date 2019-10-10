/********************** 版权声明 *************************
 * 文件名: DubboSpi.java
 * 包名: priv.primo
 * 版权:	杭州华量软件  architectstudy
 * 职责: 
 ********************************************************
 *
 * 创建者：Primo  创建时间：2019/9/3
 * 文件版本：V1.0
 *
 *******************************************************/
package priv.primo;

import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.common.extension.ExtensionLoader;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class DubboSpi {
    public static void main(String[] args) {
        ExtensionLoader<Robot> loader = ExtensionLoader.getExtensionLoader(Robot.class);
        Map<String, String> map = new HashMap<>();
        map.put("optimusPrime","optimusPrime");
        List<Robot> activateExtension = loader.getActivateExtension(new URL("dubbo", "localhost", 1234,map), "optimusPrime");
        activateExtension.get(0).aha( new URL("dubbo1", "localhost1", 12341));
        loader.getExtension("bumblebee").sayHi();

//        Pattern pattern = Pattern.compile("\\s*[,]+\\s*");
//        String[] split = pattern.split(", 1, , ,2 ,");
//        for (String s : split) {
//            System.out.println(s+"sss");
//        }

    }
}
