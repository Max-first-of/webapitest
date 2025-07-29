package back_test.soap_tests;

import back_test.base_tests.BaseTest;
import config.enums.SoapMethods;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;;
import model.SoapBodyRequestBuilder;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.RequestHelper;
import config.enums.SoapMethods.*;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DisplayName("SOAP тесты")
public class SoapDemoTests extends BaseTest {
    String someOutsideVariable = System.getenv("JENKINS_VAR");
    String soapHost = "https://demoes.ir-tech.ru/Other/ES2_ODO/Web/Services/PreschoolInquiryWcfService.svc?wsdl";

    @DisplayName("Тест метода GetSettings")
    @Tags({@Tag("SOAP"), @Tag("SMOKE")})
    @Test
    public void test1GetOrg(){
        step("Подготовка данных");

        SoapBodyRequestBuilder builder = new SoapBodyRequestBuilder("getSettings.xml");
        builder
                .addTagValue("es:Okato", "1000");

        addXmlAttach("Тело запроса", builder.build());

        step("Выполняем запрос",()->{

        });
        Response response = given()
                .header("SOAPAction", "GetSettings")//Название метода, который вызываем
                .body(builder.build())
                .when()
                .post("")
                .then()
                .statusCode(200)
                .extract().response();

        step("Работаем с ответом");
        String s = response.getBody().asString();
        AddXmlAttachFormat("Ответ", s);

        XmlPath xmlPath = new XmlPath(s);

        String dateOfCalculationAge = xmlPath.get("DateOfCalculationAge").toString().substring(0,10);
        var maxCountWishPreschools = xmlPath.get("MaxCountWishPreschools");

        step("Проверяем данные", ()->{
            assertThat(dateOfCalculationAge)
                    .as("Дата равны")
                    .isEqualTo("2021-09-02");
        });
    }
    @DisplayName("Тест с использованием данных из Jenkins")
    @Tag("ONE")
    @Test
    public void getParamFromJenkinsTest(){
        SoapBodyRequestBuilder body;
        step("Подготовка данных");
        body = new SoapBodyRequestBuilder("");
        body.addTagValue("", "");

        step("Выполняем запрос",()->{
        });
        Response response = RequestHelper.soapRequest(reg.I.value, body);

        step("Проверяем ответ");
        assertThat(someOutsideVariable).isEqualTo("4");
    }
}
