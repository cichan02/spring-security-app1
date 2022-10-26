package by.piskunou.springcourse.util;

public class PersonNotRegistratedException extends RuntimeException {
	private static final long serialVersionUID = 8397186750590501795L;

	public PersonNotRegistratedException(String message) {
		super(message);
	}
}
