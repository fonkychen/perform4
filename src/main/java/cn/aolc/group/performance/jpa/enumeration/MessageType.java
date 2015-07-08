package cn.aolc.group.performance.jpa.enumeration;

public enum MessageType{
	DrumEventType{
		public String toString(){
			return "得胜鼓消息";
		}
	},
	ContactEventType{
		public String toString(){
			return "个人消息";
		}
	},
	AchieveEventType{
		public String toString(){
			return "成就消息";
		}
	},
	PopularEventType{
		public String toString(){
			return "人气消息";
		}
	}
}
