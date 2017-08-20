import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(CityServiceParameterResolver.class)
class CityServiceTest {

    @Test
    void getCity(CityService cityService) {
        assertEquals("Goteborg", cityService.getCity().getName());
    }
}