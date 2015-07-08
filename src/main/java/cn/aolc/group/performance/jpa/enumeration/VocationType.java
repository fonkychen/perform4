package cn.aolc.group.performance.jpa.enumeration;

public enum VocationType {
	YearlyVocation{
		public String toString(){
			return "年假";
		}
	},
	EventVocation{
		public String toString(){
			return "事假";
		}
	},
	SickVocation{
		public String toString(){
			return "病假";
		}
	},
	MarryVocation{
		public String toString(){
			return "婚假";
		}
	},
	BirthVocation{
		public String toString(){
			return "产假";
		}
	}

}
