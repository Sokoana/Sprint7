import Constans.OrdersData;
import Constans.URL;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
@RunWith(Parameterized.class)
public class CreateOrderTestParam extends BasicTestOrder{
    private OrdersConts order;


    private final String firstName;
    private final String lastName;
    private final String address;
    private final String metroStation;
    private final String phone;
    private final int rentTime;
    private final String deliveryDate;
    private final String comment;
    private final String[] color;

    public CreateOrderTestParam(String firstName, String lastName, String address, String metroStation, String phone, int rentTime, String deliveryDate, String comment, String color) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.metroStation = metroStation;
        this.phone = phone;
        this.rentTime = rentTime;
        this.deliveryDate = deliveryDate;
        this.comment = comment;
        this.color = new String[]{color};
    }

    @Parameterized.Parameters(name = "color = {8}")
    public static Object[][] getTestData() {
        return new Object[][]{
                {"NewFirstName1", "NewLastName1", "Address, 1", "25", "+7 800 555 00 01", 1,
                        "2024-04-02", "Some Random Comment", "BLACK"},
                {"NewFirstName2", "NewLastName2", "New Address, 10", "22", "+7 222 000 00 01", 2,
                        "2024-08-05", "Comment", "GREY"}
        };
    }

    @Test
    @DisplayName("Order  create with 2 colors ")

    public void orderWithAnyColorTest() {
        order = new OrdersConts(firstName, lastName, address, metroStation, phone, rentTime, deliveryDate, comment, color);
        Response response = sendPostRequest(URL.ORDERS_CREATE, order);
        checkResponseCode(response, 201);
        Integer track = getOrderTrack(response);
        checkValueNotNull(track);
    }
}
