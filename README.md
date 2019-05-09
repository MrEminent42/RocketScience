# RocketGame v2
## Quickstart guide
Basic stuff
### To start a `RocketGame`:

    RocketGame game = new RocketGame();
    game.start();
To start a `RocketGame` with a with a queue of fuels to be consumed by the rocket:

    RocketGame game = new RocketGame(ArrayList<TimedFuel> fuel);

TimedFuel is an object that takes a fuel level (an integer 0-9) and a duration in miliseconds: 

    TimedFuel t = new TimedFuel(int level, long duration);

### To add fuel to be consumed for a specified duration
Add set a fuel level for a certian amount of time with this function:

    game.getRocket().addFuelToQueue(int strength, long durationMilis);
--or--

    game.getRocket().addTimedFuelToQueue(TimedFuel f);

Where `strength` is an integer between 0-9 and `durationMilis` is the duration, in miliseconds, the fuel should be consumed for. 
