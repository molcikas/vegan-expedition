package ddd.persistence;

import java.nio.ByteBuffer;
import java.util.UUID;

public class UUIDExtensions
{
    public static UUID fromBytes(byte[] bytes)
    {
        ByteBuffer bb = ByteBuffer.wrap(bytes);
        return new UUID(bb.getLong(), bb.getLong());
    }

    public static byte[] toBytes(UUID uuid)
    {
        ByteBuffer bb = ByteBuffer.wrap(new byte[16]);
        bb.putLong(uuid.getMostSignificantBits());
        bb.putLong(uuid.getLeastSignificantBits());
        return bb.array();
    }
}
