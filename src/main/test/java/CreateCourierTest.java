import Constans.CourierData;
import Constans.URL;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import io.qameta.allure.junit4.DisplayName; // импорт DisplayName



import io.qameta.allure.Description; // импорт Description

public class CreateCourierTest extends BasicTestCourier{
    private CourierConst courier;


    private final String login = "123000qq";
    private final String password = "123000qq";
    private final String firstName = "123qq";

    @Test
    @DisplayName("Courier  created")

    public void courierCreatedTest() {
        courier = new CourierData(login, password, firstName);
        Response response = sendPostRequest(URL.COURIER_CREATE, courier);
        checkResponseCode(response, 201);
        validateResponseBody(response, "ok", true);
    }

    @Test
    @DisplayName("Courier  create without firstName")

    public void courierCreatedWithoutFirstNameTest() {
        courier = new CourierData();
        courier.setLogin(login);
        courier.setPassword(password);
        Response response = sendPostRequest(URL.COURIER_CREATE, courier);
        checkResponseCode(response, 201);
    }

    @Test
    @DisplayName("Courier  create without password")

    public void courierCreatedWithoutPasswordTest() {
        courier = new CourierData();
        courier.setLogin(login);
        courier.setFirstName(firstName);
        Response response = sendPostRequest(URL.COURIER_CREATE, courier);
        checkResponseCode(response, 400);
        validateResponseBody(response, "message", "Недостаточно данных для создания учетной записи");
    }

    @Test

    public void courierCreatedWithoutLoginTest() {
        courier = new CourierData);
        courier.setPassword(password);
        courier.setFirstName(firstName);
        Response response = sendPostRequest(URL.COURIER_CREATE, courier);
        checkResponseCode(response, 400);
        validateResponseBody(response, "message", "Недостаточно данных для создания учетной записи");
    }

    @Test
    @DisplayName("Courier  create with duplicated login ")

    public void courierCreatedWithDuplicateLoginTest() {

        courier = new CourierConst(login, password, firstName);
        sendPostRequest(URL.COURIER_CREATE, courier);
        CourierData newCourier = new CourierConst(login, RandomStringUtils
                .random(7, true, true), RandomStringUtils.randomAlphabetic(7));
        Response response = sendPostRequest(URL.COURIER_CREATE, newCourier);
        checkResponseCode(response, 409);
        validateResponseBody(response, "message", "Этот логин уже используется. Попробуйте другой.");
    }

    @After
    @DisplayName("Delete courier")
    @Description("Delete courier")
    public void deleteCreatedCourier() {
        try {
            deleteCourier(courier);
        } catch (Exception e){
            System.out.println("Courier not deleted");
        }
    }

}
