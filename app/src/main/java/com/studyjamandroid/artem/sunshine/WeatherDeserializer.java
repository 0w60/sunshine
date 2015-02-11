package com.studyjamandroid.artem.sunshine;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Date;

class WeatherDeserializer implements JsonDeserializer<Weather[]> {

    @Override
    public Weather[] deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jObject = json.getAsJsonObject();
        String city = jObject.get("city").getAsJsonObject().get("name").getAsString();
        JsonArray listJArray = jObject.get("list").getAsJsonArray();

        Weather[] weatherArray = new Weather[listJArray.size()];
        int index = 0;
        for (JsonElement jElem : listJArray) {
            long dateInMilisecnds = jElem.getAsJsonObject().get("dt").getAsLong();
            String date = getReadableDateString(dateInMilisecnds);
            float temperatureMin = jElem.getAsJsonObject().get("temp").getAsJsonObject().get("min").getAsFloat();
            float temperatureMax = jElem.getAsJsonObject().get("temp").getAsJsonObject().get("max").getAsFloat();
            String temperature = formatHighLows(temperatureMax, temperatureMin);
            JsonArray weatherJArray = jElem.getAsJsonObject().get("weather").getAsJsonArray();
            String weatherCondtns = weatherJArray.get(0).getAsJsonObject().get("description").getAsString();
            weatherArray[index++] = new Weather(city, date, temperature, weatherCondtns);
        }
        return weatherArray;
    }

    private String getReadableDateString(long time) {
        Date date = new Date(time * 1000);
        SimpleDateFormat format = new SimpleDateFormat("E, MMM d");
        return format.format(date);
    }

    private String formatHighLows(float high, float low) {
        int roundedHigh = Math.round(high);
        int roundedLow = Math.round(low);
        String highLowStr = roundedHigh + "/" + roundedLow;
        return highLowStr;
    }
}
