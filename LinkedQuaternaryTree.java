/**
 * This class was adapted from LinkedBinaryTree.java from the javafoundations 
 * package that was given by the CS230 staff for Midterm 2.
 * This is a linked representation of the Quaternary tree. 
 * 
 * @author Angela Qian
 * @version May 15 2019 
 */

import java.util.*;
import javafoundations.*;
import javafoundations.exceptions.*;

public class LinkedQuaternaryTree<T>
{
    protected QTNode<T> root;

    /** 
     * Constructor. Creates empty quaternary tree. 
     */
    public LinkedQuaternaryTree()
    {
        root = null;
    }
    
    /** 
     * Constructor. Creates a quaternary tree with the specified element as its root.
     * 
     * @param element is a generic type object to be stored in the root 
     */
    public LinkedQuaternaryTree (T element)
    {
        root = new QTNode<T>(element);
        root.setFirst(null); 
        root.setSecond(null); 
        root.setThird(null);
        root.setFourth(null); 
    }
    
    /** 
     * Constructor. Creates a quaternary tree with the four specified subtrees
     * 
     * @param element is the generic type element to be stored in the root 
     * @param first, second, third, fourth are all LinkedQuaternaryTree 
     * objects of generic type 
     */
    public LinkedQuaternaryTree (T element, LinkedQuaternaryTree<T> first, 
    LinkedQuaternaryTree<T> second, 
    LinkedQuaternaryTree<T> third, 
    LinkedQuaternaryTree<T> fourth)
    {
        root = new QTNode<T>(element);
        root.setFirst(first.root);
        root.setSecond(second.root);
        root.setThird(third.root);
        root.setFourth(fourth.root);
    }

    /** 
     * Returns the element stored in the root of the tree. Throws an
     * EmptyCollectionException if the tree is empty.
     *
     * @return genetic type element stored in the root
     */
    public T getRootElement()
    {
        if (root == null)
            throw new EmptyCollectionException ("Get root operation "
                + "failed. The tree is empty.");

        return root.getElement();
    }

    /** 
     * Returns the left subtree of the root of this tree.
     * 
     * @return LinkedQuaternaryTree<T> of the left subtree 
     */
    public LinkedQuaternaryTree<T> getFirst()
    {
        if (root == null)
            throw new EmptyCollectionException ("Get operation "
                + "failed. The tree is empty.");

        LinkedQuaternaryTree<T> result = new LinkedQuaternaryTree<T>();
        result.root = root.getFirst();

        return result;
    }

    /** 
     * Returns the left-middle subtree of the root of this tree.
     * 
     * @return LinkedQuaternaryTree<T> of the left-middle subtree 
     */
    public LinkedQuaternaryTree<T> getSecond()
    {
        if (root == null)
            throw new EmptyCollectionException ("Get operation "
                + "failed. The tree is empty.");

        LinkedQuaternaryTree<T> result = new LinkedQuaternaryTree<T>();
        result.root = root.getSecond();

        return result;
    }

    /** 
     * Returns the right-middle subtree of the root of this tree.
     * 
     * @return LinkedQuaternaryTree<T> of the right-middle subtree 
     */
    public LinkedQuaternaryTree<T> getThird()
    {
        if (root == null)
            throw new EmptyCollectionException ("Get operation "
                + "failed. The tree is empty.");

        LinkedQuaternaryTree<T> result = new LinkedQuaternaryTree<T>();
        result.root = root.getThird();

        return result;
    }

    /** 
     * Returns the right subtree of the root of this tree.
     * 
     * @return LinkedQuaternaryTree<T> of the right subtree 
     */
    public LinkedQuaternaryTree<T> getFourth()
    {
        if (root == null)
            throw new EmptyCollectionException ("Get operation "
                + "failed. The tree is empty.");

        LinkedQuaternaryTree<T> result = new LinkedQuaternaryTree<T>();
        result.root = root.getFourth();

        return result;
    }

    /**
     * Returns the subtree if that root matches the target. 
     * This can only be used to find the children’s root nodes. 
     * 
     * @param target is the string object to be found in the children's 
     * root elements 
     * @return subtree of LinkedQuaternaryTree<T> if the target matches 
     * that child's root element 
     */
    public LinkedQuaternaryTree<T> find (String target)
    {
        LinkedQuaternaryTree<T> subtree = null; 
        if (root != null) { 
            if (root.getFirst() != null && this.getFirst().getRootElement().equals(target)) { 
                subtree = this.getFirst();
            } else if (root.getSecond() != null && this.getSecond().getRootElement().equals(target)) { 
                subtree = this.getSecond(); 
            } else if (root.getThird() != null && this.getThird().getRootElement().equals(target)) { 
                subtree = this.getThird(); 
            } else if (root.getFourth() != null && this.getFourth().getRootElement().equals(target)) { 
                subtree = this.getFourth(); 
            } 
        } 

        if (subtree == null) { 
            throw new ElementNotFoundException("Find operation failed. "
                + "No such element in tree.");
        } 

        return subtree;
    }

