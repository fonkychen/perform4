package cn.aolc.group.performance.jpa.enumeration;

public enum VocationEventType {
	
	NotProcessed{
		public String toString(){
			return "待审核";
		}
	},
	GrantedByHr{
		public String toString(){
			return "HR已同意";
		}
	},
	GrantedBySupervisor{
		public String toString(){
			return "主管已同意";
		}
	},
	Passed{
		public String toString(){
			return "已通过";
		}
	},
	Denied{
		public String toString(){
			return "已拒绝";
		}
	}

}
