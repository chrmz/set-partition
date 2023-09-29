package com.assignment1;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

class Testing
{

    public static void main(String[] args)
    {

        final int elements = 1000;


        SetPartition test1 = new SetPartition(elements);

        for (int i = 0; i < elements; i++)
        {

            if (i + 1 < elements)
            {

                test1.merge(i, (i + 1));

            }

        }

        System.out.println("Maximum node depth of the tree in test 1 is " + test1.maxDepth());


        SetPartition test2 = new SetPartition(elements);

        for (int i = elements - 1; i > 0; i--)
        {

            test2.merge((i - 1), i);

        }

        System.out.println("Maximum node depth of the tree in test 2 is " + test2.maxDepth());


        SetPartition test3 = new SetPartition(elements);

        List<Integer> maxDepths = new ArrayList<>();

        for (int i = 0; i < elements; i++)
        {

            for (int j = 0; j < 750; j++)
            {
                test3.merge(ThreadLocalRandom.current().nextInt(0, elements),
                        ThreadLocalRandom.current().nextInt(0, elements));

            }

            maxDepths.add(test3.maxDepth());
        }

        int total = 0;
        int avg;
        for (Integer maxDepth : maxDepths)
        {
            total += maxDepth;
        }
        avg = total / maxDepths.size();
        System.out.println("The Average is: " + avg);

    }


}


public class SetPartition
{
    private int[] elements;
    private int[] parents;

    public SetPartition(int numElements)
    {

        this.elements = new int[numElements];
        this.parents = new int[numElements];

        for (int i = 0; i < numElements; i++)
        {
            this.elements[i] = i;
            this.parents[i] = i;
        }
    }


    public int getRoot(int x)
    {
        int indexOfX = findIndex(x);
        if (this.parents[indexOfX] == x)
        {
            return indexOfX;
        }
        else
        {
            return getRoot(this.parents[indexOfX]);
        }
    }


    public boolean inSameSubset(int x, int y)
    {
        return getRoot(x) == getRoot(y);
    }


    public void merge(int x, int y)
    {
        if (!inSameSubset(x, y))
        {
            int yRoot = getRoot(y);
            this.parents[yRoot] = getRoot(x);
        }
    }


    public int depth(int x)
    {

        int indexOfX = findIndex(x);

        if (indexOfX == -1 || this.parents[indexOfX] == x)
        {
            return 0;
        }

        int depth = 1;

        int parent = this.parents[indexOfX];
        int parentParent = findIndex(this.parents[parent]);

        while (parent != this.elements[parentParent])
        {
            depth++;
            parent = findIndex(this.parents[parent]);
            parentParent = findIndex(this.parents[parent]);
        }
        return depth;
    }


    public int maxDepth()
    {

        int maxDepth = 0;

        for (int i = 0; i < this.elements.length; i++)
        {

            int depth = depth(this.elements[i]);

            if (depth > maxDepth)
            {
                maxDepth = depth;

            }

        }

        return maxDepth;

    }

    private int findIndex(int x)
    {
        int i = 0;
        while (i < this.elements.length)
        {
            if (this.elements[i] == x)
            {
                return i;
            }
            else
            {
                i = i + 1;
            }
        }
        return -1;
    }
}
