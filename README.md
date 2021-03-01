# char_crawl
simple game inspired by rogue/dugeon crawlers. experimenting to see how much fun can be had with mere text moving around the screen.

## first test demo
basic movement implemented. maybe let's collect some coins next?

![gif of game board and character mving around](demo1.gif)

## broke it, restarted...

commiting directly to master finally slew me. while sketching out moving gamepieces and updating them on screen, i built myself into a non-working state and 
didn't feel like reverting commits to get back to a working version, so i just re-implemented everything from scratch. this worked out as i got past my sticking point.
i managed to create a clean enough relationship between the gamepiece objects with their position data and what-not and the char array that serves to build the 
strings that ultimately represent them on screen. the key ended up being giving each game object a reference to the world they live in, map[][]. this makes moving 
much nicer in that the data model and the view are separated cleanly. gamepieces can move around in map[][] and board[][] can just be updated to show any changes just before 
rendering. next i need to implement collision detection and the basics of how gempieces will interact. right now, as the demo shows, the player can go out of bounds.

![otro gif of game board and character moving around, going out of bounds](demo2.gif)
