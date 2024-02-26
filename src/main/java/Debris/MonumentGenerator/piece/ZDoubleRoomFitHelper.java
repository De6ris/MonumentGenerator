package Debris.MonumentGenerator.piece;


import Debris.MonumentGenerator.reecriture.Direction;
import com.seedfinding.mccore.rand.ChunkRand;

public class ZDoubleRoomFitHelper implements IMonumentRoomFitHelper {
    public ZDoubleRoomFitHelper() {
    }

    public boolean fits(RoomDefinition definition) {
        return definition.hasOpening[Direction.NORTH.getIndex()] && !definition.connections[Direction.NORTH.getIndex()].claimed;
    }

    public Piece create(Direction p_175968_1_, RoomDefinition p_175968_2_, ChunkRand p_175968_3_) {
        RoomDefinition oceanmonumentpieces$roomdefinition = p_175968_2_;
        if (!p_175968_2_.hasOpening[Direction.NORTH.getIndex()] || p_175968_2_.connections[Direction.NORTH.getIndex()].claimed) {
            oceanmonumentpieces$roomdefinition = p_175968_2_.connections[Direction.SOUTH.getIndex()];
        }

        oceanmonumentpieces$roomdefinition.claimed = true;
        oceanmonumentpieces$roomdefinition.connections[Direction.NORTH.getIndex()].claimed = true;
        return new DoubleZRoom(p_175968_1_, oceanmonumentpieces$roomdefinition);
    }

}
