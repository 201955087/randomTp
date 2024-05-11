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
        // 범위 -10000 ~ 10000 사이의 x, z 좌표 생성
        int maxY = world.getMaxHeight();
        // y좌표는 월드 내의 가장 높은 위치로 생성
        for (int i = maxY; i > -64; i--) {
            // i를 y좌표로 지정, 가장 밑바닥 좌표인 -64까지 1씩 빼면서 해당 for문 실행
            Location testLocation = new Location(world, randomX, i, randomZ);
            // 랜덤으로 정해진 x, z에 i를 y좌표로 두는 testLocation 위치 정보 선언
            if (safeLocation(testLocation)) {
                testLocation.add(0, 1, 0);
                safeList.add(testLocation);
                i--;
                // safeLocation 함수의 조건을 만족할 시 해당 testLocation의
                // Y좌표에 1을 더하고, safeList 리스트에 해당 좌표 저장
                // 그러고 i다시 1 감소 ( 안할 시 i가 1더해진 채로 for 문이 진행됨)
            }
        }
        player.teleport(safeList.get(random.nextInt(safeList.size())));
        // 결과적으로 플레이어가 서있을 수 있는 좌표가 저장된 safeList 내의
        // 좌표 목록 중 랜덤하게 하나의 좌표를 뽑아서 해당 좌표로 플레이어 텔포
        player.sendMessage("텔포 완료");
    }

    private boolean safeLocation(Location location) {
        if(location.getBlock().getRelative(0, 2, 0).getType().isAir()
        && location.getBlock().getRelative(0, 1, 0).getType().isAir()
        && !location.getBlock().isPassable()) {
            // 해당 위치의 y좌표로 두칸위가 공기일 때, 해당 위치 블록이 통과하는 블록이 아닐때 참
            return true;
        }
        return false;
    }
}


