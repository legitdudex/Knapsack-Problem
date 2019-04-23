# Knapsack-Problem
My algorithm uses ratios of the profits to weights of each potential bag element in order to approximate the most efficient 
  combination of bag elements to put into the bag, given the bag capacity.

We first calculate the ratios of each potential bag element: linear time
Then we sort them based on the ratios: linear time
Then we add them to the bag starting from the highest value to weight ratio item to the lowest, depending on the capacity 
  remaining in the bag.

Note that we added index values to the tuple of each element according to their index in their unsorted array so we can know
  which bag element to check off in the sorted array.

After using the inputs given in https://people.sc.fsu.edu/~jburkardt/datasets/knapsack_01/knapsack_01.html
  we were able to approximate the combination of bag elements we put should put into our bag to give us the most profit by an 
   error margin of less than 8%.
This is much faster than the vanilla algorithm, which has runs at a polynomial time.

