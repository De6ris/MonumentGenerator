package Debris.MonumentGenerator.properties;

import com.seedfinding.mccore.rand.ChunkRand;
import com.seedfinding.mccore.util.data.Pair;
import com.seedfinding.mccore.util.pos.BPos;
import com.seedfinding.mccore.version.MCVersion;

public class Main {

    public static MCVersion version = MCVersion.v1_20;

    public static long worldSeed = -1432629000L;

    public static void main(String[] args) {
        run();
    }

    public static void run() {
        ChunkRand rand = new ChunkRand();

        MonumentGenerator generator = new MonumentGenerator(version);
        generator.generate(worldSeed, -5360 >> 4, 4416 >> 4, rand);
        generator.decorate();
        for (Pair<BPos, Integer> pair : generator.getSpongeDistribution()) {
            System.out.println(pair.getFirst());
            System.out.println(pair.getSecond());
        }
    }

}
