package main.api.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;
import java.util.Set;

@Data
@AllArgsConstructor
public class CalendarResponse {
    private Set<String> years;
    private Map<String, Integer> posts;
}
