package drone_simulator_G2;

import repast.simphony.space.continuous.ContinuousSpace;
import repast.simphony.space.grid.Grid;
import repast.simphony.space.grid.GridPoint;


/*
 * 
 * class to describe general behavior of a task,
 *  in this software exist two implementation of task which are ( DeliverTask and SurveillanceTask )
 * 
 */
public class Task {
	private ContinuousSpace<Object> space;
	private Grid<Object> grid;
	
	

	public Task(ContinuousSpace<Object> space, Grid<Object> grid) {
		
		this.space = space;
		this.grid = grid;
		
	}
	
	public ContinuousSpace<Object> getSpace() {
		return space;
	}
	
	public void setSpace(ContinuousSpace<Object> space) {
		this.space = space;
	}
	public Grid<Object> getGrid() {
		return grid;
	}
	public void setGrid(Grid<Object> grid) {
		this.grid = grid;
	}

	
}