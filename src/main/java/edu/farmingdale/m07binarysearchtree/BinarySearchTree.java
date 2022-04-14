/*
 * (c) David Gerstl, 2021. all rights reserved. For use
 * in my CSC programming classes
 */
package edu.farmingdale.m07binarysearchtree;

import java.util.Iterator;
import com.google.gson.*; // for cloning
import com.google.gson.reflect.TypeToken; // for cloning
import java.lang.reflect.Type; // for cloning
import java.util.Stack;

/**
 *
 * @author gerstl
 * @param <T>
 */
public class BinarySearchTree<T extends Comparable<T>> extends BinaryTree<T> implements Iterable<T> {

    /**
     * Recursively copy a node (the content of the node is copied using gson)
     *
     * @param copyMe a node to copy
     * @return a node that is the root of a copy of the tree copyMe
     */
    Node<T> deepCopyNode(Node<T> copyMe) {
        if (null == copyMe) {
            return null;
        }
        Node<T> rv = new Node();
        Gson gson = new Gson();
        Type typeOfT = new TypeToken<T>() {
        }.getType();
        rv.data = gson.fromJson(gson.toJson(copyMe.data), typeOfT);
        rv.leftChild = deepCopyNode(copyMe.leftChild);
        if (null != rv.leftChild) {
            rv.leftChild.parent = rv;
        }
        rv.rightChild = deepCopyNode(copyMe.rightChild);
        if (null != rv.rightChild) {
            rv.rightChild.parent = rv;
        }
        return rv;
    }

    /**
     * Copy ctor. Uses deepCopyNode()
     *
     * @param copyMe The other tree to copy
     */
    public BinarySearchTree(BinarySearchTree copyMe) {
        // set verbose first so deepCopy can print
        verbose = copyMe.verbose;
        if (verbose) {
            System.err.println("Copying this tree: " + copyMe);
        }
        root = deepCopyNode(copyMe.root);
    }

    /**
     * Default ctor
     */
    public BinarySearchTree() {
        root = null;
        verbose = false;
    }

    /**
     * Deep copy a tree (using deepCopyNode)
     *
     * @param copyMe The tree to clone
     */
    public void clone(BinarySearchTree copyMe) {
        root = deepCopyNode(copyMe.root);
    }

    /**
     * Iterator--this iterator returns the node content IN ORDER
     *
     * @return an iterator that produces nodes in order.
     */
    public Iterator<T> iterator() {
        
        Stack<Node<T>> s = new Stack<Node<T>>();
        
        Node<T> curr = root;
        
        while(root != null){
            s.push(root);
            root = root.leftChild;
        } 
        
        Iterator<T> iterRv = new Iterator<T>() {

        
            
            
            @Override
            public boolean hasNext() {
                return !s.isEmpty();
            } // hasNext()

            @Override
            public T next() {
                
                Node<T> n = s.pop();
                
                if(n.rightChild != null){
                    n = n.rightChild;
                    while(n != null){
                        s.push(n);
                        n = n.leftChild;
                    }
                }
                
                
                
                
                
                return n.data;
            } // next()

            // iterator remove not supported
            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            } // remove()
        }; // anon inner class iterator
        return iterRv;
    }

    // note: We cannot be Comparable since trees are not fully ordered.
    // instead. we implement equals. Note that we judge two trees as equal if they
    // have the same elements (no matter the structure). 
    // note: The requirement of hashCode() is that objects that are 
    // equal should have the same hashcode, so we'll override hashCode() and 
    // use the content values WITHOUT THE STRUCTURE
    /**
     * Return
     *
     * @param other Another tree to compare with
     * @return true iff the two trees contain exactly the same items (the
     * structure is not taken into account)
     */
    public boolean equals(BinarySearchTree other) {
        if (verbose) {
            System.err.println("Comparing:\tTree1:\n" + toString());
            System.err.println("With:\tTree2:\n" + other);
        }
        // if they are the same object, return true. Nothing to do
        if (this == other) {
            return true;
        }
        // Step 1: Threshold question - same number of nodes
        // if these are computed, maybe remove this
        // as it's no more efficient than a full traversal
        // Step 2: now create iterators through each tree and compare. If we find a 
        // difference, the two trees are not the same 
        // Step 3: Finally, if the two trees survive that guantlet, they are the same.
        return false;
    }

    // see note before equals()
    public int hashCode() {
        int rv = 0;
        Iterator<T> meIter = iterator();
        while (meIter.hasNext()) {
            T meValue = meIter.next();
            // no exception on overflow, so this is fine
            rv += meValue.hashCode();
            // We can add a positional element (order) but not a structural one
        }
        return rv;
    }

    public boolean insert(T addMe) {

        Node<T> newNode = new Node<T>();
        newNode.data = addMe;

        Node<T> traverseNode = root;
        Node<T> trail = traverseNode;

        if (search(addMe)) {
            return false;//this means that it was found
        }
        if (root == null) {
            root = newNode;
            super.numberOfNodes();
            return true;
        }

        while (traverseNode != null) {

            if (traverseNode.data.compareTo(addMe) < 0) {
                trail = traverseNode;
                traverseNode = traverseNode.rightChild;
            } else {
                trail = traverseNode;
                traverseNode = traverseNode.leftChild;
            }

        }

        if (traverseNode == null) {

            if (trail.data.compareTo(newNode.data) < 0) {
                trail.rightChild = newNode;
            } else {
                trail.leftChild = newNode;
            }

            super.numberOfNodes();
            return true;
        }

        return false;
    }

    public boolean remove(T removeMe) {

        return false;
    }

    public boolean search(T findMe) {
        Node<T> current = root;
        while (null != current && !current.data.equals(findMe)) {
            current = (findMe.compareTo(current.data) < 0) ? current.leftChild : current.rightChild;
        }
        return null != current;
    }
}
