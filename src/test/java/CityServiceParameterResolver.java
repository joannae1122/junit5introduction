import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CityServiceParameterResolver implements ParameterResolver {


    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.getParameter().getType() == CityService.class;
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        CityRepository cityRepository = mock(CityRepository.class);
        when(cityRepository.getCity()).thenReturn(new City(4, -100, "Goteborg"));
        return new CityService(cityRepository);
    }
}
