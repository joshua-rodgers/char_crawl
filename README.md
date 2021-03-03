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

## how's about a little greed?

![gif of game board and character moving around, collecting gold coins](demo3.gif)

added first collectible item, gold coins. also made gameboard adjust with the window to remain centered. no idea why i took that on, it took extra time to figure out 
but i think it could be useful later as i'd like to make the gameboard larger on fullscreen or beyond a certain large size. next i think ill add an area on screen for 
printing messages for when items are collected and for keeping track of energy levels. there were some interesting decisions to be as i built in the coins. I decided 
that items should have an intrinsic method that is called when they are encountered by the player, or more generically by any other gamepiece. i created an interface, 
game_item, that stipulates this method. in the coin class, i overloaded collected(Player collector) with collected(Gamepiece collector) as i thought it might be cool 
at some point to have an enemy or other entity collect coins. perhaps a certain number of coins is necessary to accomplish some task in a level and the player must race 
against an enemy to collect enough before the enemy destrys them all or something. there's something clean to me about having the coin itself augment the player's gold 
value and then nullify itself so there's no need to screw around with modifying the game board or map when an item is collected. i'm kinda proud of structuring it that 
way. after adding the messages, i think i need to tackle random item generation and decide whether i want levels to be randomly generated or predefined. I also need to 
figure out what i want the game to look like. do i want there to be scrolling of some sort? do i want there to be doors that load new rooms when you walk through them?
i'm leaning that way because as the name of this repo suggests, there is the spirit of a dungeon crawler here. the though of that is exciting. perhaps i need to start 
thinking of enemies first?
