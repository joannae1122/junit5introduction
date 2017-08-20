import org.springframework.stereotype.Service;

@Service
class CityService {

    private CityRepository cityRepository;

    CityService(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    City getCity() {
        return cityRepository.getCity();
        //Gets city from t ex a database
    }
}
