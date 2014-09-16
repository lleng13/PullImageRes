package per.llc.bean;

public class Method {
	//default values
	private String bean = "null";
	private String method = "null";
	private boolean valid = true;
	private String maintainer = "undistributed";
	private String memo = "null";
	public static final String[] DES = {
		"接口名称", "方法名称" , "完成情况（灰色为忽略）" , "负责人" , "备注"
	};
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
