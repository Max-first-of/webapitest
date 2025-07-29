package back_test.soap_tests;

import back_test.base_tests.BaseTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DisplayName("Параметризованные тесты")
public class ParamTest extends BaseTest {

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
    @DisplayName("Данные берутся из data.csv")
    @ParameterizedTest
    @Tags({@Tag("SOAP"), @Tag("Ещё_тег")})
    @CsvFileSource(resources = "/data.csv", numLinesToSkip = 1)
    public void test3ParamTes(String input, String expected){
        assertThat(input)
                .isEqualTo(expected);
    }
}
