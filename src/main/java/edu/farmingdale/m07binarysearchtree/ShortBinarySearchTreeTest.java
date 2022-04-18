/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.farmingdale.m07binarysearchtree;

/**
 *
 * @author gerstl
 */
public class ShortBinarySearchTreeTest implements RunTest {

    public String runTest() {
        BinarySearchTree<String> theTree = new BinarySearchTree<>();
        if (!theTree.insert("root")) {
            return "Failed at A0001";
        }
        System.out.println(theTree);
        if (!theTree.insert("lChildRoot")) {
            return "Failed at A0002";
        }
        if (!theTree.insert("rootRightChild")) {
            return "Failed at A0003";
        }
        if (!theTree.insert("somewhere3rdLevelRightmost")) {
            return "Failed at A0004";
        }

        /*
         * the tree so far:
         *       somewhere3rdLevelRightmost
         *   rootRightChild
         * root
         *   lChildRoot
         */
        if (!theTree.search("root")) {
            return "Failed at A0005";
        }
        if (!theTree.insert("lChildRootRightChild")) {
            return "Failed at A0006";
        }
        /* The tree so far:
        *         somewhere3rdLevelRightmost
               rootRightChild
            root
                  lChildRootRightChild
               lChildRoot
         */
        System.out.println("Before any removals, the tree is:\n" + theTree);

        // Consumer<T> (see https://docs.oracle.com/javase/8/docs/api/java/util/function/Consumer.html)
        // is a SAM (single abstract method) type
        // so we can use lambda to define an action (for example print)
        // and pass this to our traversal (which takes a Consumer)
        // without defining a class implementing the interface
        // Note that all Object(s) have toString(), so every Comparable
        // can be printed, so the system will allow this. Depending on the type, 
        // it may not print the content, however (it may print an address)
        
        System.out.print("Prefix traversal: ");
        // we can output directly
        theTree.dfsPrefix((t) -> {
            System.out.print(t + ", ");
        });
        // let's test this works correctly: Prefix
        String expectedPrefixString = 
                "root, lChildRoot, lChildRootRightChild, rootRightChild, somewhere3rdLevelRightmost, ";
        StringBuilder prefixSB = new StringBuilder();
        theTree.dfsPrefix((t)->{prefixSB.append(t+", ");});
        if (!prefixSB.toString().equals(expectedPrefixString)){
            System.err.print("Prefix should be:");
            System.err.println("\t["+expectedPrefixString+"]");
            System.err.print("But you produce:");
            System.err.println("\t["+prefixSB+"]");
            return "Failed at A0007";
        }
        // let's test this works correctly: Infix
        String expectedInfixString = 
        "lChildRoot, lChildRootRightChild, root, rootRightChild, somewhere3rdLevelRightmost, ";
        // Note: We can't reuse sb because of lambda capture restrictions
        StringBuilder infixSB = new StringBuilder();
        theTree.dfsInfix((t) -> {infixSB.append(t+", ");});
        if (!infixSB.toString().equals(expectedInfixString)){
            System.err.print("Infix should be:");
            System.err.println("\t["+expectedInfixString+"]");
            System.err.print("But you produce:");
            System.err.println("\t["+infixSB+"]");
            return "Failed at A0008";
        }
        // let's test this works correctly: Postfix
        String expectedPostfixString = 
        "lChildRootRightChild, lChildRoot, somewhere3rdLevelRightmost, rootRightChild, root, ";
        StringBuilder postfixSB = new StringBuilder();
        theTree.dfsPostfix((t) -> {postfixSB.append(t+", ");});
        if (!postfixSB.toString().equals(expectedPostfixString)){
            System.err.print("Postfix should be:");
            System.err.println("\t["+expectedPostfixString+"]");
            System.err.print("But you produce:");
            System.err.println("\t["+postfixSB+"]");
            return "Failed at A0009";
        }
        // Now test the BFS code
        String expectedBFSString = 
                "root, lChildRoot, rootRightChild, lChildRootRightChild, somewhere3rdLevelRightmost, ";
        StringBuilder bfsSB = new StringBuilder();
        theTree.bfs((t) -> {bfsSB.append(t+", ");});
        
        if (!bfsSB.toString().equals(expectedBFSString)){
            System.err.print("Breadth First Search should be:");
            System.err.println("\t["+expectedBFSString+"]");
            System.err.print("But you produce:");
            System.err.println("\t["+bfsSB+"]");
            return "Failed at A0010";
        }

        // Now a simple test. Make a new tree with different structure but the same content,
        // then compare
        
        BinarySearchTree<String> theOtherTree = new BinarySearchTree<>();
        var theIter = theTree.iterator();
        while (theIter.hasNext()) {
            theOtherTree.insert(theIter.next());
        }
        // note that the structure is different, but the items are identical
        System.out.println("The tree is:\n"+theOtherTree);
        if (!theTree.equals(theOtherTree)) {
            return "Failed at A0011";
        }
        
        // now back to the original tree
        // now remove the root. This should result in a different tree
        if (!theTree.remove("root")) {
            return "Failed at A0012";
        }
        System.out.println("After removing the root, the tree is:\n" + theTree);
        /*
         * the tree so far:
         *   somewhere3rdLevelRightmost
         * rootRightChild
         *   lChildRoot
         */
        // now try to remove it again. It should fail

        if (theTree.remove("root")) {
            return "Failed at A0013";
        }
        // now remove a node with 1 child:
        if (!theTree.remove("lChildRoot")) {
            return "Failed at A0014";
        }
        System.out.println("After removing lChildRoot (with one child), the tree is:\n" + theTree);
        // fin
        return "";

    }

    public String getTestName() {
        return "Short Binary Search Tree Test";
    }
}
