package com.jdolphin.dmadditions.util;

import net.minecraft.util.math.BlockPos;
import org.apache.logging.log4j.LogManager;

public class DMALocation {
	public static final DMALocation DEFAULT = new DMALocation(BlockPos.ZERO, "minecraft:overworld");
	private int x;
	private int y;
	private int z;
	private String dim;
	private final String allDatMixed;

	public DMALocation(int X, int Y, int Z, String dimension) {
		this.x = X;
		this.y = Y;
		this.z = Z;
		this.dim = dimension;
		this.allDatMixed = this.x + "|"  + this.y + "|" + this.z + "|" + this.dim;
	}

	public DMALocation(String waypoint) {
		this.allDatMixed = waypoint;
	}

	public DMALocation(BlockPos pos, String dimension) {
		this.x = pos.getX();
		this.y = pos.getY();
		this.z = pos.getZ();
		this.dim = dimension;
		this.allDatMixed = this.x + "|"  + this.y + "|" + this.z + "|" + this.dim;
	}
	public BlockPos getBlockPos() {
		return new BlockPos(this.x, this.y, this.z);
	}

	public int getX() {
		return this.x;
	}

	public int getY() {
		return this.y;
	}

	public int getZ() {
		return this.z;
	}

	public String getLocationString() {
		return allDatMixed;
	}

	private DMALocation getLocation() {
		String[] dataParts = allDatMixed.split("\\|");
		if (dataParts.length == 4) {
			int X = Integer.parseInt(dataParts[0]);
			int Y = Integer.parseInt(dataParts[1]);
			int Z = Integer.parseInt(dataParts[2]);
			String dimension = dataParts[3];


			return new DMALocation(X, Y, Z, dimension);
		} else {
			System.out.println("Invalid data format");
		}
		return null;
	}

	public static DMALocation getLocation(String locationString) {
		String[] dataParts = locationString.split("\\|");
		if (dataParts.length == 4) {
			try {
				int x = Integer.parseInt(dataParts[0]);
				int y = Integer.parseInt(dataParts[1]);
				int z = Integer.parseInt(dataParts[2]);
				String dimension = dataParts[3];

				return new DMALocation(x, y, z, dimension);
			} catch (NumberFormatException e){
				e.printStackTrace();
			}
		} else {
			LogManager.getLogger().warn("Invalid data format");
		}
		return null;
	}

	public String getDimension() {
		return this.dim;
	}

	public void setX(int X) {
		this.x = X;
	}

	public void setY(int Y) {
		this.y = Y;
	}
	public void setZ(int Z) {
		this.z = Z;
	}
	public void setX(BlockPos pos) {
		this.z = pos.getX();
	}

	public void setY(BlockPos pos) {
		this.z = pos.getZ();
	}

	public void setZ(BlockPos pos) {
		this.z = pos.getZ();
	}

	public void setDimension(String dimension) {
		this.dim = dimension;
	}
}