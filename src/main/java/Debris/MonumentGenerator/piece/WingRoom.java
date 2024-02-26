package Debris.MonumentGenerator.piece;

import Debris.MonumentGenerator.reecriture.Direction;
import com.seedfinding.mccore.util.block.BlockBox;

public class WingRoom extends Piece {
    private int mainDesign;

    public WingRoom(Direction p_i45585_1_, BlockBox p_i45585_2_, int p_i45585_3_) {
        super(p_i45585_1_, p_i45585_2_);
        this.mainDesign = p_i45585_3_ & 1;
    }
}
