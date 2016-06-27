package me.it.hes.war;

public enum AttackType {
	
	SPACE(0.01f), AIR(0.1f), GROUND(1f);
	
	float riskRewardRatio;
	
	private AttackType(float riskreward) {
		this.riskRewardRatio = riskreward;
	}

}
