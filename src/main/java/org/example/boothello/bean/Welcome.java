package org.example.boothello.bean;


import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Objects;

public class Welcome {

	private String message;
	private String from;

	private String to;

	public Welcome() {
	}

	public Welcome(String message) {

		this.message = message;
	}

	public Welcome(String message, String from) {

		this.message = message;
		this.from = from;
	}

	public Welcome(String message, String from, String to) {

		this.message = message;
		this.from = from;
		this.to = to;
	}

	public String getMessage() {

		return this.message;
	}

	public void setMessage(String message) {

		this.message = message;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	@JsonIgnore
	public String getResponse() {
		StringBuilder sb = new StringBuilder("Welcome");
		if(this.getFrom() != null) {
			sb.append(" from " + from);
		}
		if(this.getTo() != null) {
			sb.append(" to " + to);
		}
		System.out.println("Response: " + sb);
		return sb.toString();
	}

	@Override
	public String toString() {
		return "Welcome{" +
				"message='" + message + '\'' +
				", from='" + from + '\'' +
				", to='" + to + '\'' +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Welcome)) return false;
		Welcome welcome = (Welcome) o;
		return Objects.equals(getMessage(), welcome.getMessage()) && Objects.equals(getFrom(), welcome.getFrom()) && Objects.equals(getTo(), welcome.getTo());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getMessage(), getFrom(), getTo());
	}
}
