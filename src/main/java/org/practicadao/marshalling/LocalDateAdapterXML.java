package org.practicadao.marshalling;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LocalDateAdapterXML extends XmlAdapter<String, LocalDate> {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
    public LocalDate unmarshal(String v) throws Exception {
        return LocalDate.parse(v, formatter);  // Convierte de String a LocalDate
    }

    @Override
    public String marshal(LocalDate v) throws Exception {
        return v.format(formatter);  // Convierte de LocalDate a String
    }
}