package back_test.soap_tests;

import back_test.base_tests.BaseTest;
import io.qameta.allure.Step;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DisplayName("Параметризированные тесты")
public class ParamTest extends BaseTest {
    String someOutsideVarrible = System.getenv("JENKINS_VAR");
    @DisplayName("Получаем данные из Jenkins")
    @Test
    public void getParamFromJenkinsTest(){
        assertThat(someOutsideVarrible).isEqualTo("4");
    }

    //Параметризированный тест
    @DisplayName("Данные берутся из @ValueSource")
    @ParameterizedTest
    @Tags({@Tag("SOAP"), @Tag("SMOKE")})
    @ValueSource(ints = {1, 3, 5, -3, 15})
    public void test2ParamTest(int value){
        assertThat(value/2)
                .as("Число четное")
                .isEqualTo(0);
    }
    //Параметризованный тест, данные берутся из файла
    @ParameterizedTest
    @Tags({@Tag("SOAP"), @Tag("Случайный тег")})
    @CsvFileSource(resources = "/data.csv", numLinesToSkip = 1)
    public void test3ParamTes(){

    }
}
