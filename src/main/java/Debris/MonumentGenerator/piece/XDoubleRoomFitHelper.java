package Debris.MonumentGenerator.piece;

import Debris.MonumentGenerator.reecriture.Direction;
import com.seedfinding.mccore.rand.ChunkRand;

public class XDoubleRoomFitHelper implements IMonumentRoomFitHelper {
    public XDoubleRoomFitHelper() {
    }

    public boolean fits(RoomDefinition definition) {
        return definition.hasOpening[Direction.EAST.getIndex()] && !definition.connections[Direction.EAST.getIndex()].claimed;
    }

    public Piece create(Direction p_175968_1_, RoomDefinition p_175968_2_, ChunkRand p_175968_3_) {
        p_175968_2_.claimed = true;
        p_175968_2_.connections[Direction.EAST.getIndex()].claimed = true;
        return new DoubleXRoom(p_175968_1_, p_175968_2_);
    }
}
