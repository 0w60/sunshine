package com.studyjamandroid.artem.sunshine;

class Weather {

    String city;
    String day;
    String temperature;
    String weathrCondtns;

    private Weather() {
    }

    Weather(String city, String day, String temperature, String weathrCondtns) {
        this.city = city;
        this.day = day;
        this.temperature = temperature;
        this.weathrCondtns = weathrCondtns;
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append(city)
                .append(' ')
                .append(day)
                .append(' ')
                .append(temperature)
                .append(' ')
                .append(weathrCondtns)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Weather)) return false;

        Weather weather = (Weather) o;
        if (!day.equals(weather.day)) return false;
        if (!city.equals(weather.city)) return false;
        if (!day.equals(weather.day)) return false;
        if (!temperature.equals(weather.temperature)) return false;
        if (!weathrCondtns.equals(weather.weathrCondtns)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        if ((day == null) || (temperature == null) || (weathrCondtns == null)) {
            return 0;
        }
        int result = city.hashCode();
        result = 31 * result + day.hashCode();
        result = 31 * result + temperature.hashCode();
        result = 31 * result + weathrCondtns.hashCode();
        return result;
    }
}
