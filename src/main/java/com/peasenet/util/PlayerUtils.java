package com.peasenet.util;

import com.peasenet.main.GavinsMod;
import com.peasenet.main.GavinsModClient;
import com.peasenet.util.math.Rotation;
import net.minecraft.client.render.Camera;
import net.minecraft.entity.Entity;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3f;

/**
 * @author gt3ch1
 * @version 5/23/2022
 * A helper class with utilities relating to the player.
 */
public class PlayerUtils {
    /**
     * Sets the rotation of the player.
     *
     * @param rotation The rotation to set the view to.
     */
    public static void setRotation(Rotation rotation) {
        var player = GavinsModClient.getPlayer();
        player.setPitch(rotation.getPitch());
        player.setYaw(rotation.getYaw());
    }

    /**
     * Gets the current position of the player.
     *
     * @return The current position of the player.
     */
    public static Vec3d getPlayerPos() {
        var player = GavinsModClient.getPlayer();
        return player.getPos();
    }

    /**
     * Gets the new player position after the change in time.
     *
     * @param deltaTime The change in time.
     * @param camera    The camera to use.
     * @return The new player position.
     */
    public static Vec3f getNewPlayerPosition(float deltaTime, Camera camera) {
        var look = camera.getHorizontalPlane();
        var player = GavinsModClient.getPlayer();
        var px = (float) (player.prevX + (getPlayerPos().getX() - player.prevX) * deltaTime) + look.getX();
        var py = (float) (player.prevY + (getPlayerPos().getY() - player.prevY) * deltaTime) + look.getY()
                + player.getStandingEyeHeight();
        var pz = (float) (player.prevZ + (getPlayerPos().getZ() - player.prevZ) * deltaTime) + look.getZ();
        return new Vec3f(px, py, pz);
    }

    /**
     * Makes the player do a jump.
     */
    public static void doJump() {
        var player = GavinsModClient.getPlayer();
        if (onGround())
            player.jump();
    }

    /**
     * Checks if the player is on the ground.
     *
     * @return True if the player is on the ground, false otherwise.
     */
    public static boolean onGround() {
        var player = GavinsModClient.getPlayer();
        return player.isOnGround();
    }

    /**
     * Attacks the given entity.
     *
     * @param entity The entity to attack.
     */
    public static void attackEntity(Entity entity) {
        var player = GavinsModClient.getPlayer();
        assert GavinsModClient.getMinecraftClient().interactionManager != null;
        var lastAttackTime = player.getLastAttackTime();
        if (onGround() && !player.noClip && lastAttackTime < lastAttackTime + 60) {
            GavinsModClient.getMinecraftClient().interactionManager.attackEntity(player, entity);
            player.tryAttack(entity);
            player.swingHand(Hand.MAIN_HAND);
        }
    }

    /**
     * Checks whether flight is enabled.
     */
    public static void updateFlight() {
        var player = GavinsModClient.getPlayer();
        if (player == null || player.getAbilities() == null)
            return;
        var abilities = player.getAbilities();
        abilities.allowFlying = GavinsMod.FlyEnabled() || abilities.creativeMode || GavinsMod.NoClipEnabled();
        if (GavinsMod.NoClipEnabled())
            abilities.flying = true;
        if (!abilities.creativeMode && !GavinsMod.FlyEnabled() && !GavinsMod.NoClipEnabled())
            abilities.flying = false;
    }

    /**
     * Gets the distance between the player and the given entity.
     *
     * @param entity The entity to get the distance to.
     * @return The distance between the player and the given entity.
     */
    public static double distanceToEntity(Entity entity) {
        var player = GavinsModClient.getPlayer();
        return player.squaredDistanceTo(entity);
    }


    /**
     * Gets whether the player is in creative mode.
     *
     * @return True if the player is in creative mode, false otherwise.
     */
    public static boolean isCreative() {
        var player = GavinsModClient.getPlayer();
        return player.getAbilities().creativeMode;
    }

    /**
     * Gets whether the player is falling.
     *
     * @return True if the player is falling, false otherwise.
     */
    public static boolean isFalling() {
        var player = GavinsModClient.getPlayer();
        return player.isFallFlying() || player.getVelocity().y < -0.5;
    }

    /**
     * Handles No Fall. This will prevent the player from falling when enabled.
     */
    public static void handleNoFall() {

        var player = GavinsModClient.getPlayer();
        assert player != null;
        if ((GavinsMod.NoFallEnabled() && isFalling()) || (GavinsMod.FlyEnabled() && !onGround())) {
            player.networkHandler.sendPacket(new PlayerMoveC2SPacket.OnGroundOnly(true));
        }
    }

    public static void setPosition(Vec3d pos) {
        var player = GavinsModClient.getPlayer();
        player.setPos(pos.getX(), pos.getY(), pos.getZ());
    }

    public static void moveUp(int amount) {
        var player = GavinsModClient.getPlayer();
        var pos = player.getPos();
        player.setPos(pos.getX(), pos.getY() + amount, pos.getZ());
    }
}
