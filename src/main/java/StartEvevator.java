import loger.LoggerProd;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class StartEvevator {
    public static void main(String[] args) throws InterruptedException {
        User first = new User();
        first.setName("First");
        first.setDestination(4L);
        first.setLocation(1L);
        first.setStatus(UserStatus.CAUSED);

        User second = new User();
        second.setName("Second");
        second.setDestination(3L);
        second.setLocation(2L);
        second.setStatus(UserStatus.CAUSED);

        User third = new User();
        third.setName("Third");
        third.setDestination(1L);
        third.setLocation(4L);
        third.setStatus(UserStatus.CAUSED);

        List<User> users = new ArrayList<User>();
        users.add(first);
        users.add(second);
        users.add(third);

        Elevator elevator = new Elevator();
        elevator.setDirection(Direction.UP);
        elevator.setLocation(1L);
        elevator.setFloorStopEnterToElevator(new HashSet<>(Arrays.asList(1L, 2L, 4L)));
        new ElevatorService(new LoggerProd()).moving(elevator, users);
    }

}

