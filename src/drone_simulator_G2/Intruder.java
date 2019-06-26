package drone_simulator_G2;

import java.util.List;

import repast.simphony.engine.schedule.ScheduledMethod;
import repast.simphony.engine.watcher.Watch;
import repast.simphony.engine.watcher.WatcherTriggerSchedule;
import repast.simphony.query.space.grid.GridCell;
import repast.simphony.query.space.grid.GridCellNgh;
import repast.simphony.random.RandomHelper;
import repast.simphony.space.SpatialMath;
import repast.simphony.space.continuous.ContinuousSpace;
import repast.simphony.space.continuous.NdPoint;
import repast.simphony.space.grid.Grid;
import repast.simphony.space.grid.GridPoint;
import repast.simphony.util.SimUtilities;

public class Intruder {
	private ContinuousSpace<Object> space;
	private Grid<Object> grid;

	public Intruder(ContinuousSpace<Object> space, Grid<Object> grid) {
		this.space = space;
		this.grid = grid;
		
	}
	

	/*When a SurveillanceDrone is near : the intruder move more*/
	@ScheduledMethod(start = 1, interval = 1)
	public void run() {
		// get the grid location of this Human
		GridPoint pt = grid.getLocation(this);
		// use the GridCellNgh class to create GridCells for
		// the surrounding neighborhood .
		GridCellNgh<SurveillanceDrone> nghCreator = new GridCellNgh<SurveillanceDrone>(grid, pt, SurveillanceDrone.class, 1, 1);
		List<GridCell<SurveillanceDrone>> gridCells = nghCreator.getNeighborhood(true);
		SimUtilities.shuffle(gridCells, RandomHelper.getUniform());

		GridPoint pointWithLeastSurveillanceDrone = null;
		int minCount = Integer.MAX_VALUE;
		for (GridCell<SurveillanceDrone> cell : gridCells) {
			if (cell.size() < minCount) {
				pointWithLeastSurveillanceDrone = cell.getPoint();
				minCount = cell.size();
			}
		}

		moveTowards(pointWithLeastSurveillanceDrone);


	}

	public void moveTowards(GridPoint pt) {
		// only move if we are not already in this grid location
		if (!pt.equals(grid.getLocation(this))) {
			NdPoint myPoint = space.getLocation(this);
			NdPoint otherPoint = new NdPoint(pt.getX(), pt.getY());
			double angle = SpatialMath.calcAngleFor2DMovement(space, myPoint, otherPoint);
			space.moveByVector(this, 2, angle, 0);
			myPoint = space.getLocation(this);
			grid.moveTo(this, (int) myPoint.getX(), (int) myPoint.getY());
		}

	}
}
