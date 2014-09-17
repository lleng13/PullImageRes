package per.llc.bean;

public class Method implements Comparable<Method>{
	//default values
	private String bean = "null";
	private String method = "null";
	private boolean valid = true;
	private String schedule = "undone";
	private String maintainer = "undistributed";
	private String memo = "null";
	private String status = MethodStatus.OLD;
	
	public static final String[] DES = {
		"接口名称", "方法名称" , "完成情况" , "是否有效（灰色为无效）", "负责人" , "备注" , "Status"
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
	public String getSchedule() {
		return schedule;
	}
	public void setSchedule(String schedule) {
		this.schedule = schedule;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Override
	public String toString() {
		String str = getBean()+" "+getMethod()+ " " +getSchedule()+ " " +isValid()+ " " +getMaintainer()+ " " +getMemo();
		return str;
	}
	@Override
	public int compareTo(Method method) {
		int result = this.bean.compareTo(method.getBean());
		if(result > 0) {
			return 1;
		} else if(result == 0) {
			return 0;
		} else {
			return -1;
		}
	}

}
