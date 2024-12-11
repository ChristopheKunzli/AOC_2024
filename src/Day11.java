import java.util.*;

public class Day11 {
    public void solve(String fileName) {
        String input = Utils.getStrings(fileName).getFirst();
        System.out.println("Day 11");
        System.out.println("Part 1: " + part1(parse(input)));
        System.out.println("Part 2: " + part2(parse(input)));
    }

    private List<Long> parse(String s) {
        List<Long> res = new ArrayList<>();
        for (String num : s.split(" ")) {
            res.add(Long.parseLong(num));
        }
        return res;
    }

    private long part1(List<Long> stones) {
        return blinkNTimes(stones, 25);
    }

    private long part2(List<Long> stones) {
        return blinkNTimes(stones, 75);
    }

    List<Long> computeStone(Long stone) {
        String stoneStr = "" + stone;
        if (stone == 0L) {
            return List.of(1L);
        } else if (stoneStr.length() % 2  == 0) {
            int splitLoc = stoneStr.length() / 2;
            return List.of(Long.parseLong(stoneStr.substring(0, splitLoc)), Long.parseLong(stoneStr.substring(splitLoc)));
        } else {
            return List.of(stone * 2024);
        }
    }

    long blinkNTimes(List<Long> stones, int n) {
        List<Long> nextStones = new ArrayList<>(stones);
        Map<Long, Long> count = new HashMap<>();
        for (Long st : nextStones) {
            count.put(st, count.getOrDefault(st, 0L) + 1);
        }
        for (int i = 0; i < n; i++) {
            Map<Long, Long> nextCount = new HashMap<>();
            for (Map.Entry<Long, Long> e : count.entrySet()) {
                nextStones = computeStone(e.getKey());
                for (Long st : nextStones) {
                    nextCount.put(st, nextCount.getOrDefault(st, 0L) + e.getValue());
                }
            }
            count = nextCount;
        }
        return count.values().stream().mapToLong(l -> l).sum();
    }
}
