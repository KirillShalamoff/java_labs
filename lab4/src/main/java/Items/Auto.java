package Items;

public class Auto extends Item{
    private final Body body;
    private final Engine engine;
    private final Accessory accessory;

    public Auto(int id, Body body, Engine engine, Accessory accessory) {
        super(id);
        this.body = body;
        this.engine = engine;
        this.accessory = accessory;
    }

    public Body getBody(){
        return body;
    }

    public Engine getEngine(){
        return engine;
    }

    public Accessory getAccessory(){
        return accessory;
    }
}
