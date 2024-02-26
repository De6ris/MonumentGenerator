package Debris.MonumentGenerator.piece;

import Debris.MonumentGenerator.reecriture.Direction;
import com.seedfinding.mccore.rand.ChunkRand;

public class XYDoubleRoomFitHelper implements IMonumentRoomFitHelper {
    public XYDoubleRoomFitHelper() {
    }

    public boolean fits(RoomDefinition definition) {
        if (definition.hasOpening[Direction.EAST.getIndex()] && !definition.connections[Direction.EAST.getIndex()].claimed && definition.hasOpening[Direction.UP.getIndex()] && !definition.connections[Direction.UP.getIndex()].claimed) {
            RoomDefinition oceanmonumentpieces$roomdefinition = definition.connections[Direction.EAST.getIndex()];
            return oceanmonumentpieces$roomdefinition.hasOpening[Direction.UP.getIndex()] && !oceanmonumentpieces$roomdefinition.connections[Direction.UP.getIndex()].claimed;
        } else {
            return false;
        }
    }

    public Piece create(Direction p_175968_1_, RoomDefinition p_175968_2_, ChunkRand p_175968_3_) {
        p_175968_2_.claimed = true;
        p_175968_2_.connections[Direction.EAST.getIndex()].claimed = true;
        p_175968_2_.connections[Direction.UP.getIndex()].claimed = true;
        p_175968_2_.connections[Direction.EAST.getIndex()].connections[Direction.UP.getIndex()].claimed = true;
        return new DoubleXYRoom(p_175968_1_, p_175968_2_);
    }
}
