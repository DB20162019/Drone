package drone_simulator_G2;

import java.util.ArrayList;
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

public class DeliverDrone extends Drone {
	private boolean dejaTrouvePackage ;
	private boolean hasTask;
	private Package task;
	private ArrayList<Package> tasks;

	
	public DeliverDrone(ContinuousSpace<Object> space, Grid<Object> grid, int charge) {
		super(space, grid, charge);
	    dejaTrouvePackage = false;
	    this.hasTask = false;

	}
	
	// method that implement the functional behavior of the drone
	// it is called each 1 second
	@Override
	@ScheduledMethod(start = 1, interval = 1)
	public void doTask()
	{
		// the syncrhonized block is to forbide two drones to not tka the same package
		synchronized(this)
		{
			if(!this.hasTask)
		    {
				// All DeliverDrone share the same list of task(package)
				// Eache one take one available package and lock it, and try to deliver on the exact building
		    	for(Package pa : this.tasks)
		    	{
		    		// if there is a package that has not been delivered yet, then this drone take and go 
		    		// to deliver on the exact building
		    		if(!pa.isTaken())
		    		{
		    			this.setTask(pa);
		    			pa.setTaken(true);
		    			this.setHasTask(true);
		    			break;
		    		}
		    	}
		    }
		}
		 
		if(this.getCharge() > 0)
		{
			//if the package isn't found yet, so continue to looking for
			if(!dejaTrouvePackage)
			{
			  //get the package location and go find him
				if(this.hasTask) {
				   //	localisationPackage = grid.getLocation(this.getTask());
				  //findPackage method,  change the state of the "dejaTrouvePackage" variable, if the package is found
				  findPackage(grid.getLocation(this.getTask()));
				}
			}
			else 
			{
				
				// if the package has been found, then we can go find the place to deliver him
				if(this.hasTask)
				{
					move(getTask().getDestinationCoord());
				}
			}
		}
		else {
			
			// code to search a dockstation to charge
			if(!this.charging)
			{
				for(DockStation dock : docks)
				{
					if(!dock.isBusy())
					{
						
						this.setDock(dock);
						break;
					}
				}
			}
			if(!this.getDock().isBusy())
				findDockStation(this.getDock().getPositon());
			
		}
		

	}
	// method that move the Drone to a desired location on the scene(screen), we just need to give in the location
	// This method is used to move the to the building where the package will be delivered
	@Override
	public void move(GridPoint pt)
	{
		GridPoint actualLocation = grid.getLocation(this); 
		double distance = Math.hypot(pt.getX()-actualLocation.getX(), pt.getY()-actualLocation.getY());
		if(distance > 1)	{
			NdPoint myPoint = space.getLocation(this);
			NdPoint otherPoint = new NdPoint(pt.getX(),pt.getY());
			double angle = SpatialMath.calcAngleFor2DMovement (space,myPoint , otherPoint );
			space.moveByVector(this, 1, angle,0);
			myPoint = space.getLocation(this);
			grid.moveTo ( this ,( int )myPoint.getX (), ( int )myPoint.getY ());
			// This line allow the drone to move the package with him, while he is trying to looking for the building to leave the package.
			// This is why the drone pass himself as an argument to the Package move method, so that the package can get the drone's position
			// and follow him.

			if((int)myPoint.getX() == (int)otherPoint.getX() || (int)myPoint.getY() == (int)otherPoint.getY())
			{
				this.hasTask = false;	
				dejaTrouvePackage = false;

			}
			else
			{
				this.getTask().move(this);
			}
			this.setCharge(this.getCharge()-1);
			
		}

	}
	// method that move the Drone to a desired location on the scene(screen), it used to 
	// find the package that has been assigned to him, to be delivered.
	public void findPackage(GridPoint pt)
	{
		
		if(!pt.equals(grid.getLocation(this)))
		{
			NdPoint myPoint = space.getLocation(this);
			NdPoint otherPoint = new NdPoint(pt.getX(),pt.getY());
			double angle = SpatialMath.calcAngleFor2DMovement (space,myPoint , otherPoint );
			space.moveByVector(this, 1, angle,0);
			myPoint = space.getLocation(this);
			grid.moveTo ( this ,( int )myPoint.getX (), ( int )myPoint.getY ());
			
			// if the drone has found the package, the we change the state of the variable
			// so that the drone stop looking for the package and start looking for the building
			if((int)myPoint.getX() == (int)otherPoint.getX() || (int)myPoint.getY() == (int)otherPoint.getY())
			{
				dejaTrouvePackage = true;
			}
			
			this.setCharge(this.getCharge()-1);
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
				dejaTrouvePackage = false;
			}
			
		}
	}

	public ArrayList<Package> getTasks() {
		return tasks;
	}

	public void setTasks(ArrayList<Package> tasks) {
		this.tasks = tasks;
	}
	
	public boolean hasTask() {
		return hasTask;
	}

	public void setHasTask(boolean hasTask) {
		this.hasTask = hasTask;
	}

	public boolean isDejaTrouvePackage() {
		return dejaTrouvePackage;
	}

	public void setDejaTrouvePackage(boolean dejaTrouvePackage) {
		this.dejaTrouvePackage = dejaTrouvePackage;
	}

	
	// method to get the task assigned to the drone
	
	// method to assign a task on the drone
	public void setTask(Package task) {
		this.task = task;
		this.dejaTrouvePackage = false;
	}
	
	public Package getTask() {
		return task;
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
	public void run() {
		/*different step of drone*/
	}
	
	@Override
	public GridPoint  getPosition() {
		/*return drone position*/
		return new GridPoint();
	}

}