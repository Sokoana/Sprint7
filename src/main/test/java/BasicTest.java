import Constans.CourierConst;
import Constans.OrdersConts;
import Constans.URL;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.config.HttpClientConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.filter.log.ErrorLoggingFilter;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

import org.junit.Assert;


import java.util.List;

public abstract class BasicTest {
    RestAssuredConfig config = RestAssured.config()
            .httpClient(HttpClientConfig.httpClientConfig()
                    .setParam("http.socket.timeout",40000)
                    .setParam("http.connection.timeout", 40000));

    private static RequestSpecification requestSpec() {
        return new RequestSpecBuilder()
                .setBaseUri(URL.BASE_URL)
                .addHeader("Content-Type", "application/json")
                 .setRelaxedHTTPSValidation()
                .addFilter(new ErrorLoggingFilter())
                .build();
    }



    @Step("Send POST request")
    protected Response sendPostRequest(String endpoint, Object body) {
        return given()
                .config(config)
                .spec(requestSpec())
                .body(body)
                .post(endpoint)
                .thenReturn();
    }

    @Step("Send GET request")
    protected Response sendGetRequest(String endpoint) {
        return given()
                .config(config)
                .spec(requestSpec())
                .get(endpoint)
                .thenReturn();
    }

    @Step("Check response code")
    protected void checkResponseCode(Response response, int responseCode) {
        response.then()
                .assertThat()
                .statusCode(responseCode);
    }


    @Step("Check parameter = value ")
    protected void validateResponseBody(Response response, String parameter, String value) {
        response.then()
                .assertThat()
                .body(parameter, equalTo(value));
    }


    @Step("Check parameter = value")
    protected void validateResponseBody(Response response, String parameter, Boolean value) {
        response.then()
                .assertThat()
                .body(parameter, equalTo(value));
    }



    @Step("Check that value  not NULL")
    protected void checkValueNotNull(Object param) {
        String objName = param.getClass().getName();
        Assert.assertNotNull("Value for " + objName + " is not presented", param);
    }



    @Step("Login as courier with login and password")
    protected Response loginAsCourier(String login, String password) {
        CourierConst courier = new CourierConst();
        courier.setLogin(login);
        courier.setPassword(password);
        return given()
                .filter(new RequestLoggingFilter())
                .filter(new ResponseLoggingFilter())
                .filter(new ErrorLoggingFilter())
                .config(config)
                .spec(requestSpec())
                .body(courier)
                .post(URL.COURIER_LOGIN)
                .thenReturn();


    }

    @Step("Get courier id")
    protected Integer getCourierId(Response response) {
        return response
                .then()
                .extract()
                .body()
                .path("id");
    }

    @Step("Delete courier  id")
    protected void deleteCourier(Integer id) {
        given()
                .config(config)
                .spec(requestSpec())
                .pathParam("courier_id", id)
                .delete(URL.COURIER_DELETE);
    }



    @Step("Delete courier")
    protected void deleteCourier(CourierConst courier) {
        Response response = loginAsCourier(courier.getLogin(), courier.getPassword());
        Integer id = getCourierId(response);
        deleteCourier(id);
    }



    @Step("Get order track ")
    protected Integer getOrderTrack(Response response) {
        return response
                .then()
                .extract()
                .body()
                .path("track");
    }

    @Step("Get  list orders")
    protected List<String> getOrdersList(Response response) {
        return response
                .then()
                .extract()
                .body()
                .path("orders");
    }




}
