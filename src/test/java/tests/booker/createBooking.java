package tests.booker;

import base.BaseTest;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.json.simple.JSONObject;
import org.testng.Assert; // Ditambahkan untuk assertion
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo; // Ditambahkan untuk assertion RestAssured

public class createBooking extends BaseTest {

    Response response;

    @BeforeMethod
    public void setUp() {
        // Langsung panggil method karena sudah mewarisi (extends) BaseTest
        RestAssured.baseURI = getBookerBaseUrl();
    }

    // Membuat req payload create booking (JSON)
    public JSONObject bookingPayload() {
        JSONObject bookingdates = new JSONObject();
        bookingdates.put("checkin", "2026-04-12");
        bookingdates.put("checkout", "2026-04-12");

        JSONObject data = new JSONObject();
        data.put("firstname", "Lamria");
        data.put("lastname", "Tampubolon");
        data.put("totalprice", 5);
        data.put("depositpaid", true);          // Diubah menjadi boolean
        data.put("bookingdates", bookingdates); // Diubah menjadi variable JSON Object
        data.put("additionalneeds", "GADA");

        return data;
    }

    @Test
    public void createBookingTest() {
        JSONObject payload = bookingPayload();

        response = given()
                .header("Content-Type", "application/json")
                .header("Accept", "application/json") // Praktik yang baik: beri tahu server format balasan yang diinginkan
                .body(payload.toJSONString())
                .when()
                .post("/booking") // Endpoint spesifik untuk membuat booking
                .then()
                .statusCode(200)
                .extract().response();

        System.out.println("==== Create Booking ====");
        System.out.println("Status Code: " + response.getStatusCode());
        System.out.println("Response Body: " + response.asPrettyString());

        // ==========================================
        // ASSERTIONS (VALIDASI DATA)
        // ==========================================

        // 1. Memastikan Booking ID berhasil terbuat dan tidak null (TestNG Assert)
        Assert.assertNotNull(response.jsonPath().get("bookingid"), "Booking ID tidak boleh kosong!");

        // 2. Memastikan data yang disimpan sesuai dengan data yang dikirim (RestAssured Matcher)
        response.then().body("booking.firstname", equalTo("Lamria"))
                .body("booking.lastname", equalTo("Tampubolon"))
                .body("booking.totalprice", equalTo(5))
                .body("booking.depositpaid", equalTo(true))
                .body("booking.bookingdates.checkin", equalTo("2026-04-12"));
    }

    //buat case invalid
    //req data tidak valid, dan assert muncul error message
}