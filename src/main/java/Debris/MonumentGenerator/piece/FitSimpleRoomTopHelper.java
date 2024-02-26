package Debris.MonumentGenerator.piece;

import Debris.MonumentGenerator.reecriture.Direction;
import com.seedfinding.mccore.rand.ChunkRand;

public class FitSimpleRoomTopHelper implements IMonumentRoomFitHelper {
    public FitSimpleRoomTopHelper() {
    }

    public boolean fits(RoomDefinition definition) {
        return !definition.hasOpening[Direction.WEST.getIndex()] && !definition.hasOpening[Direction.EAST.getIndex()] && !definition.hasOpening[Direction.NORTH.getIndex()] && !definition.hasOpening[Direction.SOUTH.getIndex()] && !definition.hasOpening[Direction.UP.getIndex()];
    }

    public Piece create(Direction p_175968_1_, RoomDefinition p_175968_2_, ChunkRand p_175968_3_) {
        p_175968_2_.claimed = true;
        return new SimpleTopRoom(p_175968_1_, p_175968_2_);
    }
}
