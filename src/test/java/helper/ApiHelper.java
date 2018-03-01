package helper;

import io.restassured.response.Response;

public class ApiHelper {

    public static Boolean isFibonacciSeriesValid(Response response) {
        String[] fibString = toStringArray(response);

        switch (fibString.length) {
            case 0:
                return false;
            case 1:
                if (Long.parseLong(fibString[0]) != 0) return false;
            default:
                for (int i = 2; i < fibString.length; i++) {
                    if (Long.parseLong(fibString[i]) != (Long.parseLong(fibString[i - 1])) + (Long.parseLong(fibString[i - 2]))) {
                        return false;
                    }
                }
        }
        return true;
    }

    public static String[] toStringArray(Response response) {
        String s = response.asString();
        String s1 = s.substring(1, s.length() - 1).replace("\"", "");
        return s1.split(",");
    }
}
