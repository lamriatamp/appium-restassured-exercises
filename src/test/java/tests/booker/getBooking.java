package tests.booker;

import base.BaseTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class getBooking extends BaseTest {
    //ambil base url
    BaseTest baseUrl = new BaseTest();
    Response response;

    @BeforeMethod
    public void SetUP(){
        RestAssured.baseURI = baseUrl.getBookerBaseUrl();
    }

    //Lakukan get Booking All
    @Test
    public void getBooking(){
        Response response = given()
                .when()
                .get("booking")
                .then()
                .extract().response();
        System.out.println("==== Get/Booking ====");
        System.out.println("Status Code: " + response.getStatusCode());
        System.out.println("Response Body: " + response.asPrettyString());
    }
    //Get booking by id
    @Test
    public void getBookingIds(){
        Response response = given()
                .when()
                .get("booking/1")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .extract().response();

        System.out.println("==== Get/Booking By ID ====");
        System.out.println("Status Code: " + response.getStatusCode());
        System.out.println("Response Body: " + response.asPrettyString());

        //Assert firstname, Lastname, total price
        JsonPath jsonPath = response.jsonPath();
        Assert.assertEquals(jsonPath.getString("firstname"),"James");
        Assert.assertEquals(jsonPath.getString("lastname"),"Brown");
        Assert.assertEquals(jsonPath.getString("totalprice"),"111");
    }
    //Verify assertion get booking ID
}