    /** 
     * Returns the number of nodes in the tree. 
     * 
     * @return integer of the count 
     */
    public int size()
    {
        int result = 0;

        if (root != null)
            result = root.count();

        return result;
    }
    
    /** 
     * Populates and returns an iterator containing the elements 
     * in the quaternary tree using a levelorder traversal. 
     * 
     * @return iterator of generic elements 
     */
    public Iterator<T> levelorder()
    {
        LinkedQueue<QTNode<T>> queue = new LinkedQueue<QTNode<T>>();
        ArrayIterator<T> iter = new ArrayIterator<T>();

        if (root != null)
        {
            queue.enqueue(root);
            while (!queue.isEmpty())
            {
                QTNode<T> current = queue.dequeue();

                iter.add (current.getElement());

                if (current.getFirst() != null)
                    queue.enqueue(current.getFirst());
                if (current.getSecond() != null)
                    queue.enqueue(current.getSecond());
                if (current.getThird() != null)
                    queue.enqueue(current.getThird());
                if (current.getFourth() != null)
                    queue.enqueue(current.getFourth());
            }
        }

        return iter;
    }

    /** 
     * Satisfies iterable interface using preorder traversal. 
     * 
     * @return iterator of generic type T 
     */
    public Iterator<T> iterator()
    {
        return preorder();
    }

    /** 
     * Checks if the current tree contains a child subtree that has a root 
     * element that matches the target. 
     * 
     * @return boolean true if the child exists, false otherwise
     */
    public boolean containsChild (String target) 
    {
        try {
            Vector<T> children = new Vector<T>(); 
            if (root.getFirst() != null) { 
                children.add(root.getFirst().getElement()); 
            } 
            if (root.getSecond() != null) { 
                children.add(root.getSecond().getElement()); 
            } 
            if (root.getThird() != null) { 
                children.add(root.getThird().getElement()); 
            } 
            if (root.getFourth() != null) { 
                children.add(root.getFourth().getElement()); 
            } 
            //System.out.println("children: " + children); 
            for (int i = 0; i < children.size(); i++) { 
                if (children.get(i).equals(target)) { 
                    return true; 
                } 
            } 
            return false; 
        } catch(EmptyCollectionException e) {
            System.out.println("empty!");
            return false;
        }

    }
    
    /** 
     * Checks if the tree is empty. 
     * 
     * @return boolean true if empty, false otherwise 
     */
    public boolean isEmpty() 
    {
        return size() == 0;
    }

    /** 
     * Formats the tree through an iterator. 
     * 
     * @return String that contains the root elements of each node
     */
    public String toString() 
    {
        String result = "";
        Iterator<T> iter = iterator();
        while(iter.hasNext())
            result += iter.next() + "\n";
        return result;
    }

    /** 
     * Traversal that uses ArrayIterator. 
     * 
     * @return iterator that contains all the root elements 
     */
    public Iterator<T> preorder() 
    {
        ArrayIterator<T> iter = new ArrayIterator<T>();

        if (root != null)
            root.preorder (iter);

        return iter;
    }
    
    /** 
     * Checks if this is a leaf. Leaves have no children. 
     * 
     * @return boolean true if it has children, false if it's a leaf. 
     */
    public boolean hasChild() { 
        if (root.getFirst() == null && root.getSecond() == null && 
        root.getThird() == null && root.getFourth() == null) { 
            return false; 
        } 
        return true; 

    } 

    /** 
     * Setter functions for the left subtree. 
     * 
     * @param subtree to set as the child
     */
    public void setFirst(LinkedQuaternaryTree<T> subtree) { 
        root.setFirst(subtree.root);
    } 

    /** 
     * Setter functions for the left-middle subtree. 
     * 
     * @param subtree to set as the child
     */
    public void setSecond(LinkedQuaternaryTree<T> subtree) { 
        root.setSecond(subtree.root);
    } 

    /** 
     * Setter functions for the right-middle subtree. 
     * 
     * @param subtree to set as the child
     */
    public void setThird(LinkedQuaternaryTree<T> subtree) { 
        root.setThird(subtree.root);
    } 
    
