package cn.aolc.group.performance.jpa.enumeration;

public enum CoinType {
	DrumEvent{
		public String toString(){
			return "得胜鼓";
		}
	},
	SelfMark{
		public String toString(){
			return "每日自评";
		}
	},
	DailyBoard{
		public String toString(){
			return "公事榜";
		}
	},
	Attend{
		public String toString(){
			return "签到";
		}
	},
	Popular{
		public String toString(){
			return "人气英雄";
		}
	},
	@Deprecated
	Reward{
		public String toString(){
			return "悬赏榜";
		}
	},
	@Deprecated
	Task{
		public String toString(){
			return "任务中心";
		}
	},
	Achieve{
		public String toString(){
			return "成就中心";
		}
	},
	LuckyTable{
		public String toString(){
			return "幸运转盘";
		}
	},
	ShopMall{
		public String toString(){
			return "财富商城";
		}
	},
	SendReward{
		public String toString(){
			return "发布悬赏";
		}
	},
	SendTask{
		public String toString(){
			return "发布任务";
		}
	},
	QuarterlyWealthBonus{
		public String toString(){
			return "季度UB奖";
		}
	},
	@Deprecated
	ResponseTask{
		public String toString(){
			return "领取任务";
		}
	},
	@Deprecated
	ResponseReward{
		public String toString(){
			return "领取悬赏";
		}
	},
	AcceptReward{
		public String toString(){
			return "确认悬赏";
		}
	},
	TitleSalary{
		public String toString(){
			return "官职薪俸";
		}
	},
	AcceptRewardOut{
		public String toString(){
			return "确认他人悬赏";
		}
	},
	AcceptTask{
		public String toString(){
			return "确认任务";
		}
	}
}
