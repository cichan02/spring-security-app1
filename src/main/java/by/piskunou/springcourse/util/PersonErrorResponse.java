package by.piskunou.springcourse.util;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PersonErrorResponse {
	private String message;
	private long timestamp;
}

