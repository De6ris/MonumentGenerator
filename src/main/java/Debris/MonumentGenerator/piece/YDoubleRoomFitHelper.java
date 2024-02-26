package Debris.MonumentGenerator.piece;

import Debris.MonumentGenerator.reecriture.Direction;
import com.seedfinding.mccore.rand.ChunkRand;

public class YDoubleRoomFitHelper implements IMonumentRoomFitHelper{
    public YDoubleRoomFitHelper() {
    }

    public boolean fits(RoomDefinition definition) {
        return definition.hasOpening[Direction.UP.getIndex()] && !definition.connections[Direction.UP.getIndex()].claimed;
    }

    public Piece create(Direction p_175968_1_, RoomDefinition p_175968_2_, ChunkRand p_175968_3_) {
        p_175968_2_.claimed = true;
        p_175968_2_.connections[Direction.UP.getIndex()].claimed = true;
        return new DoubleYRoom(p_175968_1_, p_175968_2_);
    }
}
