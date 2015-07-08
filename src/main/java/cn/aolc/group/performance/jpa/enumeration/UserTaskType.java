package cn.aolc.group.performance.jpa.enumeration;

public enum UserTaskType {
	None{
		public String toString(){
			return "快抢任务";
		}
	},
	Daily{
		public String toString(){
			return "循环任务——每日";
		}
	},
	Weekly{
		public String toString(){
			return "循环任务——每周";
		}
	}
	,
	Monthly{
		public String toString(){
			return "循环任务——每月";
		}
	}

}
