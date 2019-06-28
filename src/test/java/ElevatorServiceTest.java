import loger.TestLogger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class ElevatorServiceTest {

    TestLogger myLog = new TestLogger();
    ElevatorService elevatorService = new ElevatorService(myLog);


    @Test
    public void moving() throws InterruptedException {
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

        elevatorService.moving(elevator, users);
        List answer = myLog.getList();
        assertThat(answer.size()).isEqualTo(17);
        assertThat(answer.get(0)).isEqualTo("Лифт остановился на 1 этаже");
        assertThat(answer.get(5)).isEqualTo("Лифт на 2этаже двигается вверх");
        assertThat(answer.get(16)).isEqualTo("Third ввышел из лифта на этаже 1");


    }
}
//        Лифт остановился на 1 этаже
//        First ввошел в лифт на этаже 1
//        Лифт на 1этаже двигается вверх
//        Лифт остановился на 2 этаже
//        Second ввошел в лифт на этаже 2
//        Лифт на 2этаже двигается вверх
//        Лифт остановился на 3 этаже
//        Second ввышел из лифта на этаже 3
//        Лифт на 3этаже двигается вверх
//        Лифт остановился на 4 этаже
//        Third ввошел в лифт на этаже 4
//        First ввышел из лифта на этаже 4
//        Лифт на 4этаже двигается вниз
//        Лифт на 3этаже двигается вниз
//        Лифт на 2этаже двигается вниз
//        Лифт остановился на 1 этаже
//        Third ввышел из лифта на этаже 1