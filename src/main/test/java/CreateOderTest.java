import Constans.OrdersConts;
import Constans.URL;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import io.qameta.allure.junit4.DisplayName; // импорт DisplayName

public class CreateOderTest extends BasicTest{
    private OrdersConts order;


    private String firstName = "NewFirstName";
    private String lastName = "NewLastName";
    private String address = " New Address, 99";
    private String metroStation = "26";
    private String phone = "+7 800 000 00 01";
    private int rentTime = 3;
    private String deliveryDate = "2024-08-02";
    private String comment = "Random Comment";
    private String[] color = {"BLACK", "GREY"};


    @Test
    @DisplayName("Order  create with 2 colors ")

    public void orderCouldBeCreatedWith2ColorsTest() {
        order = new OrdersConts(firstName, lastName, address, metroStation, phone, rentTime, deliveryDate, comment, color);
        Response response = sendPostRequest(URL.ORDERS_CREATE, order);
        checkResponseCode(response, 201);
        Integer track = getOrderTrack(response);
        checkValueNotNull(track);
    }

    @Test
    @DisplayName("Order  create without color ")

    public void orderCouldBeCreatedWithoutColorTest() {
        order = new OrdersConts(firstName, lastName, address, metroStation, phone, rentTime, deliveryDate, comment);
        Response response = sendPostRequest(URL.ORDERS_CREATE, order);
        checkResponseCode(response, 201);
        Integer track = getOrderTrack(response);
        checkValueNotNull(track);
    }

}
