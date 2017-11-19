import responseTransformer.ToCapsTranformer;

import java.util.HashMap;
import java.util.Map;

import static spark.Spark.*;

public class HelloWorld {

    public static void main(String[] args) {

        Map<String, String> pretendDB = new HashMap<String, String>();

        port(5555);
        threadPool(42);
        staticFiles.location("/public");

        get("/hello", (req, res) -> "Hello World!!!\n", new ToCapsTranformer());

        post("/event", (req, res) -> {
            String name = req.queryParams("name");
            String date = req.queryParams("date");
            pretendDB.put(name, date);
            return ("Success\n");
        });

        get("/event/:eventname", (req, res) -> {
            String eventDate = "Unknown event";
            String eventName = req.params(":eventname");
            if (pretendDB.containsKey(eventName)) {
                eventDate = pretendDB.get(eventName);
                return "The " + eventName + " is scheduled for " + eventDate + "\n";
            }
            res.status(404);
            return (eventDate + "\n");
        });

        put("/event/:eventname", (req, res) -> {
            String eventDate = "Unknown event";
            String name = req.queryParams("name");
            String date = req.queryParams("date");
            if (pretendDB.containsKey(name)) {
                pretendDB.put(name, date);
                return "The " + name + " is rescheduled for " + date + "\n";
            }
            res.status(404);
            return (eventDate + "\n");
        });

        before((req, res) -> {
            System.out.println("Request IP: " + req.ip());
            System.out.println("Request Verb: " + req.requestMethod());
            System.out.println("Request user agent: " + req.userAgent());
        });

        exception(Exception.class, (e, req, res) -> {
            res.status(500);
            res.body("Server Error");
        });
    }


}
