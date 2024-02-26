package Debris.MonumentGenerator.piece;

import Debris.MonumentGenerator.reecriture.Direction;
import com.seedfinding.mccore.rand.ChunkRand;

public interface IMonumentRoomFitHelper {
    boolean fits(RoomDefinition definition);

    Piece create(Direction p_175968_1_, RoomDefinition p_175968_2_, ChunkRand p_175968_3_);
}
