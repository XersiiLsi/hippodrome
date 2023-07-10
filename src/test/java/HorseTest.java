import org.apache.logging.log4j.core.net.TcpSocketManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.stream.Stream;

@ExtendWith(MockitoExtension.class)
class HorseTest {

    Horse horse;

    @Test
    void whatsBeWithNull() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> {
                    horse = new Horse(null, 11, 11);
                });
        assertTrue(exception.getMessage().contains("Name cannot be null."));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "      ", " "})
    void whatsBeWithSpace(String sym) {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> {
                    horse = new Horse(sym, 11, 11);
                });
        assertTrue(exception.getMessage().contains("Name cannot be blank."));
    }

    @Test
    void secondArgumentMinus() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> {
                    horse = new Horse("horse", -12, 22);
                });
        assertTrue(exception.getMessage().contains("Speed cannot be negative."));
    }

    @Test
    void thirdArgumentMinus() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> {
                    horse = new Horse("Default", 12, -11);
                });
        assertTrue(exception.getMessage().contains("Distance cannot be negative."));
    }


    @Test
    void getName() {
        horse = new Horse("King", 100, 11);
        assertEquals("King", horse.getName());
    }

    @Test
    void getSpeed() {
        horse = new Horse("King", 100, 11);
        assertEquals(100, horse.getSpeed());
    }

    @Test
    void getDistance() {
        Horse horse1 = new Horse("King", 100, 11);
        Horse horse2 = new Horse("Belyash", 5);

        assertEquals(11, horse1.getDistance());
        assertEquals(0, horse2.getDistance());
    }


    @Test
    void move() {
        horse = new Horse("King", 100, 11);
        try (MockedStatic<Horse> horseMock = Mockito.mockStatic(Horse.class)) {

            horse.move();
            horseMock.verify(() -> horse.getRandomDouble(0.2, 0.9), Mockito.atLeastOnce());
        }
    }

    private static Stream<Arguments> testArguments() {
        return Stream.of(
                Arguments.of(12, 34),
                Arguments.of(5, 7),
                Arguments.of(54, 11),
                Arguments.of(63, 10)
        );
    }

    @ParameterizedTest
    @MethodSource("testArguments")
    void workWithFormula(int speed, int distance) {
        Horse horse = new Horse("King", speed, distance);
        try (MockedStatic<Horse> horseMock = Mockito.mockStatic(Horse.class)) {
            double res = (distance + speed * horse.getRandomDouble(0.2, 0.9));
            horseMock.when(() -> horse.move()).thenReturn(res);

            assertEquals(horse.getRandomDouble(0.2, 0.9), res);
        }
    }


}