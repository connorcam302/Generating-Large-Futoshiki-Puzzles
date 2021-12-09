package futoshikigenerator;

import futoshikisolver.*;
import java.util.*;

public class InstanceGenerator {
	private int constraintAmount = 0;
	private int startingValueAmount = 0;
	
	
	// -- Getters and Setters --
	// Unsure if this is correct approach to design.
	public void setConstraintAmount(int i) {
		this.constraintAmount = i;
	}
	
	public int getConstraintAmount() {
		return this.constraintAmount;
	}
	
	public void setStartingValueAmount(int i) {
		this.startingValueAmount = i;
	}
	
	public int getStartingValueAmount() {
		return this.startingValueAmount;
	}
}
