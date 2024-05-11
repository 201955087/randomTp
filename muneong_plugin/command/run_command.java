package me.air_bottle.muneong_plugin.command;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class run_command extends BukkitCommand {

    public run_command(@NotNull String name) {
        super(name);
    }

    @Override
    public boolean execute(@NotNull CommandSender commandSender, @NotNull String s, @NotNull String[] strings) {
        if (commandSender instanceof Player player) {
            randomTp(player); // 플레이어만 해당 명령어 입력 시 randomTp 함수 실행
        }
        return false;
    }

    public void randomTp(Player player) {
        World world = player.getWorld(); // 플레이어가 있는 월드 받아주기
        Random random = new Random(); // 랜덤 변수 받아오기

        List<Location> safeList = new ArrayList<>();
        // 조건에 충족되는(플레이어가 서있을 수 있는 위치) 위치 좌표를 담아놓을 리스트 미리 생성
        int randomX = random.nextInt(20001) - 10000;
        int randomZ = random.nextInt(20001) - 10000;
        //
        int maxY = world.getMaxHeight();
        for (int i = maxY; i > -64; i--) {
            Location testLocation = new Location(world, randomX, i, randomZ);
            if (safeLocation(testLocation)) {
                testLocation.add(0, 1, 0);
                safeList.add(testLocation);
                i--;
            }
        }
        player.teleport(safeList.get(random.nextInt(safeList.size())));
        player.sendMessage("텔포 완료");
    }

    private boolean safeLocation(Location location) {
        if(location.getBlock().getRelative(0, 2, 0).getType().isAir()
        && location.getBlock().getRelative(0, 1, 0).getType().isAir()
        && !location.getBlock().isPassable()) {
            return true;
        }
        return false;
    }
}


