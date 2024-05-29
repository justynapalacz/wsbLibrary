package palaczjustyna.library.actuator;

import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.stereotype.Component;

import java.time.Year;
import java.util.Map;

@Component
@Endpoint(id = "copyright")
public class CopyrightEndpoint {

    @ReadOperation
    public Map<String, String> copyright() {
        return Map.of("author", "Justyna Palacz", "year", Year.now().toString());
    }
}
