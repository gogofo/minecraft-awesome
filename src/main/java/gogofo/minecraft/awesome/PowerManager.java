package gogofo.minecraft.awesome;

import java.util.ArrayList;
import java.util.HashMap;

import gogofo.minecraft.awesome.tileentity.AwesomeTileEntityMachine;
import net.minecraft.util.BlockPos;

public class PowerManager {
	public static PowerManager instance = new PowerManager();
	
	private HashMap<BlockPos, Integer> powerGrid = new HashMap<BlockPos, Integer>();
	private HashMap<BlockPos, AwesomeTileEntityMachine> machineGrid = 
						new HashMap<BlockPos, AwesomeTileEntityMachine>();
	
	public void registerMachine(AwesomeTileEntityMachine machine) {
		if (machineGrid.containsKey(machine.getPos())) {
			return;
		}
		
		machineGrid.put(machine.getPos(), machine);
		
		
		addToGrid(machine.getPos(), 
				  machine.powerGenerated() - machine.powerRequired(),
				  true);
	}

	public void unregisterMachine(AwesomeTileEntityMachine machine) {
		if (!machineGrid.containsKey(machine.getPos())) {
			return;
		}
		
		machineGrid.remove(machine.getPos());
		
		removeFromGrid(machine.getPos());
	}
	
	public void registerWire(BlockPos wirePos) {	
		if (powerGrid.containsKey(wirePos)) {
			return;
		}
		
		addToGrid(wirePos, 0, true);
	}
	
	public void unregisterWire(BlockPos wirePos) {
		if (!powerGrid.containsKey(wirePos)) {
			return;
		}
		
		removeFromGrid(wirePos);
	}
	
	public int getPower(BlockPos pos) {
		Integer power = powerGrid.get(pos);
		
		if (power == null) {
			return 0;
		}
		
		return power;
	}
	
	private boolean setPower(BlockPos pos, int power) {
		if (powerGrid.containsKey(pos)) {
			powerGrid.put(pos, power);
			return true;
		}
		
		return false;
	}
	
	private void addToGrid(BlockPos pos, int power, boolean firstTime) {
		if (firstTime) {
			if (powerGrid.containsKey(pos)) {
				return;
			}
			
			ArrayList<AwesomeTileEntityMachine> machines = 
					new ArrayList<AwesomeTileEntityMachine>();
			
			nullifyNeighborGrids(pos, machines);
			
			powerGrid.put(pos, 
			  	  	  0);
			
			AwesomeTileEntityMachine placedMachine = machineGrid.get(pos);
			if (placedMachine != null && !machines.contains(placedMachine)) {
				machines.add(placedMachine);
			}
			
			for (AwesomeTileEntityMachine machine : machines) {
				addToGrid(machine.getPos(), 
						  machine.powerGenerated() - machine.powerRequired(),
						  false);
			}
		} else {
			propagatePower(new ArrayList<BlockPos>(), 
					   	   null, 
					   	   pos, 
					   	   power);
		}
	}
	
	private void nullifyNeighborGrids(BlockPos pos, ArrayList<AwesomeTileEntityMachine> machines) {
		propagatePower(new ArrayList<BlockPos>(), 
				   machines, 
			       pos.up(), 
			       -getPower(pos.up()));
		
		propagatePower(new ArrayList<BlockPos>(), 
				   machines, 
			       pos.down(), 
			       -getPower(pos.down()));
		
		propagatePower(new ArrayList<BlockPos>(), 
				   machines, 
			       pos.north(), 
			       -getPower(pos.north()));
		
		propagatePower(new ArrayList<BlockPos>(), 
				   machines, 
			       pos.south(), 
			       -getPower(pos.south()));
		
		propagatePower(new ArrayList<BlockPos>(), 
				   machines, 
			       pos.east(), 
			       -getPower(pos.east()));
		
		propagatePower(new ArrayList<BlockPos>(), 
				   machines, 
			       pos.west(), 
			       -getPower(pos.west()));
	}
	
	private void removeFromGrid(BlockPos pos) {
		if (!powerGrid.containsKey(pos)) {
			return;
		}
		
		ArrayList<AwesomeTileEntityMachine> machines = 
					new ArrayList<AwesomeTileEntityMachine>();
		
		propagatePower(new ArrayList<BlockPos>(), 
					   machines, 
				       pos, 
				       -getPower(pos));
		
		powerGrid.remove(pos);
		
		for (AwesomeTileEntityMachine machine : machines) {
			addToGrid(machine.getPos(), 
					  machine.powerGenerated() - machine.powerRequired(),
					  false);
		}
	}
	
	private void propagatePower(ArrayList<BlockPos> visited, 
								ArrayList<AwesomeTileEntityMachine> machines,
								BlockPos pos, 
								int powerChange) {
		if (visited.contains(pos)) {
			return;
		}

		visited.add(pos);
		
		int newPower = getPower(pos) + powerChange;
		
		AwesomeTileEntityMachine machine = machineGrid.get(pos);
		if (machine != null) {
			if (machines != null && !machines.contains(machine)) {
				machines.add(machine);
			}
		}
		
		if (setPower(pos, newPower)) {
			propagatePower(visited, machines, pos.up(), powerChange);
			propagatePower(visited, machines, pos.down(), powerChange);
			propagatePower(visited, machines, pos.north(), powerChange);
			propagatePower(visited, machines, pos.south(), powerChange);
			propagatePower(visited, machines, pos.east(), powerChange);
			propagatePower(visited, machines, pos.west(), powerChange);
		}
	}
}
