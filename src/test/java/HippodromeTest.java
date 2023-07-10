import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class HippodromeTest {

    Hippodrome hippodrome;

    Horse horse = new Horse("King", 11, 43);

    List<Horse> horses = new ArrayList<>();
    List<Horse> emptyHorses = new ArrayList<>();

    @Test
    void whenUseNull() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> hippodrome = new Hippodrome(null)
        );
        assertTrue(exception.getMessage().contains("Horses cannot be null."));
    }

    @Test
    void whenAddNothing() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> hippodrome = new Hippodrome(emptyHorses)
        );
        assertEquals(exception.getMessage(), "Horses cannot be empty.");
    }

    @Test
    void getHorses() {
        List<Horse> horses = Mockito.spy(List.class);
        hippodrome = new Hippodrome(horses);
        assertEquals(hippodrome.getHorses(), horses);
    }

    @Test
    void move() {
        for (int i = 0; i < 50; i++) {
            horses.add(Mockito.mock(Horse.class));
        }
        hippodrome = new Hippodrome(horses);
        hippodrome.move();

        for (Horse horse : horses) {
            Mockito.verify(horse, Mockito.atLeastOnce()).move();
        }
    }

    @Test
    void getWinner() {
        for (int i = 0; i < 5; i++) {
            horses.add(new Horse("horse" + i, i, i));
        }
        hippodrome = new Hippodrome(horses);
        for (int i = 0; i < 5; i++) {
            hippodrome.move();
        }
        assertEquals(hippodrome.getWinner(), horses.get(4));
    }
}