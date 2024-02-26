package Debris.MonumentGenerator.reecriture;

import com.seedfinding.mccore.rand.ChunkRand;

public class XoroChunkRand extends ChunkRand {
    private long seedLo, seedHi;

    public void setSeed(long seed0, long seed1) {
        this.seedLo = seed0;
        this.seedHi = seed1;
        if ((this.seedLo | this.seedHi) == 0L) {
            this.seedLo = -7046029254386353131L;
            this.seedHi = 7640891576956012809L;
        }
    }

    public static long mixStafford13(long l) {
        l = (l ^ l >>> 30) * -4658895280553007687L;
        l = (l ^ l >>> 27) * -7723592293110705685L;
        return l ^ l >>> 31;
    }

    @Override
    public void setSeed(long l) {
        long l2 = l ^ 0x6A09E667F3BCC909L;
        long l3 = l2 - 7046029254386353131L;
        this.setSeed(mixStafford13(l2), mixStafford13(l3));
    }

    @Override
    public long nextLong() {
        long l = this.seedLo;
        long l2 = this.seedHi;
        long l3 = Long.rotateLeft(l + l2, 17) + l;
        this.seedLo = Long.rotateLeft(l, 49) ^ (l2 ^= l) ^ l2 << 21;
        this.seedHi = Long.rotateLeft(l2, 28);
        return l3;
    }

    public long popNextLong() {
        int a_ = (int) (this.nextLong() >> 32);
        int b = (int) (this.nextLong() >> 32);
        long a = (long) a_ << 32;
        return a + (long) b;
    }

    @Override
    public int nextInt() {
        return (int) this.nextLong();
    }

    @Override
    public int nextInt(int bound) {
        if (bound <= 0) {
            throw new IllegalArgumentException("Bound must be positive");
        } else {
            long i = Integer.toUnsignedLong(this.nextInt());
            long j = i * (long) bound;
            long k = j & 4294967295L;
            if (k < (long) bound) {
                for (int l = Integer.remainderUnsigned(~bound + 1, bound); k < (long) l; k = j & 4294967295L) {
                    i = Integer.toUnsignedLong(this.nextInt());
                    j = i * (long) bound;
                }
            }
            long i1 = j >> 32;
            return (int) i1;
        }
    }

    public long setPopulationSeed(long worldseed, int chunkOriginX, int chunkOriginZ) {
        this.setSeed(worldseed);
        long a = this.popNextLong() | 1L;
        long b = this.popNextLong() | 1L;
        long populationSeed = (long) chunkOriginX * a + (long) chunkOriginZ * b ^ worldseed;
        this.setSeed(populationSeed);
        return populationSeed;
    }

    public long setDecoratorSeed(long worldSeed, int chunkX, int chunkZ, int salt) {
        long decoratorSeed = setPopulationSeed
                (worldSeed, chunkX << 4, chunkZ << 4) + (long) salt;
        this.setSeed(decoratorSeed);
        return decoratorSeed;
    }
}
