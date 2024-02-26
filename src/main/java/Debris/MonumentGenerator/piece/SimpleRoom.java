package Debris.MonumentGenerator.piece;

import Debris.MonumentGenerator.reecriture.Direction;
import com.seedfinding.mccore.rand.ChunkRand;
import com.seedfinding.mccore.util.block.BlockBox;

public class SimpleRoom extends Piece {
    private int mainDesign;

    public SimpleRoom(Direction p_i45587_1_, RoomDefinition p_i45587_2_, ChunkRand p_i45587_3_) {
        super(p_i45587_1_, p_i45587_2_, 1, 1, 1);
        this.mainDesign = p_i45587_3_.nextInt(3);
    }

    @Override
    public void decorate(ChunkRand rand, BlockBox chunkBox) {
        if (mainDesign != 0) rand.nextLong();
    }
}
