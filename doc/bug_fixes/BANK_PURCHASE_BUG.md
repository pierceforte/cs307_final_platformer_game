## Author
Pierce Forte

## Description
The behavior of bank items when added to the builder pane is unexpected.  
**NOTE**  
The code added to reproduce this bug was previously written before the deadline in my branch "phf7". Given that I was unable to fix the bug before the deadline, I removed the code causing it, which meant that I had to make the bank system less flexible.

## Expected Behavior
I would expect that when an item is purchased, it would be placed on *top* of the builder pane, temporarily disable the drag feature of the builder pane, and be accessible to the user.  

## Current Behavior
When a bank item is purchased, it does not appear to the user. It seems to be behind the builder pane in some way, for it does not disable the builder pane's drag feature as expected, nor is it accessible in any way.

## Steps to Reproduce
 1. Run the game and proceed to the builder stage.
 2. Purchase an item.
 3. Notice that the item is not visible to the user, cannot be accessed, and does not disable the builder pane's drag feature.
 **NOTE**  
 On some occasions, the purchase actually functions as intended, which has made this bug all the more difficult to solve.

## Failure Logs
While I do not have any failure logs to indicate the issue, there is one thing that I have noticed. When the bug occurs, it appears that it causes the builder pane to clear all of its objects and add them again on every *other* step. I noticed this by printing the number of children after each step. Again, I am not sure what the cause of this is, but when the purchases function correctly, this does not seem to occur.

## Hypothesis for Fixing the Bug
The test that will verify this bug is purchasing an item from the bank and asserting that the builder pane is *not* draggable. While I do not have any hypotheses for how to fix this bug, I do know exactly which lines of code have caused it (which, as I mentioned above, I re-added to the code). As such, I will focus here. 
