package back_test.soap_tests;

import back_test.base_tests.BaseTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class ParamTest extends BaseTest {
    String es_host = System.getenv("ES_HOST");
    @DisplayName("WowTest")
    @Test
    public void getParamFromJenkinsTest(){
        assertThat(es_host).isEqualTo("4");
        System.out.println(es_host);
    }
}
