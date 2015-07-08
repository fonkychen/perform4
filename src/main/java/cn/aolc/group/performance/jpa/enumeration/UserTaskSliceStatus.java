package cn.aolc.group.performance.jpa.enumeration;

public enum UserTaskSliceStatus {
	
	OnGoing{
		public String toString(){
			return "正在进行中";
		}
	},
	Closed{
		public String toString(){
			return "已关闭";
		}
	}

}
