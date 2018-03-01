import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.eclipse.jetty.http.HttpStatus;
import org.junit.Test;

import static helper.ApiHelper.isFibonacciSeriesValid;
import static helper.ApiHelper.toStringArray;
import static io.restassured.RestAssured.get;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class FibonacciServiceTest {

    @Test
    public void fibonacciSeriesTest() {
        RestAssured.baseURI = "http://localhost:7003";
        Response response = get("/fib");

        assertEquals("/fib API is down!", HttpStatus.OK_200, response.statusCode());
        assertTrue("Not in a fibonacci series", isFibonacciSeriesValid(response));
    }

    @Test
    public void fibonacciIndexTest() {
        RestAssured.baseURI = "http://localhost:7003";

        int index = 9;
        Response response = get("/fib/" + index);

        assertEquals("/fib/{index} API is down!", HttpStatus.OK_200, response.statusCode());
        assertEquals("Incorrect index value", "34", response.asString());
    }

    @Test
    public void fibonacciRangeTest() {
        RestAssured.baseURI = "http://localhost:7003";
        int startIndex = 0;
        int finishIndex = 20;

        Response response = get("/fib/range?startIndex=" + startIndex + "&finishIndex=" + finishIndex);

        assertEquals("/range API is down!", HttpStatus.OK_200, response.statusCode());
        assertTrue("Not a fibonacci series", isFibonacciSeriesValid(response));

        String[] result = toStringArray(response);
        assertEquals("Incorrect range of numbers!", finishIndex, result.length);
    }

    @Test
    public void firstIncorrectFibonacciSequenceTest() {
        //additional test to check if first incorrect fibonacci number is consistent. this might not be needed
        RestAssured.baseURI = "http://localhost:7003";
        int startIndex = 0;

        for (int finishIndex = 1; ; finishIndex++) {
            Response response = get("/fib/range?startIndex=" + startIndex + "&finishIndex=" + finishIndex);

            if (!isFibonacciSeriesValid(response)){
                assertEquals("First incorrect fibonacci number should be the 30th", 30, finishIndex );
                break;
            }
        }
    }
}
