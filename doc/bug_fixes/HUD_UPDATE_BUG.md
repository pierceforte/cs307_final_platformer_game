## Author
Pierce Forte

## Description
The behavior of the HUD when the level changes is unexpected.

## Expected Behavior
I would expect that when the level changes, the HUD would update the level number by adding 1.
## Current Behavior
When the level changes, the level number does not change in the HUD.

## Steps to Reproduce
 1. Run the game and proceed to the builder stage.
 2. Play the first level and complete it.
 3. Advance to the next level and notice that the HUD's level number display has not updated.

## Failure Logs
N/A

## Hypothesis for Fixing the Bug
The test that will verify this bug is running the game, making a request to change the level, and verifying that the HUD's level display has not changed.