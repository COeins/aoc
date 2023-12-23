package de.coeins.aoc2023;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

class Day22 implements Day<Integer> {
	@Override
	public Integer task1(String[] in) {
		List<Brick> bricks = parse(in);
		log("Waiting for stabilization...");
		stabilize(bricks);

		log("Testing bricks...");
		int freeBricks = 0;
		for (Brick b : bricks) {
			if (b.supports.isEmpty()) {
				log(b.name, "doesnt support anything");
				freeBricks++;
			} else {
				boolean supporting = false;
				nextUpper:
				for (Brick upper : b.supports) {
					if (upper.supportedBy.contains(b) && upper.supportedBy.size() == 1) {
						log(b.name, "supports", upper.name, "and is the only support");
						supporting = true;
					} else {
						log(b.name, "supports", upper.name, "but is also supported by",
								upper.supportedBy.stream().filter(s -> s != b).toArray());
					}
				}
				if (!supporting)
					freeBricks++;
			}
		}

		return freeBricks;
	}

	@Override
	public Integer task2(String[] in) {
		List<Brick> bricks = parse(in);
		stabilize(bricks);
		int count = 0;
		for (Brick b : bricks) {
			List<Brick> bricks2 = new ArrayList<>(bricks.size());
			for (Brick copy : bricks)
				if (copy != b)
					bricks2.add(copy.clone());
			int m = stabilize(bricks2);
			log("removing", b, "moved", m, "bricks");
			count += m;
		}
		return count;
	}

	private List<Brick> parse(String[] in) {
		List<Brick> bricks = new ArrayList<>(in.length);
		int n = 'A';
		for (String l : in) {
			String[] split = l.split("[,~]");
			bricks.add(new Brick(n > 'Z' ? Integer.toString(n++ - 'Z') : Character.toString(n++),
					new Point3D(Integer.parseInt(split[0]), Integer.parseInt(split[1]), Integer.parseInt(split[2])),
					new Point3D(Integer.parseInt(split[3]), Integer.parseInt(split[4]), Integer.parseInt(split[5]))));
		}
		return bricks;
	}

	private Brick[][][] generateMap(List<Brick> bricks) {
		int x = 0, z = 0, y = 0;
		for (Brick b : bricks) {
			x = Math.max(b.p2.x, x);
			y = Math.max(b.p2.y, y);
			z = Math.max(b.p2.z, z);
		}
		Brick[][][] map = new Brick[x + 1][y + 1][z + 1];
		for (Brick b : bricks)
			b.addToMap(map);
		return map;
	}

	private int stabilize(List<Brick> bricks) {
		Set<Brick> moved = new HashSet<>();
		Brick[][][] map = generateMap(bricks);
		boolean stable;
		do {
			stable = true;
			for (Brick b : bricks) {
				stable &= !b.sink(bricks, map, moved);
			}
		} while (!stable);
		return moved.size();
	}

	record Point3D(int x, int y, int z) {
		public Point3D below() {
			return new Point3D(x, y, z - 1);
		}
	}

	class Brick {
		private String name;
		private Point3D p1;
		private Point3D p2;
		private List<Brick> supports = new ArrayList<>();
		private List<Brick> supportedBy = new ArrayList<>();

		Brick(String name, Point3D p1, Point3D p2) {
			this.name = name;
			if (p1.x > p2.x || p1.y > p2.y || p1.z > p2.z) {
				this.p1 = p2;
				this.p2 = p1;
			} else {
				this.p1 = p1;
				this.p2 = p2;
			}
			if (this.p1.x > this.p2.x || this.p1.y > this.p2.y || this.p1.z > this.p2.z)
				throw new RuntimeException("Invalid Brick configuration " + p1 + ", " + p2);
		}

		public boolean containsBrick(Point3D compare) {
			return compare.x >= p1.x && compare.x <= p2.x
					&& compare.y >= p1.y && compare.y <= p2.y
					&& compare.z >= p1.z && compare.z <= p2.z;
		}

		public boolean canSink(Iterable<Brick> bricks, Brick[][][] map) {
			if (p1.z == 1) {
				//log(name, " sits on the ground");
				return false;
			}

			boolean freeflowing = true;
			for (int x = p1.x; x <= p2.x; x++)
				for (int y = p1.y; y <= p2.y; y++)
					for (int z = p1.z; z <= p2.z; z++)
						if (map[x][y][z - 1] != null && map[x][y][z - 1] != this) {
							if (!map[x][y][z - 1].supports.contains(this))
								map[x][y][z - 1].supports.add(this);
							if (!supportedBy.contains(map[x][y][z - 1]))
								supportedBy.add(map[x][y][z - 1]);
							freeflowing = false;
						}
			return freeflowing;
		}

		public boolean sink(Iterable<Brick> bricks, Brick[][][] map, Set<Brick> moved) {
			boolean thisMoved = false;
			while (canSink(bricks, map)) {
				removeFromMap(map);
				p1 = p1.below();
				p2 = p2.below();
				thisMoved = true;
				if (moved != null)
					moved.add(this);
			}
			if (thisMoved) {
				addToMap(map);
				List<Brick> tmp = new ArrayList<>(supports);
				supports.removeAll(supports);

				for (Brick s : tmp) {
					s.supportedBy.remove(this);
					if (s.supportedBy.isEmpty())
						s.sink(bricks, map, moved);
				}
			}
			return thisMoved;
		}

		public Brick clone() {
			return new Brick(name, p1, p2);
		}

		public String toString() {
			return name;
		}

		public void addToMap(Brick[][][] map) {
			for (int x = p1.x; x <= p2.x; x++)
				for (int y = p1.y; y <= p2.y; y++)
					for (int z = p1.z; z <= p2.z; z++) {
						if (map[x][y][z] != null && map[x][y][z] != this)
							throw new RuntimeException("Overlap");
						map[x][y][z] = this;
					}
		}

		public void removeFromMap(Brick[][][] map) {
			for (int x = p1.x; x <= p2.x; x++)
				for (int y = p1.y; y <= p2.y; y++)
					for (int z = p1.z; z <= p2.z; z++) {
						map[x][y][z] = null;
					}
		}
	}
}
