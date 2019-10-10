/********************** 版权声明 *************************
 * 文件名: User.java
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

public class User {
    private int id;
    private String name;
    // setters and getters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static void main(String[] args) {
        StringBuffer querySql = new StringBuffer();
        querySql.append("select a.OWNER as SCHEMANAME, a.table_name as TABLENAME, a.constraint_name as CONSTRAINTNAME, a.constraint_type as CONSTRAINTTYPE, c.column_name as COLUMNNAME, c.POSITION as POSITION, ");
        querySql.append("       a.table_name ||'.'|| a.constraint_name as field_v").append(", ");
        querySql.append("       a.OWNER || '|' || a.table_name AS table_name_v").append(" ");
        querySql.append("  from DBA_CONSTRAINTS a, DBA_CONS_COLUMNS c ");
        querySql.append(" where a.table_name = c.table_name and a.constraint_name = c.constraint_name AND a.OWNER = GGSHARE'").append("'");
        System.out.println(querySql);

        int i = 1<<0;
        System.out.println(i);
    }
}
