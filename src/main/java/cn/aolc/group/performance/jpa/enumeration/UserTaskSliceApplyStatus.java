package cn.aolc.group.performance.jpa.enumeration;

public enum UserTaskSliceApplyStatus {

	Not_Processed{
		public String toString(){
			return "未处理";
		}
	},
	Rejected{
		public String toString(){
			return "已拒绝";
		}
	},
	Accept{
		public String toString(){
			return "已接受";
		}
	},
	Close{
		public String toString(){
			return "已关闭";
		}
	}
}
