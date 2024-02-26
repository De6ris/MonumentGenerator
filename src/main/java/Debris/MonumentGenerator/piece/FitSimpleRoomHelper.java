package Debris.MonumentGenerator.piece;

import Debris.MonumentGenerator.reecriture.Direction;
import com.seedfinding.mccore.rand.ChunkRand;

public class FitSimpleRoomHelper implements IMonumentRoomFitHelper {
    public FitSimpleRoomHelper() {
    }

    public boolean fits(RoomDefinition definition) {
        return true;
    }

    public Piece create(Direction p_175968_1_, RoomDefinition p_175968_2_, ChunkRand p_175968_3_) {
        p_175968_2_.claimed = true;
        return new SimpleRoom(p_175968_1_, p_175968_2_, p_175968_3_);
    }
}
