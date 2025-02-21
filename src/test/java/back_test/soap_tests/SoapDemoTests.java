package back_test.soap_tests;

import back_test.base_tests.BaseTest;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import utils.RequestHelper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class SoapDemoTests extends BaseTest {

    @DisplayName("Тест 1. Получаем настройки")
    @Tags({@Tag("SOAP"), @Tag("SMOKE")})
    @Test
    public void test1GetOrg(){
        Response response = given()
                .body(RequestHelper.ConvertXmlToString("getSettings.xml"))
                .when()
                .post("")
                .then()
                .statusCode(200)
                .extract().response();
        String s = response.asString();
        XmlPath xmlPath = new XmlPath(s);

        var dateOfCalculationAge = xmlPath.get("DateOfCalculationAge");
        var maxCountWishPreschools = xmlPath.get("MaxCountWishPreschools");
        assertThat(s)
                .as("Дата равна")
                .isEqualTo("");
        System.out.println(s);
    }
}
