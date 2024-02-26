package Debris.MonumentGenerator.piece;

import Debris.MonumentGenerator.reecriture.Direction;

public class RoomDefinition {
    public final int index;
    public final RoomDefinition[] connections = new RoomDefinition[6];
    public final boolean[] hasOpening = new boolean[6];
    public boolean claimed;
    public boolean isSource;
    public int scanIndex;

    public RoomDefinition(int p_i45584_1_) {
        this.index = p_i45584_1_;
    }

    public void setConnection(Direction p_175957_1_, RoomDefinition p_175957_2_) {
        this.connections[p_175957_1_.getIndex()] = p_175957_2_;
        p_175957_2_.connections[p_175957_1_.getOpposite().getIndex()] = this;
    }

    public void updateOpenings() {
        for(int i = 0; i < 6; ++i) {
            this.hasOpening[i] = this.connections[i] != null;
        }

    }

    public boolean findSource(int p_175959_1_) {
        if (this.isSource) {
            return true;
        } else {
            this.scanIndex = p_175959_1_;

            for(int i = 0; i < 6; ++i) {
                if (this.connections[i] != null && this.hasOpening[i] && this.connections[i].scanIndex != p_175959_1_ && this.connections[i].findSource(p_175959_1_)) {
                    return true;
                }
            }

            return false;
        }
    }

    public boolean isSpecial() {
        return this.index >= 75;
    }
}
