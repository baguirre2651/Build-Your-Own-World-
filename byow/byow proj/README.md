# Build Your Own World Design Document

**Bryan Aguirre:**


## Classes and Data Structures

Main: main runner

Engine: Handles the inputs for the main method

RandomUtils: creates psuedo-random scenarios.

Point: X and Y coordinates

Hallway: Creates the Path that connects two rooms.

Room: Creates a room around a center position with a random width and height.

World: Creates a random number of rooms with different dimensions, and connected hallways.

GameFile: Used to Save Our World. Saves the Game using a Hash Map.

StringInput: Prepares our string to generate a seed.

Avatar: Character and Movements.

Keyboard Interactor: Interacts with Keyboard Movements, as well as has HUD display, Spanish Mode Display, and other requirements for the project. 

String Interactor: Allows us to pass in a string and world in our configuriations. 

## Algorithms

Random World Generator that builds rooms around our single point. Allowing us to keep track of the middle point and based on its position, we are able to connect two rooms using hallways based on a certain direction. By inputing a string of numbers, the algorithm is able to take in a string and convert it into a seed, where each seed is embedded and capable of generating the same or different world based on the input. There is an Avatar character that is able to move around the world using keyboard inputs of 'WASD' as well as capable of toggling the world using keyboard input 'V' from light mode to dark mode feature. Using a HashMap in our GameFile Class , we are able to save our world and load back from our previous position. And as a result, we create a Game Engine that generates explorable 2D tile-based worlds! 

## Persistence
We use Persistence Utils in which we use it to read and write Serialized information. 
