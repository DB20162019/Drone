package drone_simulator_G2;

import java.util.ArrayList;
import java.util.List;

import repast.simphony.context.Context;
import repast.simphony.engine.schedule.ScheduledMethod;
import repast.simphony.query.space.grid.GridCell;
import repast.simphony.query.space.grid.GridCellNgh;
import repast.simphony.random.RandomHelper;
import repast.simphony.space.SpatialMath;
import repast.simphony.space.continuous.ContinuousSpace;
import repast.simphony.space.continuous.NdPoint;
import repast.simphony.space.grid.Grid;
import repast.simphony.space.grid.GridPoint;
import repast.simphony.util.ContextUtils;
import repast.simphony.util.SimUtilities;

public class SurveillanceDrone extends Drone{
	private boolean moved;

	public SurveillanceDrone(ContinuousSpace<Object> space, Grid<Object> grid , int charge) {
		super(space, grid, charge);
	}
	
	@Override
	public ContinuousSpace<Object> getSpace() {
		return space;
	}

	@Override
	public void setSpace(ContinuousSpace<Object> space) {
		this.space = space;
	}

	@Override
	public Grid<Object> getGrid() {
		return grid;
	}

	@Override
	public void setGrid(Grid<Object> grid) {
		this.grid = grid;
	}

	@Override
	public void communicate() {
		/*Communication between 2 or more drone or with other smart things */
	}
	
	@Override
	public void negociate() {
		/*negotiate between 2 or more drones */
	}
	
	@Override
	public void move(GridPoint pt) {
		/*code for movement of drone*/
		if( !pt.equals(grid.getLocation(this))) {
			/*move to search intruder and to rejoin intruder*/
			NdPoint myPoint = space.getLocation(this);
			NdPoint otherPoint = new NdPoint(pt.getX(), pt.getY());
			double angle = SpatialMath.calcAngleFor2DMovement(space, myPoint,
					otherPoint);
			
			/*How the drone have to move*/
			space.moveByVector(this ,1, angle, 0);
			/*he moves in direction of the intruder*/
			myPoint = space.getLocation(this);
			grid.moveTo(this ,( int )myPoint.getX (), ( int )myPoint.getY ());
			moved = true;
			this.setCharge(this.getCharge()-1);
			
		}
		
	}
	
	@Override
	@ScheduledMethod(start = 1, interval = 1)
	public void doTask() {
		// get the grid location of this Drone
		GridPoint pt = grid.getLocation(this);

		// use the GridCellNgh class to create GridCells for the surrounding neighborhood.
		GridCellNgh<Intruder> nghCreator = new GridCellNgh<Intruder>(grid, pt,
				Intruder.class, 30, 300);
		List<GridCell<Intruder>> gridCells = nghCreator.getNeighborhood(true);
		SimUtilities.shuffle(gridCells, RandomHelper.getUniform());
		
		GridPoint intruder = null;
		int maxCount = -1;
		

		for (GridCell<Intruder> cell : gridCells) {
			
			if (cell.size() > maxCount) {
				intruder = cell.getPoint();
				maxCount = cell.size();

			}
			
		}
		/*To run the method move and detect*/
		
		if(this.getCharge() > 0)
		{
			move(intruder);	
			detect();
		}
		else{
			// test to know if the drone is charging
			if(!this.charging)
			{
				for(DockStation dock : docks)
				{
					// just take the dockstation that is free
					if(!dock.isBusy())
					{
						// if is not busy then the drone go there
						this.setDock(dock);
						break;
					}
				}
			}
			// test to know if the dockstation nearby is occupied or not
			// if it is occupied then he doesn't move until the dockstation is free
			if(!this.getDock().isBusy())
			{
				findDockStation(this.getDock().getPositon());
			}

		}
	}
	@Override
	public void findDockStation(GridPoint pt)
	{
		if(!pt.equals(grid.getLocation(this)) )
		{
			NdPoint myPoint = space.getLocation(this);
			NdPoint otherPoint = new NdPoint(pt.getX(),pt.getY());
			double angle = SpatialMath.calcAngleFor2DMovement (space,myPoint , otherPoint );
			space.moveByVector(this, 1, angle,0);
			myPoint = space.getLocation(this);
			grid.moveTo ( this ,( int )myPoint.getX (), ( int )myPoint.getY ());
			
			// if the drone has found the package, the we change the state of the variable
			// so that the drone stop looking for the package and start looking for the building
			if(((int)myPoint.getX() == (int)otherPoint.getX() || (int)myPoint.getY() == (int)otherPoint.getY()) && !this.getDock().isBusy() )
			{
				int countCharge=0;
				
				this.getDock().setBusy(true);
				this.charging = true;
			
				while(countCharge < 100000)
				{
					
					if(countCharge > 90000)
					   this.setCharge(200);
					
					countCharge++;
				}
				charging = false;
				this.getDock().setBusy(false);
				
			}
			
		}
	}
	public void detect() {
		/*method to detect an intruder*/
		GridPoint pt = grid.getLocation(this);
		List<Object> intruder = new ArrayList<Object>();
		for (Object obj : grid.getObjectsAt(pt.getX(), pt.getY())) {
			if (obj instanceof Intruder) {
				intruder.add(obj);
			}
		}

		if (intruder.size() > 0) {
			/*to know the number of intruder to detect and to remove them from this simulation*/
			int index = RandomHelper.nextIntFromTo(0, intruder.size() - 1);
			Object obj = intruder.get(index);
			//NdPoint spacePt = space.getLocation(obj);
			Context<Object> context = ContextUtils.getContext(obj);
			
			/*if intruder is detected then he is remove from the simulation*/
			context.remove(obj);

		}
	}
	
	@Override
	public GridPoint  getPosition() {
		/*return drone position*/
		return new GridPoint();
	}

}
