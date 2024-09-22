package elca.ntig.partnerapp.be.utils.mapper;

import com.google.protobuf.Timestamp;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Component
public class DateMapper {

    @Named("mapLocalDateToString")
    public static String mapLocalDateToString(LocalDate localDate) {
        if (localDate == null) {
            return "";
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return localDate.format(formatter);
    }

    @Named("mapStringToLocalDate")
    public static LocalDate mapStringToLocalDate(String date) {
        if (date == null || date.trim().isEmpty()) {
            return null;
        }
        return LocalDate.parse(date, DateTimeFormatter.ISO_LOCAL_DATE);
    }
}