    /** 
     * Setter functions for the right subtree. 
     * 
     * @param subtree to set as the child
     */
    public void setFourth(LinkedQuaternaryTree<T> subtree) { 
        root.setFourth(subtree.root);
    } 
    
    /** 
     * Setter that places the subtree in the next available spot. 
     * Will not insert if all four spots are occupied. 
     * 
     * @param subtree that will be placed in the next available spot as 
     * a child 
     */
    public void setNext(LinkedQuaternaryTree<T> subtree) { 
        if (root.getFirst() == null) { 
            this.setFirst(subtree); 
        } else if (root.getSecond() == null) { 
            this.setSecond(subtree); 
        } else if (root.getThird() == null) { 
            this.setThird(subtree); 
        } else if (root.getFourth() == null) { 
            this.setFourth(subtree); 
        }
    } 

    /** 
     * Main method with test codes. 
     */
    public static void main(String[] args) { 
        System.out.println("--------------------"); 
        LinkedQuaternaryTree<String> tree, area, cat1, cat2, cat3, current; 
        tree = new LinkedQuaternaryTree<String>("start"); 
        current = tree; 
        if (!current.containsChild("Cambridge")) { 
            area = new LinkedQuaternaryTree<String>("Cambridge"); 
            current.setNext(area); 
        } 
        current = current.find("Cambridge");
        System.out.println("Cambridge: " + current); 
        if (!current.containsChild("Food")) { 
            cat1 = new LinkedQuaternaryTree<String>("Food"); 
            current.setNext(cat1); 
        } 
        
        System.out.println("Cambridge and Food: " + current); 
        current = tree; 
        System.out.println("tree: " + current); 
        
        if (!current.containsChild("Cambridge")) { 
            area = new LinkedQuaternaryTree<String>("Cambridge"); 
            current.setNext(area); 
        } else { 
            System.out.println("Cambridge exists."); 
        } 
        
        
        System.out.println("--------------------"); 
        
        LinkedQuaternaryTree<String> t1, t2, t3, test1, result; 
        t1 = new LinkedQuaternaryTree<String>("B"); 
        t2 = new LinkedQuaternaryTree<String>("D"); 
        t3 = new LinkedQuaternaryTree<String>("C", t2, new LinkedQuaternaryTree<String>(), new LinkedQuaternaryTree<String>(), new LinkedQuaternaryTree<String>()); 
        test1 = new LinkedQuaternaryTree<String>("A", t1, t3, new LinkedQuaternaryTree<String>(), new LinkedQuaternaryTree<String>()); 
        System.out.println(test1.levelorder()); 
        System.out.println("first: " + test1.getFirst()); 
        System.out.println("third: (empty)" + test1.getThird()); 
        System.out.println("third null ?"); 

        result = test1.find("C"); 
        System.out.println("Current subtree C: " + result); 
        System.out.println("C has child (true): " + result.hasChild());
        System.out.println("C's first child: " + result.getFirst()); 
        System.out.println("C has D (true): " + result.containsChild("D")); 
        System.out.println("C has E (false): " + result.containsChild("E"));
        result = result.find("D"); 
        System.out.println("Current subtree D: " + result);
        System.out.println("D has child (false): " + result.hasChild());

        LinkedQuaternaryTree<String> t6 = new LinkedQuaternaryTree<String>("G"); 
        result.setNext(t6); 
        System.out.println("Set next (G): ");
        System.out.println("D has G: " + result.containsChild("G"));
        System.out.println("D's first child: " + result.getFirst());
        System.out.println("D's second child: " + result.getSecond());
        
        System.out.println(test1.levelorder()); 
        result = result.find("G"); 
        System.out.println("Current subtree G: " + result);
        System.out.println("G has child (false): " + result.hasChild());

        System.out.println("-----");
        System.out.println(test1); 
        System.out.println("-----");

        //traverse back to tree 
        result = test1; 
        System.out.println(result); 
        System.out.println("A has C (true): " + result.containsChild("C"));
        result = result.find("C"); 
        System.out.println("C has D (true): " + result.containsChild("D"));

        LinkedQuaternaryTree<String> t4 = new LinkedQuaternaryTree<String>("E"); 
        result = test1.find("C");
        result.setFirst(t4); 
        System.out.println("Changed first child of C(D replaced with E): ");
        System.out.println(test1.levelorder()); 

        LinkedQuaternaryTree<String> t5 = new LinkedQuaternaryTree<String>("F"); 
        result.setThird(t5); 
        System.out.println("Changed third child (F): ");
        System.out.println(test1.levelorder()); 

    } 
}

