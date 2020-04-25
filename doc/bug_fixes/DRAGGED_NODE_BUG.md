## Author
Pierce Forte

## Description
The behavior of items when dragged during the builder stage is not as expected.

## Expected Behavior
I would expect that when an item is dragged during the builder stage it would be given the highest priority, ensuring that it always remains reachable for the user.

## Current Behavior
When a node is dragged and then dropped during the builder stage, it is being placed behind nodes that were placed more recently.

## Steps to Reproduce
 1. Run the game and proceed to the builder stage.
 2. Purchase one and item and drag it somewhere.
 3. Purchase another item and place it.
 4. Drag the first item and drop it on top of the newer item.
 5. Notice that the first item now appears behind the newer item and is unreachable.

## Failure Logs
N/A

## Hypothesis for Fixing the Bug
The test that will verify this bug is testing to see if when an older item has a mouse drag event fired on it, its priority (index in the pane's children) will be lower than the newer item that has not been dragged (the higher the index the more priority, aka the more on "top" of the pane). I believe this issue can be solved by overriding the way my NodeDragger class is used for these nodes such that dragged nodes will immediately be given top priority in the pane.   