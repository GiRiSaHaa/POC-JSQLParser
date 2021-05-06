import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.util.TablesNamesFinder;


import java.util.List;

public class PocJsqlParser {
    public static void main(String[] args) {
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
    }
}
