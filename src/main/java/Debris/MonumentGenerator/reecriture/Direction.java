package Debris.MonumentGenerator.reecriture;

import com.seedfinding.mccore.util.math.Vec3i;

import java.util.Arrays;
import java.util.Comparator;
import java.util.function.Predicate;

public enum Direction {
    DOWN(0, 1, -1, "down", AxisDirection.NEGATIVE, Axis.Y, new Vec3i(0, -1, 0)),
    UP(1, 0, -1, "up", AxisDirection.POSITIVE, Axis.Y, new Vec3i(0, 1, 0)),
    NORTH(2, 3, 2, "north", AxisDirection.NEGATIVE, Axis.Z, new Vec3i(0, 0, -1)),
    SOUTH(3, 2, 0, "south", AxisDirection.POSITIVE, Axis.Z, new Vec3i(0, 0, 1)),
    WEST(4, 5, 1, "west", AxisDirection.NEGATIVE, Axis.X, new Vec3i(-1, 0, 0)),
    EAST(5, 4, 3, "east", AxisDirection.POSITIVE, Axis.X, new Vec3i(1, 0, 0));

    private final int index;
    private final int opposite;
    private final int horizontalIndex;
    private final Axis axis;
    private final Vec3i directionVec;
    private static final Direction[] VALUES = values();
    private static final Direction[] BY_INDEX = Arrays.stream(VALUES).sorted(Comparator.comparingInt((a) -> a.index)).toArray((b) -> new Direction[b]);
    private static final Direction[] BY_HORIZONTAL_INDEX = Arrays.stream(VALUES).filter((a) -> a.getAxis().isHorizontal()).sorted(Comparator.comparingInt((b) -> b.horizontalIndex)).toArray((c) -> new Direction[c]);

    Direction(int indexIn, int oppositeIn, int horizontalIndexIn, String nameIn, AxisDirection axisDirectionIn, Axis axisIn, Vec3i directionVecIn) {
        this.index = indexIn;
        this.horizontalIndex = horizontalIndexIn;
        this.opposite = oppositeIn;
        this.axis = axisIn;
        this.directionVec = directionVecIn;
    }


    public int getIndex() {
        return this.index;
    }

    public Direction getOpposite() {
        return byIndex(this.opposite);
    }

    public static Direction byIndex(int index) {
        return BY_INDEX[Math.abs(index % BY_INDEX.length)];
    }

    public int getXOffset() {
        return this.directionVec.getX();
    }

    public int getYOffset() {
        return this.directionVec.getY();
    }

    public int getZOffset() {
        return this.directionVec.getZ();
    }

    public Axis getAxis() {
        return this.axis;
    }

    public static Direction byHorizontalIndex(int horizontalIndexIn) {
        return BY_HORIZONTAL_INDEX[Math.abs(horizontalIndexIn % BY_HORIZONTAL_INDEX.length)];
    }

    public enum Axis implements Predicate<Direction> {
        X("x") {
            public boolean test(Direction direction) {
                return false;
            }
        },
        Y("y") {
            public boolean test(Direction direction) {
                return false;
            }
        },
        Z("z") {
            public boolean test(Direction direction) {
                return false;
            }
        };
        private final String name;

        Axis(String nameIn) {
            this.name = nameIn;
        }

        public boolean isHorizontal() {
            return this == X || this == Z;
        }

        public String toString() {
            return this.name;
        }
    }

    public enum AxisDirection {
        POSITIVE(1, "Towards positive"),
        NEGATIVE(-1, "Towards negative");

        AxisDirection(int offset, String description) {
        }
    }

}
