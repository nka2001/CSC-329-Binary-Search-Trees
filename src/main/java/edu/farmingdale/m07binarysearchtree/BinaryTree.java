 /*
 * (c) David Gerstl, 2021. all rights reserved. For use
 * in my CSC programming classes
 */
package edu.farmingdale.m07binarysearchtree;

import java.util.LinkedList;
import java.util.Queue;
import java.util.function.Consumer; // Consumer<T>. 
//See docs.oracle.com/javase/8/docs/api/java/util/function/Consumer.html

/**
 *
 * @author gerstl Binary tree class. Note that this is not a BST class as this
 * imposes no ordering on the nodes
 * @param <T> type to be stored in the tree. Must implement Comparable
 */
public class BinaryTree<T extends Comparable<T>> {

    /**
     * Node for a binary tree. Includes a parent pointer and is protected so
     * derived classes have access.
     *
     * @param <T> Type that implements Comparable so that this can be subclassed
     * to impose ordering
     */
    protected class Node<T extends Comparable<T>> {

        T data;
        Node<T> leftChild;
        Node<T> rightChild;
        // MANY binary tree implementations do not store a parent pointer. 
        // algorithms can *mostly* be designed to work efficiently without it
        // but this makes removal and the iterator easier to write 
        // non-recursively
        Node<T> parent;

        Node() {
            leftChild = null;
            rightChild = null;
            parent = null;
        }

        Node(Node theParent) {
            leftChild = null;
            rightChild = null;
            parent = theParent;
        }
    }
    protected Node root;
    // This variable can be used in your methods to toggle verbose printing
    // to System.err 
    protected Boolean verbose;

    /**
     * Default CTOR
     */
    BinaryTree() {
        root = null;
        verbose = false;
    }

    /**
     * Set the verbose variable. This does nothing in my code, but you are free
     * to add verbose output to System.err that is toggled with this
     */
    public void setVerbose() {
        verbose = true;
    }

    /**
     * See setVerbose. Unsets the variable
     */
    public void unsetVerbose() {
        verbose = false;
    }

    /** 
     * Count nodes. This could be done with a maintained count variable, 
     * but also by counting recursively (as is done here).
     * @return the count of nodes. 
     */
    public int numberOfNodes() {
        // note that root.left and root.right 
        return numberOfNodes(root);
    }

    /**
     * Internal method to count nodes in a subtree recursively
     * @param t a node 
     * @return the count of nodes in the tree anchored at t
     */
    int numberOfNodes(Node t) {
        if (null == t) {
            return 0;
        }
        return 1 + numberOfNodes(t.leftChild) + numberOfNodes(t.rightChild);
    }

    /**
     * Height method
     * @return the height of the tree
     */
    public int height() {
        return height(root);
    }

    /** 
     * a recursive height method
     * @param t a node
     * @return The height of the tree anchored at node
     */
    int height(Node t) {
        if (null == t) {
            return -1;
        }
        return 1 + Math.max(height(t.leftChild), height(t.rightChild));
    }

    /**
     * 
     * @return true iff the tree is empty 
     */
    public boolean isEmpty() {
        return (null == root);
    }


    /**
     * clears the entire tree
     */
    public void clear() {
        // yay for garbage collection
        root = null;
    }

    /**
     * An enumerated type used by the internal function to indicate which type
     * of depth first search (prefix/infix/postfix) to run
     */
    enum DfsType {
        PREFIX,
        INFIX,
        POSTFIX
    }
    /**
     * A recursive DFS function
     * @param dfs The type of dfs (pre/in/post-fix).
     * @param t The node
     * @param actionClass An object implementing the Consumer interface. 
     * This is a SAM interface, and if the caller uses a lambda, 
     * the single method will be inferred to be void accept(T t);
     * see https://docs.oracle.com/javase/8/docs/api/java/util/function/Consumer.html
     * See my call to the three wrappers in ShortBinarySearchTreeTest.java
     */
        void recursiveDfs(DfsType dfs, Node t, Consumer actionClass) {
        if (null == t) {
            return;
        }
        if (dfs.equals(DfsType.PREFIX)) {
            actionClass.accept(t.data);
        }
        recursiveDfs(dfs, t.leftChild, actionClass);
        if (dfs.equals(DfsType.INFIX)) {
            actionClass.accept(t.data);
        }
        recursiveDfs(dfs, t.rightChild, actionClass);
        if (dfs.equals(DfsType.POSTFIX)) {
            actionClass.accept(t.data);
        }
    }

    /**
     * Public wrapper for recursiveDfs for prefix traversal
     * @param actionObject An object of a class implementing Consumer
     * (so it has .accept(T t)
     */
    public void dfsPrefix(Consumer actionObject) {
        recursiveDfs(DfsType.PREFIX, root, actionObject);
    }

    /**
     * Public wrapper for recursiveDfs for infix traversal
     * @param actionObject An object of a class implementing Consumer
     * (so it has .accept(T t)
     */
    public void dfsInfix(Consumer actionObject) {
        recursiveDfs(DfsType.INFIX, root, actionObject);
    }

        /**
     * Public wrapper for recursiveDfs for postfix traversal
     * @param actionObject An object of a class implementing Consumer
     * (so it has .accept(T t)
     */
    public void dfsPostfix(Consumer actionObject) {
        recursiveDfs(DfsType.POSTFIX, root, actionObject);
    }

    /**
     * Performs a breadth-first search of the tree
     * @param actionObject An object of a class implementing Consumer
     * (so it has .accept(T t)
     */
    public void bfs(Consumer actionObject) {
        
        Node<T> current = new Node<T>();
        current = root;
        Queue<Node<T>> nq = new LinkedList<Node<T>>();
        nq.add(root);
        
        while(!nq.isEmpty()){
            current = nq.poll();
            
            if(current.leftChild != null){
                nq.add(current.leftChild);
            }
            if(current.rightChild != null){
                nq.add(current.rightChild);
            }
            System.out.println(current.data);
        }
        
        
        
    } // bfs

    // in order to make this print correctly, we can override toString() and 
    // a tree will be printable by System.out.print[ln].
    /**
     * Performs a right-first inorder traversal, and prints the content of each
     * node with an indent proportional to it's depth
     * @param nd Node to print from
     * @param indent Degree of current indent (will increase in deeper subnodes)
     * @return A StringBuilder picture of the nodes.
     */
    StringBuilder reversedInorder(Node nd, int indent) {
        var rv = new StringBuilder();
        if (null != nd) {
            if (null != nd.rightChild) {
                rv.append(reversedInorder(nd.rightChild, indent + 4));
            }
            for (int i = 0; i < indent + 5; ++i) {
                rv.append(" ");
            }
            rv.append("(" + (indent / 4) + ")");
            rv.append(nd.data);
            rv.append("\n");
            if (null != nd.leftChild) {
                rv.append(reversedInorder(nd.leftChild, indent + 4));
            }
        }
        return rv;
    }

    /** 
     * Calls reversedInorder to do a traversal and build a string representation
     * of a binary tree
     * @return a String representation of the tree
     */
    @Override
    public String toString() {
        return reversedInorder(root, 0).toString();
    }
}
