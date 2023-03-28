package io.ylab.intensive.lesson04.persistentmap;

import io.ylab.intensive.lesson04.DbUtil;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;

public class PersistenceMapTest {
    public static void main(String[] args) throws SQLException {
        DataSource dataSource = initDb();
        PersistentMap persistentMap = new PersistentMapImpl(dataSource);
        persistentMap.init("map");
        persistentMap.put("key1", "value1");
        persistentMap.put("key2", "value2");
        persistentMap.put("key3", "value3");
        persistentMap.put("key4", "value4");
        persistentMap.put("key5", "value5");
        persistentMap.remove("key4");
        persistentMap.put("key3", "value333");

        PersistentMap persistentMapSecond = new PersistentMapImpl(dataSource);
        persistentMapSecond.init("map2");
        persistentMapSecond.put("key1", "value1");
        persistentMapSecond.put("key2", "value2");
        persistentMapSecond.put("key3", "value3");
        persistentMapSecond.put("key4", "value4");
        persistentMapSecond.put("key5", "value5");
        persistentMapSecond.remove("key1");
        persistentMapSecond.put("key2", "value222");

        System.out.println(persistentMap.getKeys());
        System.out.println(persistentMapSecond.getKeys());
        System.out.println();
        System.out.println(persistentMap.containsKey("key1"));  //true
        System.out.println(persistentMap.containsKey("key2"));  //true
        System.out.println(persistentMap.containsKey("key10")); //false
        System.out.println(persistentMap.get("key2"));   // value2
        System.out.println(persistentMap.get("key3"));   // value333
        System.out.println();
        System.out.println(persistentMapSecond.containsKey("key1"));    //false
        System.out.println(persistentMapSecond.containsKey("key2"));    //true
        System.out.println(persistentMapSecond.containsKey("key10"));   //false
        System.out.println(persistentMapSecond.get("key3"));   // value3
        System.out.println(persistentMapSecond.get("key2"));   // value222
        System.out.println();
        persistentMap.clear();
        persistentMapSecond.clear();
        System.out.println(persistentMap.getKeys());
        System.out.println(persistentMapSecond.getKeys());
    }

    public static DataSource initDb() throws SQLException {
        String createMapTable = ""
                + "drop table if exists persistent_map; "
                + "CREATE TABLE if not exists persistent_map (\n"
                + "   map_name varchar,\n"
                + "   KEY varchar,\n"
                + "   value varchar\n"
                + ");";
        DataSource dataSource = DbUtil.buildDataSource();
        DbUtil.applyDdl(createMapTable, dataSource);
        return dataSource;
    }
}
