package entity;

public class Message {
	private String msg;
	private int status;

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Message [msg=" + msg + ", status=" + status + "]";
	}

	public Message(String msg, int status) {
		super();
		this.msg = msg;
		this.status = status;
	}

}
