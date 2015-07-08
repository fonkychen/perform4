package cn.aolc.group.performance.jpa.enumeration;

public enum UserStatus{
	Newbie{
		public String toString(){
			return "新人";
		}
	},
	Onduty{
		public String toString(){
			return "在职";
		}
	},
	Retired{
		public String toString(){
			return "离职";
		}
	},
	Locked{
		public String toString(){
			return "锁定";
		}
	}
}