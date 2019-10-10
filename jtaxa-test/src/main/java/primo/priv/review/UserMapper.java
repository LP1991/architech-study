/********************** 版权声明 *************************
 * 文件名: UserMapper.java
 * 包名: priv.primo.primo.priv.review
 * 版权:	杭州华量软件  jtaxatest
 * 职责: 
 ********************************************************
 *
 * 创建者：Primo  创建时间：2019/4/8
 * 文件版本：V1.0
 *
 *******************************************************/
package primo.priv.review;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;

public interface UserMapper {
    @Insert("INSERT INTO user(id,name) VALUES(#{id},#{name})")
    @Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
    void insert(User user);
}
