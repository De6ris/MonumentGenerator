package Debris.MonumentGenerator.piece;

import Debris.MonumentGenerator.reecriture.Direction;
import com.seedfinding.mccore.rand.ChunkRand;
import com.seedfinding.mccore.util.block.BlockBox;
import com.seedfinding.mccore.util.pos.BPos;

import java.util.ArrayList;
import java.util.List;

public abstract class Piece {
    public Direction direction;
    public BlockBox boundingBox;
    public List<BPos> sponge = new ArrayList<>();
    protected RoomDefinition roomDefinition;

    public Piece(Direction p_i50648_2_, BlockBox p_i50648_3_) {
        this.direction = p_i50648_2_;
        this.boundingBox = p_i50648_3_;
    }


    protected Piece(Direction direction, RoomDefinition roomDefinition, int p_i50649_5_, int p_i50649_6_, int p_i50649_7_) {
        this.direction = direction;
        this.roomDefinition = roomDefinition;
        int i = roomDefinition.index;
        int j = i % 5;
        int k = i / 5 % 5;
        int l = i / 25;
        if (direction != Direction.NORTH && direction != Direction.SOUTH) {
            this.boundingBox = new BlockBox(0, 0, 0, p_i50649_7_ * 8 - 1, p_i50649_6_ * 4 - 1, p_i50649_5_ * 8 - 1);
        } else {
            this.boundingBox = new BlockBox(0, 0, 0, p_i50649_5_ * 8 - 1, p_i50649_6_ * 4 - 1, p_i50649_7_ * 8 - 1);
        }

        switch (direction) {
            case NORTH:
                this.boundingBox.move(j * 8, l * 4, -(k + p_i50649_7_) * 8 + 1);
                break;
            case SOUTH:
                this.boundingBox.move(j * 8, l * 4, k * 8);
                break;
            case WEST:
                this.boundingBox.move(-(k + p_i50649_7_) * 8 + 1, l * 4, j * 8);
                break;
            default:
                this.boundingBox.move(k * 8, l * 4, j * 8);
        }

    }

    public void decorate(ChunkRand rand, BlockBox chunkBox) {
    }


    public BPos getPosWithOffset(int x, int y, int z) {
        return new BPos(getXWithOffset(x, z), getYWithOffset(y), getZWithOffset(x, z));
    }

    public int getXWithOffset(int x, int z) {
        Direction direction = this.direction;
        if (direction == null) {
            return x;
        } else {
            switch (direction) {
                case NORTH:
                case SOUTH:
                    return this.boundingBox.minX + x;
                case WEST:
                    return this.boundingBox.maxX - z;
                case EAST:
                    return this.boundingBox.minX + z;
                default:
                    return x;
            }
        }
    }

    public int getYWithOffset(int y) {
        return this.direction == null ? y : y + this.boundingBox.minY;
    }

    public int getZWithOffset(int x, int z) {
        Direction direction = this.direction;
        if (direction == null) {
            return z;
        } else {
            switch (direction) {
                case NORTH:
                    return this.boundingBox.maxZ - z;
                case SOUTH:
                    return this.boundingBox.minZ + z;
                case WEST:
                case EAST:
                    return this.boundingBox.minZ + x;
                default:
                    return z;
            }
        }
    }

}
