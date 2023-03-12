package game.object.tetromino;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;

public class Bag {
    Tetromino[] defaultBag = new Tetromino[] {
            new T(), new L(), new S(),
            new I(), new Z(), new J(),
            new O()
    };

    public Queue<Tetromino> bag;
    private ArrayList<Tetromino> listBag = new ArrayList<>(Arrays.asList(defaultBag));

    public Bag() {
        reset();
    }

    public void reset() {
        bag = new LinkedList<>();
        Collections.shuffle(listBag);
        bag.addAll(listBag);
        Collections.shuffle(listBag);
        bag.addAll(listBag);
    }

    public void checkBag() {
        if (bag.size() <= 7) {
            Collections.shuffle(listBag);

            bag.addAll(listBag);
        }
    }
}
