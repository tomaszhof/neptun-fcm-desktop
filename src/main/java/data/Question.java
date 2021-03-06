package data;

public class Question {

	private Long id;
	
	private String code;

	private String text;

	private String answersCodes;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getAnswersCodes() {
		return answersCodes;
	}

	public void setAnswersCodes(String answersCodes) {
		this.answersCodes = answersCodes;
	}

}
