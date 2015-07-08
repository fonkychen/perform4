package cn.aolc.group.performance.jpa.enumeration;

public enum RoleType {
	
	NONE{
		public String toString(){
			return "空";
		}
	},
	USER{
		public String toString(){
			return "普通用户";
		}
	},
	ADMIN{
		public String toString(){
			return "管理员";
		}
	},
	SYSADMIN{
		public String toString(){
			return "系统管理员";
		}
	}

}
