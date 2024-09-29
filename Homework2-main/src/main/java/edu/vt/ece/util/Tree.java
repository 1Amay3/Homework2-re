package edu.vt.ece.util;

public class Tree<T extends Comparable<T>> implements Cloneable{
    private T data;
    private Tree<T> right;
    private Tree<T> left;

    public Tree(T root) {
        this.data = root;
    }

    public T getData() {
        return data;
    }

    public Tree<T> getRightChild(){
        return right;
    }

    public Tree<T> getLeftChild(){
        return left;
    }

    public void add(T node){
        if(data.compareTo(node)<0){
            if(right==null)
                right = new Tree<T>(node);
            else
                right.add(node);
        }
        else{
            if(left==null)
                left = new Tree<T>(node);
            else
                left.add(node);
        }
    }

    public static void main(String[] args) {
        Tree<Integer> tree = new Tree<Integer>(4);
        tree.add(2);
        tree.add(6);
        tree.add(1);
        tree.add(3);
        tree.add(5);
        tree.add(7);
		/*
 	 	Tree now should be like that
                   4
               2        6
             1   3    5   7
		 */

        // print left branch from the root till the left-most leaf
        Tree<Integer> itr = tree;
        do{
            System.out.println(itr.getData());
        }while((itr = itr.getLeftChild())!=null);
        // print right branch from the root till the right-most leaf
        itr = tree;
        do{
            System.out.println(itr.getData());
        }while((itr = itr.getRightChild()) != null);
    }
}
