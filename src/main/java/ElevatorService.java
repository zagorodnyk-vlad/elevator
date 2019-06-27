

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class ElevatorService {

    public static final Logger LOG = LoggerFactory.getLogger(ElevatorService.class);
    private static final Long MAX_FLOOR = 4L;
    private static final Long STEP = MAX_FLOOR * 2L;

    public void moving(Elevator elevator, List<User> users) throws InterruptedException {
        Set<Long> floorStopExitFromElevator = new HashSet();
        for (int i = 0; i < STEP; i++) {
            boolean stopEnter = users.stream()
                    .anyMatch(u -> u.getLocation().equals(elevator.getLocation()) && UserStatus.CAUSED.equals(u.getStatus()));

            boolean stopExit = users.stream()
                    .anyMatch(u -> u.getDestination().equals(elevator.getLocation()) && UserStatus.INSIDE.equals(u.getStatus()));
            if (stopEnter || stopExit) {
                TimeUnit.SECONDS.sleep(4);
                LOG.info("Лифт остановился на " + elevator.getLocation() + " этаже");
            }
            if (stopEnter) {
                List usersEnterInElevator = users.stream().filter(user ->
                        user.getLocation().equals(elevator.getLocation())
                                && UserStatus.CAUSED.equals(user.getStatus())).collect(Collectors.toList());
                enterInElevator(elevator, usersEnterInElevator, floorStopExitFromElevator);
            }
            if (stopExit) {
                List usersExitFromElevator = users.stream().filter(user ->
                        user.getDestination().equals(elevator.getLocation())
                                && UserStatus.INSIDE.equals(user.getStatus())).collect(Collectors.toList());
                exitWithElevator(elevator, usersExitFromElevator);
            }
            elevatorMoving(elevator);
        }
    }

    private void enterInElevator(Elevator elevator, List<User> users, Set<Long> floorStopExitFromElevator) throws InterruptedException {
        if (users.isEmpty()) return;
        users.stream()
                .forEach(user -> {
                    user.setStatus(UserStatus.INSIDE);
                    floorStopExitFromElevator.add(user.getDestination());
                    LOG.info(user.getName() + " ввошел в лифт на этаже " + elevator.getLocation());
                });
    }

    private void exitWithElevator(Elevator elevator, List<User> users) {
        if (users.isEmpty()) return;
        users.stream()
                .forEach(user -> {
                    user.setStatus(UserStatus.EXIT);
                    LOG.info(user.getName() + " ввышел из лифта на этаже " + elevator.getLocation());
                });
    }

    private void elevatorMoving(Elevator elevator) throws InterruptedException {

        if (MAX_FLOOR == elevator.getLocation()) {
            elevator.setDirection(Direction.DOWN);
        }
        if (Direction.DOWN.equals(elevator.getDirection()) && elevator.getLocation() != 1) {
            LOG.info("Лифт на " + elevator.getLocation() + "этаже" + " двигается " + elevator.getDirection().getTranslate());
            elevator.setLocation(elevator.getLocation() - 1);
            TimeUnit.SECONDS.sleep(4);

        } else if (Direction.DOWN.equals(elevator.getDirection()) && elevator.getLocation() == 1) {
            return;
        } else {
            LOG.info("Лифт на " + elevator.getLocation() + "этаже" + " двигается " + elevator.getDirection().getTranslate());
            elevator.setLocation(elevator.getLocation() + 1);
            TimeUnit.SECONDS.sleep(4);
        }


    }
}
