package per.llc.bean;

public class Method {
	//default values
	private String bean;
	private String method;
	private boolean valid = false;
	private String maintainer = "undistributed";
	private String memo = "null";
	
	public Method(String bean, String method) {
		this.bean = bean;
		this.method = method;
	}
	public String getBean() {
		return bean;
	}
/*	public void setBean(String bean) {
		this.bean = bean;
	}*/
	public String getMethod() {
		return method;
	}
/*	public void setMethod(String method) {
		this.method = method;
	}*/
	public boolean isValid() {
		return valid;
	}
	public void setValidity(boolean valid) {
		this.valid = valid;
	}
	public String getMaintainer() {
		return maintainer;
	}
	public void setMaintainer(String maintainer) {
		this.maintainer = maintainer;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
}
