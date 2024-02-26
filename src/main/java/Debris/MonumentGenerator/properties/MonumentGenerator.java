package Debris.MonumentGenerator.properties;

import Debris.MonumentGenerator.piece.*;
import Debris.MonumentGenerator.reecriture.Direction;
import Debris.MonumentGenerator.reecriture.XoroChunkRand;
import com.seedfinding.mccore.rand.ChunkRand;
import com.seedfinding.mccore.util.block.BlockBox;
import com.seedfinding.mccore.util.data.Pair;
import com.seedfinding.mccore.util.pos.BPos;
import com.seedfinding.mccore.version.MCVersion;
import com.seedfinding.mccore.version.UnsupportedVersion;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MonumentGenerator {
    private final MCVersion version;
    private Direction direction;
    private long worldSeed;
    private BlockBox boundingBox;

    private RoomDefinition sourceRoom;
    private RoomDefinition coreRoom;
    private final List<Piece> pieces = new ArrayList<>();


    protected static final int GRIDROOM_SOURCE_INDEX = getRoomIndex(2, 0, 0);
    protected static final int GRIDROOM_TOP_CONNECT_INDEX = getRoomIndex(2, 2, 0);
    protected static final int GRIDROOM_LEFTWING_CONNECT_INDEX = getRoomIndex(0, 1, 0);
    protected static final int GRIDROOM_RIGHTWING_CONNECT_INDEX = getRoomIndex(4, 1, 0);


    public MonumentGenerator(MCVersion version) {
        this.version = version;
    }

    public void generate(long worldSeed, int chunkX, int chunkZ, ChunkRand rand) {
        if (version.isOlderThan(MCVersion.v1_8)) {
            throw new UnsupportedVersion(version, " Monument");
        }
        this.pieces.clear();

        int startX = chunkX * 16 - 29;
        int startZ = chunkZ * 16 - 29;

        this.worldSeed = worldSeed;
        rand.setCarverSeed(worldSeed, chunkX, chunkZ, version);
        this.direction = switch (rand.nextInt(4)) {
            case 0 -> Direction.NORTH;
            case 1 -> Direction.EAST;
            case 2 -> Direction.SOUTH;
            default -> Direction.WEST;
        };

        this.boundingBox = new BlockBox(startX, 39, startZ, startX + 58 - 1, 61, startZ + 58 - 1);

        List<RoomDefinition> list = this.generateRoomGraph(rand);

        this.sourceRoom.claimed = true;
        this.pieces.add(new EntryRoom(direction, this.sourceRoom));
        this.pieces.add(new MonumentCoreRoom(direction, this.coreRoom));
        List<IMonumentRoomFitHelper> list1 = new ArrayList<>();
        list1.add(new XYDoubleRoomFitHelper());
        list1.add(new YZDoubleRoomFitHelper());
        list1.add(new ZDoubleRoomFitHelper());
        list1.add(new XDoubleRoomFitHelper());
        list1.add(new YDoubleRoomFitHelper());
        list1.add(new FitSimpleRoomTopHelper());
        list1.add(new FitSimpleRoomHelper());

        loop:
        for (RoomDefinition roomDefinition : list) {
            if (!roomDefinition.claimed && !roomDefinition.isSpecial()) {
                Iterator<IMonumentRoomFitHelper> iterator = list1.iterator();

                IMonumentRoomFitHelper roomFitHelper;
                while (true) {
                    if (!iterator.hasNext()) {
                        continue loop;
                    }
                    roomFitHelper = iterator.next();
                    if (roomFitHelper.fits(roomDefinition)) {
                        break;
                    }
                }
                this.pieces.add(roomFitHelper.create(direction, roomDefinition, rand));
            }
        }

        int j = this.boundingBox.minY;
        int k = this.getXWithOffset(9, 22);
        int l = this.getZWithOffset(9, 22);

        for (Piece piece : this.pieces) {
            piece.boundingBox.move(k, j, l);
        }

        BlockBox box1 = createProper(this.getXWithOffset(1, 1), this.getYWithOffset(1), this.getZWithOffset(1, 1), this.getXWithOffset(23, 21), this.getYWithOffset(8), this.getZWithOffset(23, 21));
        BlockBox box2 = createProper(this.getXWithOffset(34, 1), this.getYWithOffset(1), this.getZWithOffset(34, 1), this.getXWithOffset(56, 21), this.getYWithOffset(8), this.getZWithOffset(56, 21));
        BlockBox box = createProper(this.getXWithOffset(22, 22), this.getYWithOffset(13), this.getZWithOffset(22, 22), this.getXWithOffset(35, 35), this.getYWithOffset(17), this.getZWithOffset(35, 35));
        int i = rand.nextInt();
        this.pieces.add(new WingRoom(direction, box1, i++));
        this.pieces.add(new WingRoom(direction, box2, i++));
        this.pieces.add(new Penthouse(direction, box));
    }

    public List<Piece> getPieces() {
        return pieces;
    }

    public int getTotalSponge() {
        int count = 0;
        for (Piece piece : pieces) {
            count += piece.sponge.size();
        }
        return count;
    }

    public List<BPos> getSpongePos() {
        return pieces.stream().filter(x -> x instanceof SimpleTopRoom).flatMap(x -> x.sponge.stream()).toList();
    }

    public List<Pair<BPos, Integer>> getSpongeDistribution() {
        return pieces.stream().filter(x -> x instanceof SimpleTopRoom).map(x -> new Pair<>(new BPos(x.boundingBox.getCenter()), x.sponge.size())).toList();
    }

    public BPos getGold() {
        for (Piece piece : pieces) {
            if (piece instanceof MonumentCoreRoom) return new BPos(piece.boundingBox.getCenter());
        }
        return null;
    }

    //for sponge
    public void decorate() {
        List<Piece> generationPieces = new ArrayList<>(pieces);
        for (int x = boundingBox.minX >> 4; x <= boundingBox.maxX >> 4; x++) {
            for (int z = boundingBox.minZ >> 4; z <= boundingBox.maxZ >> 4; z++) {
                decorateChunk(generationPieces, x, z);
            }
        }
    }

    private void decorateChunk(List<Piece> pieces, int chunkX, int chunkZ) {
        BlockBox chunkBox = new BlockBox(chunkX * 16, chunkZ * 16, chunkX * 16 + 15, chunkZ * 16 + 15);
        if (version.isNewerOrEqualTo(MCVersion.v1_18)) {
            XoroChunkRand rand = new XoroChunkRand();
            if (version.isNewerOrEqualTo(MCVersion.v1_19_3)) {
                rand.setDecoratorSeed(worldSeed, chunkX, chunkZ, 40008);//TODO Who knows why 40000 to 40020 are all wrong
            } else {
                rand.setDecoratorSeed(worldSeed, chunkX, chunkZ, 40008);//TODO unsure
            }
            for (Piece piece : pieces) {
                if (piece.boundingBox.intersects(chunkBox)) {
                    piece.decorate(rand, chunkBox);
                }
            }
        } else {
            ChunkRand rand = new ChunkRand();
            rand.setDecoratorSeed(worldSeed, chunkX << 4, chunkZ << 4, 40008, version);//TODO only for 1.16?
            for (Piece piece : pieces) {
                if (piece.boundingBox.intersects(chunkBox)) {
                    piece.decorate(rand, chunkBox);
                }
            }
        }
    }


    private static int getRoomIndex(int p_175820_0_, int p_175820_1_, int p_175820_2_) {
        return p_175820_1_ * 25 + p_175820_2_ * 5 + p_175820_0_;
    }

    private List<RoomDefinition> generateRoomGraph(ChunkRand rand) {
        RoomDefinition[] roomDefinitions = new RoomDefinition[75];

        for (int i = 0; i < 5; ++i) {
            for (int j = 0; j < 4; ++j) {
                int k = 0;
                int l = getRoomIndex(i, 0, j);
                roomDefinitions[l] = new RoomDefinition(l);
            }
        }

        for (int i2 = 0; i2 < 5; ++i2) {
            for (int l2 = 0; l2 < 4; ++l2) {
                int k3 = 1;
                int j4 = getRoomIndex(i2, 1, l2);
                roomDefinitions[j4] = new RoomDefinition(j4);
            }
        }

        for (int j2 = 1; j2 < 4; ++j2) {
            for (int i3 = 0; i3 < 2; ++i3) {
                int l3 = 2;
                int k4 = getRoomIndex(j2, 2, i3);
                roomDefinitions[k4] = new RoomDefinition(k4);
            }
        }

        this.sourceRoom = roomDefinitions[GRIDROOM_SOURCE_INDEX];

        for (int k2 = 0; k2 < 5; ++k2) {
            for (int j3 = 0; j3 < 5; ++j3) {
                for (int i4 = 0; i4 < 3; ++i4) {
                    int l4 = getRoomIndex(k2, i4, j3);
                    if (roomDefinitions[l4] != null) {
                        for (Direction direction : Direction.values()) {
                            int i1 = k2 + direction.getXOffset();
                            int j1 = i4 + direction.getYOffset();
                            int k1 = j3 + direction.getZOffset();
                            if (i1 >= 0 && i1 < 5 && k1 >= 0 && k1 < 5 && j1 >= 0 && j1 < 3) {
                                int l1 = getRoomIndex(i1, j1, k1);
                                if (roomDefinitions[l1] != null) {
                                    if (k1 == j3) {
                                        roomDefinitions[l4].setConnection(direction, roomDefinitions[l1]);
                                    } else {
                                        roomDefinitions[l4].setConnection(direction.getOpposite(), roomDefinitions[l1]);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        RoomDefinition definition = new RoomDefinition(1003);
        RoomDefinition definition1 = new RoomDefinition(1001);
        RoomDefinition definition2 = new RoomDefinition(1002);
        roomDefinitions[GRIDROOM_TOP_CONNECT_INDEX].setConnection(Direction.UP, definition);
        roomDefinitions[GRIDROOM_LEFTWING_CONNECT_INDEX].setConnection(Direction.SOUTH, definition1);
        roomDefinitions[GRIDROOM_RIGHTWING_CONNECT_INDEX].setConnection(Direction.SOUTH, definition2);
        definition.claimed = true;
        definition1.claimed = true;
        definition2.claimed = true;
        this.sourceRoom.isSource = true;
        this.coreRoom = roomDefinitions[getRoomIndex(rand.nextInt(4), 0, 2)];
        this.coreRoom.claimed = true;
        this.coreRoom.connections[Direction.EAST.getIndex()].claimed = true;
        this.coreRoom.connections[Direction.NORTH.getIndex()].claimed = true;
        this.coreRoom.connections[Direction.EAST.getIndex()].connections[Direction.NORTH.getIndex()].claimed = true;
        this.coreRoom.connections[Direction.UP.getIndex()].claimed = true;
        this.coreRoom.connections[Direction.EAST.getIndex()].connections[Direction.UP.getIndex()].claimed = true;
        this.coreRoom.connections[Direction.NORTH.getIndex()].connections[Direction.UP.getIndex()].claimed = true;
        this.coreRoom.connections[Direction.EAST.getIndex()].connections[Direction.NORTH.getIndex()].connections[Direction.UP.getIndex()].claimed = true;
        List<RoomDefinition> list = new ArrayList<>();

        for (RoomDefinition definition4 : roomDefinitions) {
            if (definition4 != null) {
                definition4.updateOpenings();
                list.add(definition4);
            }
        }

        definition.updateOpenings();
        rand.shuffle(list);
        int i5 = 1;

        for (RoomDefinition definition3 : list) {
            int j5 = 0;
            int k5 = 0;

            while (j5 < 2 && k5 < 5) {
                ++k5;
                int l5 = rand.nextInt(6);
                if (definition3.hasOpening[l5]) {
                    int i6 = Direction.byIndex(l5).getOpposite().getIndex();
                    definition3.hasOpening[l5] = false;
                    definition3.connections[l5].hasOpening[i6] = false;
                    if (definition3.findSource(i5++) && definition3.connections[l5].findSource(i5++)) {
                        ++j5;
                    } else {
                        definition3.hasOpening[l5] = true;
                        definition3.connections[l5].hasOpening[i6] = true;
                    }
                }
            }
        }

        list.add(definition);
        list.add(definition1);
        list.add(definition2);
        return list;
    }

    private int getXWithOffset(int x, int z) {
        Direction direction = this.direction;
        if (direction == null) {
            return x;
        } else {
            switch (direction) {
                case NORTH:
                case SOUTH:
                    return this.boundingBox.minX + x;
                case WEST:
                    return this.boundingBox.maxX - z;
                case EAST:
                    return this.boundingBox.minX + z;
                default:
                    return x;
            }
        }
    }

    private int getYWithOffset(int y) {
        return this.direction == null ? y : y + this.boundingBox.minY;
    }

    private int getZWithOffset(int x, int z) {
        Direction direction = this.direction;
        if (direction == null) {
            return z;
        } else {
            switch (direction) {
                case NORTH:
                    return this.boundingBox.maxZ - z;
                case SOUTH:
                    return this.boundingBox.minZ + z;
                case WEST:
                case EAST:
                    return this.boundingBox.minZ + x;
                default:
                    return z;
            }
        }
    }

    private static BlockBox createProper(int x1, int y1, int z1, int x2, int y2, int z2) {
        return new BlockBox(Math.min(x1, x2), Math.min(y1, y2), Math.min(z1, z2), Math.max(x1, x2), Math.max(y1, y2), Math.max(z1, z2));
    }

}
