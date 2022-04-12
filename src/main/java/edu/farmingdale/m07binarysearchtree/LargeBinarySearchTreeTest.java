/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.farmingdale.m07binarysearchtree;

import java.util.Random;
import java.util.TreeSet;
import java.util.Set;
import java.util.Iterator;

/**
 *
 * @author gerstl
 */
public class LargeBinarySearchTreeTest implements RunTest {

    public String runTest() {
        // we'll compare with a set
        BinarySearchTree<String> theTree = new BinarySearchTree<String>();
        Set<String> theSet = new TreeSet<>();
        final int TEST_SIZE = 100_000;
        var random = new Random();
        // this is defined in java, so not necessary to compute, but I do so 
        // you can see how I compute it
        int MAX_WIDTH = (int) Math.log10(Integer.MAX_VALUE) + 1;
        System.out.println("MAX_WIDTH is " + MAX_WIDTH);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < TEST_SIZE; ++i) {
            // statistically, we anticipate that nunbers between 0..test_size
            // should occassionally repeat. 
            // we want to avoid zeros, hense the + 1
            int aNumber = random.nextInt(TEST_SIZE - 1) + 1;
            String sNumber = Integer.toString(aNumber);
            // now pad it.
            sb.delete(0, sb.length());
            while (sb.length() < MAX_WIDTH - sNumber.length()) {
                sb.append("0");
            }
            sb.append(sNumber);
            sNumber = sb.toString();
            boolean treeSuccess = theTree.insert(sNumber);
            boolean setSuccess = theSet.add(sNumber);
            // this is an error if the two booleans are not equal
            if (treeSuccess != setSuccess) {
                System.out.println("Failed on iteration " + i
                        + " with inconsistent results adding " + sNumber);
                if (setSuccess) {
                    System.out.println("The set thinks this is not a duplicate");
                } else {
                    System.out.println("Your tree thinks this is not a duplicate");

                }
                return "Failed at A0300";
            }
        } // insertion for loop
        Iterator<String> iterator = theSet.iterator();
        while (iterator.hasNext()) {
            String element = iterator.next();
            // check for -(element) in the tree. should fail
            String negativeElement = "-" + element;
            if (theTree.search(negativeElement)) {
                return "Failed at A0301";
            }
            // check for element in the tree. Should succeed.
            if (!theTree.search(element)) {
                return "Failed at A0302";
            }

        }
        // remove about TEST_SIZE/5 elements from the bst and set. Store somewhere
        Set<String> removedElements = new TreeSet<>();
        iterator = theSet.iterator();
        int count = 0;
        while (iterator.hasNext()) {
            String element = iterator.next();
            if ((++count % 5) == 0) {
                removedElements.add(element);
                iterator.remove();
                theTree.remove(element);
            }
        }
        // now run the test above again. 
        iterator = theSet.iterator();
        while (iterator.hasNext()) {
            String element = iterator.next();
            // check for element in the tree. Should succeed.
            if (!theTree.search(element)) {
                return "Failed at A0303";
            }
        }
        // now verify the deleted elements are still missing
        Iterator<String> removedElementsIterator = removedElements.iterator();
        while (removedElementsIterator.hasNext()) {
            String element = removedElementsIterator.next();
            if (theTree.search(element)) {
                return "Failed at A0304";
            }
        }

        // now the same with Integers
        // we'll compare with a set
        BinarySearchTree<Integer> theIntegerTree = new BinarySearchTree<>();
        Set<Integer> theIntegerSet = new TreeSet<>();
        // this is defined in java, so not necessary to compute, but I do so 
        // you can see how I compute it
        for (int i = 0; i < TEST_SIZE; ++i) {
            // statistically, we anticipate that nunbers between 0..test_size
            // should occassionally repeat. 
            // we want to avoid zeros, hense the + 1
            int aNumber = random.nextInt(TEST_SIZE - 1) + 1;
            boolean treeSuccess = theIntegerTree.insert(aNumber);
            boolean setSuccess = theIntegerSet.add(aNumber);
            // this is an error if the two booleans are not equal
            if (treeSuccess != setSuccess) {
                System.out.println("Failed on iteration " + i
                        + " with inconsistent results adding " + aNumber);
                if (setSuccess) {
                    System.out.println("The set thinks this is not a duplicate");
                } else {
                    System.out.println("Your tree thinks this is not a duplicate");
                }
                return "Failed at A0305";
            }
        } // insertion for loop
        Iterator<Integer> integerIterator = theIntegerSet.iterator();
        while (integerIterator.hasNext()) {
            Integer element = integerIterator.next();
            // check for -(element) in the tree. should fail
            if (theIntegerTree.search(-1 * element)) {
                return "Failed at A0306";
            }
            // check for element in the tree. Should succeed.
            if (!theIntegerTree.search(element)) {
                System.out.println(theIntegerTree);
                return "Failed at A0307";
            }

        }
        // remove about TEST_SIZE/5 elements from the bst and set. Store somewhere
        Set<Integer> removedIntegerElements = new TreeSet<>();
        integerIterator = theIntegerSet.iterator();
        count = 0;
        while (integerIterator.hasNext()) {
            Integer element = integerIterator.next();
            if ((++count % 5) == 0) {
                removedIntegerElements.add(element);
                integerIterator.remove();
                theIntegerTree.remove(element);
            }
        }
        // now run the test above again. 
        integerIterator = theIntegerSet.iterator();
        while (integerIterator.hasNext()) {
            Integer element = integerIterator.next();
            // check for element in the tree. Should succeed.
            if (!theIntegerTree.search(element)) {
                return "Failed at A0308";
            }
        }
        // now verify the deleted elements are still missing
        Iterator<Integer> removedIntegerElementsIterator = removedIntegerElements.iterator();
        while (removedIntegerElementsIterator.hasNext()) {
            Integer element = removedIntegerElementsIterator.next();
            if (theIntegerTree.search(element)) {
                return "Failed at A0309";
            }
        }

        return "";
    }
}
