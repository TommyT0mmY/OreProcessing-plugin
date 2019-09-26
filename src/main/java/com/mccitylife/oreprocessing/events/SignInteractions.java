package com.mccitylife.oreprocessing.events;

import com.mccitylife.oreprocessing.OreProcessing;
import com.mccitylife.oreprocessing.utility.Pair;
import com.mccitylife.oreprocessing.utility.Permissions;
import com.mccitylife.oreprocessing.utility.XMaterial;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Sign;
import org.bukkit.block.data.type.WallSign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.material.Directional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class SignInteractions implements Listener {

    private OreProcessing mainClass = OreProcessing.getInstance();
    private List<Location> placedWallSigns = new ArrayList<>();

    @EventHandler
    public void startedPlacing(BlockPlaceEvent e) {
        Player p = e.getPlayer();
        Block placed = e.getBlockPlaced();
        if (!isWallSign(placed)) {
            return;
        }
        if (!p.hasPermission(Permissions.PLACE_SIGN.getNode())) {
            return;
        }
        placedWallSigns.add(placed.getLocation());
    }

    @EventHandler
    public void finishedPlacing(SignChangeEvent e) {
        Player p = e.getPlayer();
        Block placedBlock = e.getBlock();
        WallSign wallsign;

        if (!placedWallSigns.contains(placedBlock.getLocation())) return;
        placedWallSigns.remove(placedBlock.getLocation());

        try {
            wallsign = (WallSign) placedBlock.getBlockData();
        }catch (Exception exc) { return; }

        Pair opposites = getOpposites(wallsign, placedBlock.getLocation());
        Block opposite1 = (Block) opposites.first();
        Block opposite2 = (Block) opposites.second();

        opposite1.setType(XMaterial.BLUE_TERRACOTTA.parseMaterial()); //TODO REMOVE
        opposite2.setType(XMaterial.GLOWSTONE.parseMaterial()); //TODO REMOVE

    }

    @EventHandler
    public void rightClickedSign (PlayerInteractEvent e) {
        Player p = e.getPlayer();
        Action action = e.getAction();
        if (!action.equals(Action.RIGHT_CLICK_BLOCK)) {
            return;
        }


    }

    private Pair getOpposites (WallSign wallsign, Location loc) {
        BlockFace facing = wallsign.getFacing();

        Location opposite1 = loc;
        Location opposite2 = loc;

        //getting opposite arena points for each facing
        switch (facing) {
            case EAST:
                opposite1.add(5, 0,-2);
                opposite2.add(1, -1, 2);

                break;
            case WEST:
                opposite1.add(-5, 0, -2);
                opposite2.add(-1, -1, 2);

                break;
            case SOUTH:
                opposite1.add(-2, 0, 5);
                opposite2.add( 2, -1, 1);

                break;
            case NORTH:
                opposite1.add(-2, 0, -5);
                opposite2.add( 2, -1, -1);

                break;
        }

        return new Pair(opposite1.getBlock(), opposite2.getBlock());
    }


    private boolean isWallSign(Block block) {
        Material bMaterial = block.getType();
        if (bMaterial.equals(XMaterial.OAK_WALL_SIGN.parseMaterial()) ||
                bMaterial.equals(XMaterial.ACACIA_WALL_SIGN.parseMaterial()) ||
                bMaterial.equals(XMaterial.BIRCH_WALL_SIGN.parseMaterial()) ||
                bMaterial.equals(XMaterial.DARK_OAK_WALL_SIGN.parseMaterial()) ||
                bMaterial.equals(XMaterial.JUNGLE_WALL_SIGN.parseMaterial()) ||
                bMaterial.equals(XMaterial.SPRUCE_WALL_SIGN.parseMaterial())) return true;
        return false;
    }
}
