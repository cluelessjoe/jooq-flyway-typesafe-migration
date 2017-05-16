import org.jooq.example.common.DbInfo;
import org.jooq.example.migrator.Migrator;

public class ModelGenerator {

    public static void main(String[] args) throws Exception {
        DbInfo dbInfo = new DbInfo("jdbc:h2:~/h2testDb", "sa", "");
        new Migrator(dbInfo, new ModelSynchronizer(dbInfo)).migrate();
    }

}