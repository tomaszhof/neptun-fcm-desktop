package components;

import javax.swing.JCheckBox;

public class AnswerCheckBox extends JCheckBox {
	private String answerCode = "NO_ANSWER";

	public AnswerCheckBox(String answerCode, String answerText) {
		super(answerText);
		this.answerCode = answerCode;
	}
	public String getAnswerCode() {
		return answerCode;
	}

	public void setAnswerCode(String answerCode) {
		this.answerCode = answerCode;
	}
	
}
