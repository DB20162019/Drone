package drone_simulator_G2;

import repast.simphony.space.continuous.ContinuousSpace;
import repast.simphony.space.grid.Grid;


/*
 *  class to represent the building in a street
 *  
 *  Don't need comment, it is pretty straight forward.
 * 
 */

public class Building {
	private ContinuousSpace<Object> space;
	private Grid<Object> grid;
	private DockStation dockstation;
	
	public Building(ContinuousSpace<Object> space, Grid<Object> grid) {
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
	
	public DockStation getDockstation() {
		return dockstation;
	}

	public void setDockstation(DockStation dockstation) {
		this.dockstation = dockstation;
	}

}