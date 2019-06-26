/**
 * 
 */
package drone_simulator_G2;

import repast.simphony.space.continuous.ContinuousSpace;
import repast.simphony.space.grid.Grid;

/**
 *  
 *
 */
public class Tree {

	protected ContinuousSpace<Object> space;
	protected Grid<Object> grid;
	/**
	 * @param space
	 * @param grid
	 */
	public Tree(ContinuousSpace<Object> space, Grid<Object> grid) {
		this.space = space;
		this.grid = grid;
	}
	
	
}
