package JSQLBuilder;


import com.bunker.jsqlbuilder.InsertQueryBuilder;
import org.junit.jupiter.api.Test;

public class SQLTest {
    @Test
    public void test() {
        InsertQueryBuilder insertQueryBuilder = new InsertQueryBuilder("table");
        insertQueryBuilder.insertField("field", "value");
        insertQueryBuilder.setLast(new String[]{"field", "field"}, null);

        System.out.println(insertQueryBuilder.build());
    }
}
