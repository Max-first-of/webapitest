package back_test.soap_tests;

import back_test.base_tests.BaseTest;
import org.junit.jupiter.api.Test;

public class ParamTest extends BaseTest {
    String es_host = System.getProperty("ES_HOST");
    @Test
    public void getParamFromJenkins(){
        System.out.println(es_host);
    }
}
