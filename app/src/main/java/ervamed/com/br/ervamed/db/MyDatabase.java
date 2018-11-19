package ervamed.com.br.ervamed.db;

import com.raizlabs.android.dbflow.annotation.Database;

@Database(name = MyDatabase.NAME, version = MyDatabase.VERSION)
public class MyDatabase {
    public static final String NAME = "DB_PLANTAS";
    public static final int VERSION = 1;
}
