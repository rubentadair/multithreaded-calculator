import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Solution implements CommandRunner {
    private final Map<Long, Future<Integer>> runningCalculations = new HashMap<>();
    private final Map<Long, Long> scheduledCalculations = new HashMap<>();
    private final ExecutorService executorService = Executors.newCachedThreadPool();

    @Override
    public String runCommand(String command) {
        String[] parts = command.split(" ");
        if (parts.length == 0) {
            return "Invalid command";
        }

        String action = parts[0];

        switch (action) {
            case "start":
                if (parts.length != 2 || !isInteger(parts[1])) {
                    return "Invalid command";
                }
                return startCalculation(Long.parseLong(parts[1]));
            case "cancel":
                if (parts.length != 2 || !isInteger(parts[1])) {
                    return "Invalid command";
                }
                return cancelCalculation(Long.parseLong(parts[1]));
            case "running":
                return getRunningCalculations();
            case "get":
                if (parts.length != 2 || !isInteger(parts[1])) {
                    return "Invalid command";
                }
                return getCalculationResult(Long.parseLong(parts[1]));
            case "after":
                if (parts.length != 3 || !isInteger(parts[1]) || !isInteger(parts[2])) {
                    return "Invalid command";
                }
                return scheduleCalculation(Long.parseLong(parts[1]), Long.parseLong(parts[2]));
            case "finish":
                return finishAllCalculations();
            case "abort":
                return abortAllCalculations();
            default:
                return "Invalid command";
        }
    }

    private boolean isInteger(String s) {
        try {
            Long.parseLong(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private String startCalculation(long n) {
        if (runningCalculations.containsKey(n)) {
            return "already running";
        }
        Future<Integer> future = executorService.submit(new SlowCalculator(n));
        runningCalculations.put(n, future);
        return "started " + n;
    }

    private String cancelCalculation(long n) {
        Future<Integer> future = runningCalculations.remove(n);
        if (future != null) {
            future.cancel(true);
            startScheduledCalculation(n);
        }
        return "cancelled " + n;
    }

    private void startScheduledCalculation(long n) {
        Long m = scheduledCalculations.remove(n);
        if (m != null) {
            startCalculation(m);
        }
    }

    private String getRunningCalculations() {
        if (runningCalculations.isEmpty()) {
            return "no calculations running";
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append(runningCalculations.size()).append(" calculations running:");
            for (Long n : runningCalculations.keySet()) {
                sb.append(" ").append(n);
            }
            return sb.toString();
        }
    }

    private String getCalculationResult(long n) {
        Future<Integer> future = runningCalculations.get(n);
        if (future == null) {
            return "not running";
        } else if (future.isDone()) {
            try {
                int result = future.get();
                runningCalculations.remove(n);
                startScheduledCalculation(n);
                return "result is " + result;
            } catch (Exception e) {
                return "cancelled";
            }
        } else {
            return "calculating";
        }
    }

    private String scheduleCalculation(long n, long m) {
        if (runningCalculations.containsKey(n)) {
            scheduledCalculations.put(n, m);
            return m + " will start after " + n;
        } else {
            return "calculation " + n + " is not running";
        }
    }

    private String finishAllCalculations() {
        for (Future<Integer> future : runningCalculations.values()) {
            try {
                future.get();
            } catch (Exception e) {
                // ignore
            }
        }
        runningCalculations.clear();
        startScheduledCalculations();
        return "finished";
    }

    private String abortAllCalculations() {
        for (Future<Integer> future : runningCalculations.values()) {
            future.cancel(true);
        }
        runningCalculations.clear();
        scheduledCalculations.clear();
        return "aborted";
    }

    private void startScheduledCalculations() {
        for (Map.Entry<Long, Long> entry : scheduledCalculations.entrySet()) {
            long n = entry.getKey();
            long m = entry.getValue();
            if (!runningCalculations.containsKey(n)) {
                startCalculation(m);
            }
        }
        scheduledCalculations.clear();
    }
}