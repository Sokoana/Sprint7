import Constans.URL;

import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import io.qameta.allure.junit4.DisplayName; // импорт DisplayName



import java.util.List;

public class ListOderTest extends BasicTest{
    @Test
    @DisplayName("Get Orders  list")
    public void getOrdersList() {
        Response response = sendGetRequest(URL.ORDERS_GET);
        checkResponseCode(response, 200);
        List<String> orders = getOrdersList(response);
        checkValueNotNull(orders);
    }


}
