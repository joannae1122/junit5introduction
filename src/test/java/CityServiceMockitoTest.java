import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CityServiceMockitoTest {


    @Mock
    private CityRepository cityRepository;

    @BeforeEach
    void setUp() {
        when(cityRepository.getCity()).thenReturn(new City(4, -100, "Goteborg"));
    }

    @Test
    void getCity(CityService cityService) {
        assertEquals("Goteborg", cityService.getCity().getName());
    }

}