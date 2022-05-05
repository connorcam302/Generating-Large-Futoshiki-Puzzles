# Generating-Large-Futoshiki-Puzzles

Generating-Large-Futoshiki-Puzzles contains the product file, with the classes being contained within src

The whole folder may be imported into Eclipse to run, with CreatePuzzle being the main class to be ran.

To adjust the size of the puzzle, within InstanceGenerator adjust the puzzleSize variable.

Constraints can be adjusted by changing relCount (the number of inequalities) and numCount (the number of starting values).
The recommended constraints for optimal runtime are as follows,

Size 4: relCount = 2 | numCount = 12
Size 5: relCount = 3 | numCount = 15
Size 6: relCount = 4 | numCount = 20
Size 7: relCount = 5 | numCount = 30
Size 8: relCount = 6 | numCount 45
Size 9: relCount = 7 | numCount = 65
Size 10: relCount = 8 | numCount =  95

Puzzles after generation will be outputted in the project directory inside a folder named instances, with the file being named "Generation -timeOfGeneration-.txt"
