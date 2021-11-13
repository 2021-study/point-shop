package product.demo.shop.healthcheck;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = HealthCheckController.PING_PATH)
public class HealthCheckController {
    public static final String PING_PATH = "/api/ping";

    @GetMapping("")
    public ResponseEntity<String>  healthCheck(){
        return ResponseEntity.ok("Check");
    }
}
