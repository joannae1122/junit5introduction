import org.junit.jupiter.api.*;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.*;

class CityTest {

    @Nested
    @DisplayName("Distance to ")
    class DistanceToCity {

        @Test
        @DisplayName("Oslo is 0")
        void distanceToOslo() {
            City city = new City(0, 0, "Oslo");
            assertEquals(new Integer(0), city.getDistanceFromOslo());
        }

        @Test
        @DisplayName("Bergen is 10")
        void distanceToBergen() {
            City city = new City(-10, 2, "Bergen");
            assertEquals(new Integer(10), city.getDistanceFromOslo());
        }

        @Test
        @DisplayName("Trondheim is 15")
        void distanceToTrondheim() {
            City city = new City(-2, 15, "Trondheim");
            assertEquals(new Integer(15), city.getDistanceFromOslo());
        }

        @Test
        @DisplayName("Fredrikstad is 3")
        void distanceToFredrikstad() {
            City city = new City(1, -3, "Fredrikstad");
            assertEquals(new Integer(3), city.getDistanceFromOslo());
        }
    }

    @Test
    void calculateTotalDistance() {

        List<City> cities = getCities();

        List<Integer> distances = getDistances();
        int totalDistance = distances.stream().mapToInt(i -> i).sum();
        assertEquals(totalDistance, cities.stream().mapToInt(city -> city.getDistanceFromOslo() * 2).sum(),
                () -> "visiting all" +
                        " cities should give total distance of " + totalDistance);
    }

    @Test
    void exceptionTesting() {
        City city = new City(10, 100000, "Longyearbyen");
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, city::getDistanceFromOslo);

        assertEquals("City is too far away", exception.getMessage());
    }

    private String getErrorMessage(City city, int dist) {
        return "Distance to " + city.getName() + " should be " + dist;
    }

    @Test
    void calcluateDistanceWithAssertAll() {
        assertAll("distances from capital",
                () -> {
                    City city = new City(0, 0, "Oslo");
                    int dist = 0;
                    assertEquals(new Integer(dist), city.getDistanceFromOslo(), getErrorMessage(city, dist));
                },
                () -> {
                    City city = new City(-10, 2, "Bergen");
                    int dist = 10;
                    assertEquals(new Integer(dist), city.getDistanceFromOslo(), getErrorMessage(city, dist));
                },
                () -> {
                    City city = new City(-2, 15, "Trondheim");
                    int dist = 16;
                    assertEquals(new Integer(dist), city.getDistanceFromOslo(), getErrorMessage(city, dist));
                },
                () -> {
                    City city = new City(1, -3, "Fredrikstad");
                    int dist = 30;
                    assertEquals(new Integer(dist), city.getDistanceFromOslo(), getErrorMessage(city, dist));
                }
        );
    }

    private List<Integer> getDistances() {
        return asList(0, 10, 15, 3);
    }

    private static List<City> getCities() {
        return asList(
                new City(0, 0, "Oslo"),
                new City(-10, 2, "Bergen"),
                new City(-2, 150, "Trondheim"),
                new City(1, -3, "Fredrikstad"));
    }

    @Test
    void calculateDistanceWithLoop() {
        List<City> cities = getCities();
        List<Integer> distances = getDistances();
        for (int i = 0; i < cities.size(); i++) {
            City city = cities.get(i);
            Integer distance = distances.get(i);
            assertEquals(distance, city.getDistanceFromOslo(), "Distance to " + city.getName() + " should be " + distance);
        }
    }

    @TestFactory
    Collection<DynamicTest> calculateDistanceWithCollection() {

        Collection<DynamicTest> dynamicTests = new ArrayList<>();
        List cities = getCities();

        List<Integer> distances = getDistances();

        for (int i = 0; i < cities.size(); i++) {
            final City city = (City) cities.get(i);
            final Integer distance = distances.get(i);
            Executable executable = () -> assertEquals(distance, city.getDistanceFromOslo());

            String testName = "Test of distance to " + city.getName();

            DynamicTest dTest = DynamicTest.dynamicTest(testName, executable);
            dynamicTests.add(dTest);
        }
        return dynamicTests;

    }

    @TestFactory
    Stream<DynamicTest> calculateDistanceWithStream() {

        List<City> cities = getCities();

        List<Integer> distances = getDistances();

        return cities.stream().map(city -> DynamicTest.dynamicTest("Test of distance to " + city.getName(), ()
                -> {
            int idx = cities.indexOf(city);
            assertEquals(distances.get(idx), city.getDistanceFromOslo());
        }));

    }

    @ParameterizedTest
    @MethodSource("getCities")
    void calculateDistanceWithMethodInput(City city) {
        assertTrue(city.getName().length() > 3, city.getName() + " should be longer than three characters");
    }

    //@ParameterizedTest
    @ParameterizedTest(name = "x = {0}, y = {1}, name = {2}")
    @CsvSource({"0, 0, Oslo", "-10, 2, Bergen", "-2, 15, Trondheim", "1, -3, Fredrikstad"})
    void calculateDistanceWithCsvInput(int x, int y, String cityName) {
        City city = new City(x, y, cityName);
        assertTrue(city.getDistanceFromOslo() >= 0 && city.getDistanceFromOslo() < 500);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/cities.csv")
    void calculateDistanceWithCsvFile(int x, int y, String cityName, Integer distance) {
        City city = new City(x, y, cityName);
        assertEquals(distance, city.getDistanceFromOslo(), getErrorMessage(city, distance));
    }
}