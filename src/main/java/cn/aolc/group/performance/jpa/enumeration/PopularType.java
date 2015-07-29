package cn.aolc.group.performance.jpa.enumeration;

public enum PopularType {
	@Deprecated Flower,
	@Deprecated Egg,
	PopularAction{
		public String toString(){
			return "送花砸蛋";
		}
	};
	
	
}
