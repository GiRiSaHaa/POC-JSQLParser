
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserManager;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.insert.Insert;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.util.TablesNamesFinder;
import net.sf.jsqlparser.util.SelectUtils;
import org.junit.Test;

import java.io.StringReader;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class JsqlParserTest {

    private final CCJSqlParserManager parserManager = new CCJSqlParserManager();

    @Test
    public void testMultiPartTableNameWithServerProblem() throws Exception {
        Statement statement = null;
        try {
            statement = CCJSqlParserUtil.parse("SELECT * FROM MY_TABLE1");
        } catch (JSQLParserException e) {
            e.printStackTrace();
        }
        Select selectStatement = (Select) statement;
        TablesNamesFinder tablesNamesFinder = new TablesNamesFinder();
        List<String> tableList = tablesNamesFinder.getTableList(selectStatement);

        System.out.println(tableList);

        assertEquals("MY_TABLE1", tableList.get(0));

    }

    @Test
    public void testPostgresSchema() throws Exception {
        final String statement = "SELECT nextval('seq_testseq')";
        Select select = (Select) parserManager.parse(new StringReader(statement));

        System.out.println(select);
        assertEquals(select.toString(), statement);
    }

    @Test
    public void testOracleSchema() throws Exception {
        final String statement = "SELECT seq_testseq.nextval FROM dual";
        Select select = (Select) parserManager.parse(new StringReader(statement));

        Insert insert = (Insert) parserManager.parse(new StringReader(statement));

        System.out.println(select);
        System.out.println(insert);
        assertEquals(select.toString(), statement);
    }
}
