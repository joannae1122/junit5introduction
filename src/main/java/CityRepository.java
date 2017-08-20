import org.springframework.stereotype.Repository;

@Repository
class CityRepository {
    City getCity() {
        //get City from database
        return new City(200, -1, "Stockholm");
    }
}
