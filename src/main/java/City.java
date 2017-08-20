import static java.lang.Math.sqrt;

class City {
    private int x;
    private int y;
    private String name;

    City(int x, int y, String name) {
        this.x = x;
        this.y = y;
        this.name = name;
    }

    Integer getDistanceFromOslo() {
        int dist = (int) sqrt(Math.pow(x, 2) + Math.pow(y, 2));
        if(dist > 1000){
            throw new IllegalArgumentException("City is too far away");
        }
        return dist;
    }

    String getName() {
        return name;
    }
}
