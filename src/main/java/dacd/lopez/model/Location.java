package dacd.lopez.model;

public class Location {
    private double latitude;
    private double longitude;
    private String name;

    public Location(double latitude, double longitude, String name) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.name = name;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getName() {
        return name;
    }

    public String getCoordinatesAndName() {
        return "Latitud: " + latitude + ", Longitud: " + longitude + ", Localización: " + name;
    }

    public static void main(String[] args) {
        Location granCanaria = new Location(28.0997300, -15.4134300, "Las Palmas de Gran Canaria");
        System.out.println(granCanaria.getCoordinatesAndName());

        Location tenerife = new Location( 28.46824, -16.25462 , "Santa Cruz de Tenerife");
        System.out.println(tenerife.getCoordinatesAndName());

        Location fuerteventura = new Location(28.7307900, -13.8674900, "Corralejo - Fuerteventura");
        System.out.println(fuerteventura.getCoordinatesAndName());

        Location laPalma = new Location(28.4932900, -17.8501300 , "Fuencaliente de La Palma");
        System.out.println(laPalma.getCoordinatesAndName());

        Location elHierro = new Location(27.6851600 , -18.0590100, "El Pinar de El Hierro");
        System.out.println(elHierro.getCoordinatesAndName());

        Location laGraciosa = new Location(29.2523, -13.5091, "La Graciosa");
        System.out.println(laGraciosa.getCoordinatesAndName());

        Location lanzarote = new Location(28.0997300, -15.4134300, "Tiagua - Lnazarote");
        System.out.println(lanzarote.getCoordinatesAndName());

        Location laGomera = new Location(28.168611, -17.1966667, "Hermigua - La Gomera");
        System.out.println(laGomera.getCoordinatesAndName());
    }
}
