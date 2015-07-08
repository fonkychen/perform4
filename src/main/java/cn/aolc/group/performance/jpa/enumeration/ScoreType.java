package cn.aolc.group.performance.jpa.enumeration;

public  enum ScoreType{
	DAILYBOARD_SCORE{
		public String toString(){
			return "公事榜";
		}
	},
	SELF_MARK_SCORE{
		public String toString(){
			return "每日自评";
		}
	},
	WEEKLY_COMMENT_SCORE{
		public String toString(){
			return "周评";
		}
	},
	MERIT_ACCEPT_SCORE{
		public String toString(){
			return "功勋簿";
		}
	},
	RANDOM_COMMENT_SCORE{
		public String toString(){
			return "有缘点评";
		}
	},
	WEEKLY_PENALTY_SCORE{
		public String toString(){
			return "周评未评";
		}
	},
	RANDOM_PENALTY_SCORE{
		public String toString(){
			return "有缘未评";
		}
	},
	MONTHLY_INDICATOR_SCORE{
		public String toString(){
			return "指标加成";
		}
	}
};
