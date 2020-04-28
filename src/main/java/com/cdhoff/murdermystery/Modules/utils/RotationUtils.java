package com.cdhoff.murdermystery.Modules.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public enum RotationUtils
{
    ;

    public static Vec3d getEyesPos()
    {
        EntityPlayer player = Minecraft.getMinecraft().player;

        return new Vec3d(player.posX,
                player.posY + player.getEyeHeight(),
                player.posZ);
    }

    public static Vec3d getClientLookVec()
    {
        EntityPlayer player = Minecraft.getMinecraft().player;
        float f = 0.017453292F;
        float pi = (float)Math.PI;

        float f1 = MathHelper.cos(-player.cameraYaw * f - pi);
        float f2 = MathHelper.sin(-player.cameraYaw * f - pi);
        float f3 = -MathHelper.cos(-player.cameraPitch * f);
        float f4 = MathHelper.sin(-player.cameraPitch * f);

        return new Vec3d(f2 * f3, f4, f1 * f3);
    }

    public static Rotation getNeededRotations(Vec3d vec)
    {
        Vec3d eyesPos = getEyesPos();

        double diffX = vec.x - eyesPos.x;
        double diffY = vec.y - eyesPos.y;
        double diffZ = vec.z - eyesPos.z;

        double diffXZ = Math.sqrt(diffX * diffX + diffZ * diffZ);

        float yaw = (float)Math.toDegrees(Math.atan2(diffZ, diffX)) - 90F;
        float pitch = (float)-Math.toDegrees(Math.atan2(diffY, diffXZ));

        return new Rotation(yaw, pitch);
    }

    public static double getAngleToLookVec(Vec3d vec)
    {
        Rotation needed = getNeededRotations(vec);

        EntityPlayer player = Minecraft.getMinecraft().player;
        float currentYaw = MathHelper.wrapDegrees(player.cameraYaw);
        float currentPitch = MathHelper.wrapDegrees(player.cameraPitch);

        float diffYaw = currentYaw - needed.yaw;
        float diffPitch = currentPitch - needed.pitch;

        return Math.sqrt(diffYaw * diffYaw + diffPitch * diffPitch);
    }

    public static double getAngleToLastReportedLookVec(Vec3d vec)
    {
        Rotation needed = getNeededRotations(vec);

        EntityPlayer player = Minecraft.getMinecraft().player;
        float lastReportedYaw = MathHelper.wrapDegrees(player.prevCameraYaw);
        float lastReportedPitch = MathHelper.wrapDegrees(player.prevCameraPitch);

        float diffYaw = lastReportedYaw - needed.yaw;
        float diffPitch = lastReportedPitch - needed.pitch;

        return Math.sqrt(diffYaw * diffYaw + diffPitch * diffPitch);
    }

    public static float getHorizontalAngleToLookVec(Vec3d vec)
    {
        Rotation needed = getNeededRotations(vec);
        return MathHelper.wrapDegrees(Minecraft.getMinecraft().player.cameraYaw) - needed.yaw;
    }

    public static final class Rotation
    {
        private final float yaw;
        private final float pitch;

        public Rotation(float yaw, float pitch)
        {
            this.yaw = MathHelper.wrapDegrees(yaw);
            this.pitch = MathHelper.wrapDegrees(pitch);
        }

        public float getYaw()
        {
            return yaw;
        }

        public float getPitch()
        {
            return pitch;
        }
    }
}