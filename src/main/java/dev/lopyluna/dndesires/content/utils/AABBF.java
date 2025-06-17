package dev.lopyluna.dndesires.content.utils;

import net.minecraft.world.phys.AABB;

public class AABBF {
    public final float minX;
    public final float minY;
    public final float minZ;
    public final float maxX;
    public final float maxY;
    public final float maxZ;

    public AABBF(float x1, float y1, float z1, float x2, float y2, float z2) {
        this.minX = Math.min(x1, x2);
        this.minY = Math.min(y1, y2);
        this.minZ = Math.min(z1, z2);
        this.maxX = Math.max(x1, x2);
        this.maxY = Math.max(y1, y2);
        this.maxZ = Math.max(z1, z2);
    }

    public AABBF(AABB bb) {
        this.minX = (float) bb.minX;
        this.minY = (float) bb.minY;
        this.minZ = (float) bb.minZ;
        this.maxX = (float) bb.maxX;
        this.maxY = (float) bb.maxY;
        this.maxZ = (float) bb.maxZ;
    }

    public AABB toAABB() {
        return new AABB(this.minX, this.minY, this.minZ, this.maxX, this.maxY, this.maxZ);
    }
}
