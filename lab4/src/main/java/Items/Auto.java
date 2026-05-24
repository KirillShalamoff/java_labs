package Items;

import java.util.List;

public class Auto extends Item{
    private final Body body;
    private final Engine engine;
    private final List<Accessory> accessories;

    public Auto(int id, Body body, Engine engine, List<Accessory> accessories) {
        super(id);
        this.body = body;
        this.engine = engine;
        this.accessories = accessories;
    }

    public Body getBody(){
        return body;
    }

    public Engine getEngine(){
        return engine;
    }

    public List<Accessory> getAccessories(){
        return accessories;
    }
}
