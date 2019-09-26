/**
 * This class was adapted from BTNode.java from the javafoundations 
 * package that was given by the CS230 staff for Midterm 2. It represents 
 * a tree node that can point to four children. 
 * 
 * @author Angela Qian
 * @version May 15 2019 
 */

import java.util.*;
import javafoundations.ArrayIterator; 

public class QTNode<T>
{
  protected T element;
  protected QTNode<T> first, second, third, fourth;
  
  /** 
   * Constructor with one argument that takes generic type to set the 
   * root element of the node
   * 
   * @param T element of genetic type 
   */
  public QTNode (T element)
  {
    this.element = element;
    first = second = third = fourth = null;
  }
  
  /** 
   * Returns element stored in node
   * 
   * @return T generic type of the root element 
   */
  public T getElement()
  {
    return element;
  }
  
  /** 
   * Sets element stored in the node 
   * 
   * @param element generic type to store in node 
   */
  public void setElement (T element)
  {
    this.element = element;
  }
  
  /** 
   * Returns left subtree of the node
   * 
   * @return QTNode of the left subtree
   */
  public QTNode<T> getFirst()
  {
    return first;
  }
  
  /** 
   * Returns left-middle subtree of the node
   * 
   * @return QTNode of the left-middle subtree
   */
  public QTNode<T> getSecond()
  {
    return second;
  }
  
  /** 
   * Returns right-middle subtree of the node
   * 
   * @return QTNode of the right-middle subtree
   */
  public QTNode<T> getThird()
  {
    return third;
  }
  
  /** 
   * Returns right subtree of the node
   * 
   * @return QTNode of the right subtree
   */
  public QTNode<T> getFourth()
  {
    return fourth;
  }
  
  /** 
   * Sets left subtree of the node
   * 
   * @param QTNode of the left subtree to be set 
   */
  public void setFirst (QTNode<T> newElement)
  {
    this.first = newElement;
  }
  
  /** 
   * Sets left-middle subtree of the node
   * 
   * @param QTNode of the left-middle subtree to be set 
   */
  public void setSecond (QTNode<T> newElement)
  {
    this.second = newElement;
  }
  
  /** 
   * Sets right-middle subtree of the node
   * 
   * @param QTNode of the right-middle subtree to be set 
   */
  public void setThird (QTNode<T> newElement)
  {
    this.third = newElement;
  }
  
  /** 
   * Sets right subtree of the node
   * 
   * @param QTNode of the right subtree to be set 
   */
  public void setFourth (QTNode<T> newElement)
  {
    this.fourth = newElement;
  }
   
  /** 
   * Returns element in the subtree that matches the target 
   * 
   * @param target of generic element to find in the tree 
   * @return QTNode<T> the element that matches, null if not found 
   */
  public QTNode<T> find (T target)
  {
    QTNode<T> result = null;
    
    if (element.equals(target))
      result = this;
    else
    {
      if (first != null)
        result = first.find(target);
      if (result == null && second != null)
        result = second.find(target);
      if (result == null && third != null)
        result = third.find(target);
      if (result == null && fourth != null)
        result = fourth.find(target);
    }
    
    return result;
  }
  
  /**
   * Returns the number of node s
   * 
   * @return integer of the count
   */
  public int count()
  {
    int result = 1;
    
    if (first != null)
      result += first.count();
    
    if (second != null)
      result += second.count();
    
    if (third != null)
      result += third.count();
    
    if (fourth != null)
      result += fourth.count();
    return result;
  }
  
  /** 
   * Traversal that checks the root element before traversing the children. 
   * 
   * @param iter uses the ArrayIterator from the javafoundations package. 
   */
  public void preorder (ArrayIterator<T> iter) 
  {
    iter.add(element);
    
    if(first != null)
      first.preorder(iter);
    
    if(second != null)
      second.preorder(iter);
      
    if(third != null)
      third.preorder(iter);
    
    if(fourth != null)
      fourth.preorder(iter);
  }
  
}
  