//package com.docTime.simulations;
//
//import io.gatling.javaapi.core.*;
//import io.gatling.javaapi.http.*;
//import java.util.*;
//import java.util.concurrent.atomic.AtomicInteger;
//import java.util.stream.Stream;
//import static io.gatling.javaapi.core.CoreDsl.*;
//import static io.gatling.javaapi.http.HttpDsl.*;
//
//public class PatientLoadTest extends Simulation {
//
//    HttpProtocolBuilder httpProtocol = http
//            .baseUrl("http://localhost:8080")
//            .acceptHeader("application/json")
//            .contentTypeHeader("application/json");
//
//    AtomicInteger counter = new AtomicInteger(0);
//
//    Iterator<Map<String, Object>> feeder = Stream.generate(() -> {
//        int id = counter.incrementAndGet();
//        int phoneBase = 70000000 + (id % 9999999);
//        Map<String, Object> map = new HashMap<>();
//        map.put("firstName", "Patient_" + id);
//        map.put("lastName", "Ndayi_" + id);
//        map.put("email", "patient." + id + "@bi-sante.com");
//        map.put("phone", String.valueOf(phoneBase));
//        map.put("countryCode", "BI");
//        map.put("birthDate", "1990-05-12");
//        return map;
//    }).iterator();
//
//    ScenarioBuilder scn = scenario("Test de robustesse Patient")
//            .feed(feeder)
//            .exec(http("POST Créer Patient")
//                    .post("/api/patients/create")
//                    .body(StringBody("{"
//                            + "\"firstName\":\"#{firstName}\","
//                            + "\"lastName\":\"#{lastName}\","
//                            + "\"email\":\"#{email}\","
//                            + "\"phone\":\"#{phone}\","
//                            + "\"countryCode\":\"#{countryCode}\","
//                            + "\"birthDate\":\"#{birthDate}\""
//                            + "}"))
//                    .check(status().is(201))); // Vérifie que le serveur répond HTTP 201 Created
//
//    {
//        // Stratégie d'injection : Montée progressive jusqu'à 1000 utilisateurs en 30 secondes
//        setUp(
//                scn.injectOpen(
//                        rampUsers(1000).during(30)
//                )
//        ).protocols(httpProtocol);
//    }
//}
