package com.example.propertysearcherprojectbackend.domain;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LocalDateAdapter extends TypeAdapter<LocalDate> {

    private static final LocalDateAdapter INSTANCE = new LocalDateAdapter();
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private LocalDateAdapter() {
    }

    public static LocalDateAdapter getInstance() {
        return INSTANCE;
    }

    @Override
    public void write(JsonWriter out, LocalDate value) throws IOException {
        if (value == null) {
            out.nullValue();
        } else {
            out.value(formatter.format(value));
        }
    }

    @Override
    public LocalDate read(JsonReader in) throws IOException {
        if (in.hasNext()) {
            String value = in.nextString();
            return LocalDate.parse(value, formatter);
        } else {
            return null;
        }
    }
}