package Debris.MonumentGenerator.piece;

import Debris.MonumentGenerator.reecriture.Direction;
import com.seedfinding.mccore.rand.ChunkRand;
import com.seedfinding.mccore.util.block.BlockBox;
import com.seedfinding.mccore.util.pos.BPos;

public class SimpleTopRoom extends Piece {
    public SimpleTopRoom(Direction p_i50644_1_, RoomDefinition p_i50644_2_) {
        super(p_i50644_1_, p_i50644_2_, 1, 1, 1);
    }

    @Override
    public void decorate(ChunkRand rand, BlockBox chunkBox) {
        for (int i = 1; i <= 6; ++i) {
            for (int j = 1; j <= 6; ++j) {
                if (rand.nextInt(3) != 0) {
                    int k = 2 + (rand.nextInt(4) == 0 ? 0 : 1);
                    for (int y = k; y <= 3; y++) {
                        BPos pos = this.getPosWithOffset(i, y, j);
                        if (chunkBox.contains(pos)) {
                            this.sponge.add(pos);
                        }
                    }
                }
            }
        }
    }
}
