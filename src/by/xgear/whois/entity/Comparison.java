package by.xgear.whois.entity;

public class Comparison {

	private Result result;
	private Input input;
	
	public Comparison() {
		super();
	}
	
	public Comparison(Result result, Input input) {
		super();
		this.result = result;
		this.input = input;
	}
	
	public Result getResult() {
		return result;
	}
	public void setResult(Result result) {
		this.result = result;
	}
	public Input getInput() {
		return input;
	}
	public void setInput(Input input) {
		this.input = input;
	}
	
}