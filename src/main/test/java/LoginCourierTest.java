import Constans.CourierConst;
import Constans.URL;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.filter.log.ErrorLoggingFilter;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class LoginCourierTest extends BasicTest{
    private CourierConst courier;


    private final String login = "123000qq";
    private final String password = "123000qq";

    @Before
    @DisplayName("Create a courier")

    public void createCourier() {

        String firstName = "123";
        courier = new CourierConst(login, password, firstName);
        sendPostRequest(URL.COURIER_CREATE, courier);
    }

    @Test
    @DisplayName("Courier could log in")

    public void courierCouldLogInTest() {
        Response response = loginAsCourier(login, password);
        checkResponseCode(response, 200);
        Integer id = getCourierId(response);
        checkValueNotNull(id);
    }

    @Test
    @DisplayName("Courier could not log in without password")

    public void courierCouldNotLogInWithoutPasswordTest() {

            Response response = loginAsCourier(login, "");
            checkResponseCode(response, 400);
            validateResponseBody(response, "message", "Недостаточно данных для входа");


    }

    @Test
    @DisplayName("Courier could not log in without login")

    public void courierCouldNotLogInWithoutLoginTest() {
        Response response = loginAsCourier("", password);
        checkResponseCode(response, 400);
        validateResponseBody(response, "message", "Недостаточно данных для входа");
    }



    @Test
    @DisplayName("Courier could not log in with wrong login")

    public void courierCouldNotLogInWithWrongLoginTest() {
        Response response = loginAsCourier(RandomStringUtils.randomAlphabetic(7), password);
        checkResponseCode(response, 404);
        validateResponseBody(response, "message", "Учетная запись не найдена");
    }

    @Test
    @DisplayName("Courier could not log in with wrong password")

    public void courierCouldNotLogInWithWrongPasswordTest() {
        Response response = loginAsCourier(login, RandomStringUtils.randomAlphabetic(7));
        checkResponseCode(response, 404);
        validateResponseBody(response, "message", "Учетная запись не найдена");
    }

    @After
    @DisplayName("Deleting created courier")
    public void deleteCreatedCourier() {
        try {
            deleteCourier(courier);
        } catch (Exception e){
            System.out.println("Courier could not be deleted");
        }
    }



}
