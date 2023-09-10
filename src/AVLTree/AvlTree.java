package AVLTree;

import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

public class AvlTree implements Tree{

    private Node root;
    private static int size;

    @Override
    public boolean add(int value) {

        if(!contains(value)) {
            root = addValue(root, value);
            size++;
            return true;
        }

        return false;
    }

    private Node addValue(Node node, int value) {
        if(node == null){
            node = new Node(value);
        }

        else if(node.value > value){
            node.left = addValue(node.left, value);

            if(getHeight(node.left) - getHeight(node.right) == 2){
                if(value< node.left.value){ // ll
                    node = ll(node);
                }else{ // lr
                    node = lr(node);
                }
            }
        }

        else if(node.value < value){
            node.right = addValue(node.right, value);

            if(getHeight(node.left) - getHeight(node.right) == -2){
                if(value < node.right.value){ // rl
                    node = rl(node);
                }else{ // rr
                    node = rr(node);
                }
            }
        }

        updateHeight(node);

        return node;
    }

    private Node ll(Node parent){
        Node child = parent.left;
        parent.left = child.right;
        child.right = parent;

        updateHeight(child);
        updateHeight(parent);

        return child;
    }

    private Node rr(Node parent){
        Node child = parent.right;
        parent.right = child.left;
        child.left = parent;

        updateHeight(child);
        updateHeight(parent);

        return child;
    }

    private Node lr(Node parent){
        parent.left = rr(parent.left);
        return ll(parent);
    }

    private Node rl(Node parent){
        parent.right = ll(parent.right);
        return rr(parent);
    }

    private void updateHeight(Node node){
        if(node != null){
            int lh = getHeight(node.left);
            int rh = getHeight(node.right);
            node.height = Math.max(lh, rh) + 1;
        }
    }

    private int getHeight(Node node){
       return (node == null) ? -1 : node.height;
    }

    @Override
    public boolean remove(int value) {
        if(contains(value)){
            root = deleteValue(root, value);
            size--;
            return true;
        }
        return false;
    }

    private Node deleteValue(Node node, int value) {
        if(node == null){
            return null;
        }

        if(value < node.value){
            node.left = deleteValue(node.left, value);
        }else if(value > node.value){
            node.right = deleteValue(node.right, value);
        }else{
            // 단말 노드일 때
            if(node.left == null && node.right == null){
                node = null;
            }
            // 왼쪽 자식을 가지고 있는 경우
            else if(node.right == null){
                node = node.left;
            }

            // 오른쪽 자식을 가지고 있는 경우
            else if(node.left == null){
                node = node.right;
            }

            // 두 개의 자식을 가지고 있는 경우
            else{
                Node min = findMin(node.right); // 오른쪽 자식의 최소를 가져옴
                int tmp = min.value;
                node.value = tmp;
                node.right = deleteValue(node.right, tmp); // 해당 tmp를 가지고 있는 노드 삭제
            }
        }

        if(node == null){
            return null;
        }

        int balance = getBalance(node);

        // ll
        if(balance >= 2 && getBalance(node.left) >= 0){
            return ll(node);
        }

        // lr
        if(balance >= 2 && getBalance(node.left) < 0){
            return lr(node);
        }

        // rl
        if(balance <= -2 && getBalance(node.right) > 0){
            return rl(node);
        }

        //rr
        if(balance <= -2 && getBalance(node.right) <= 0){
            return rr(node);
        }

        return node;
    }

    private int getBalance(Node node) {
        if(node == null){
            return 0;
        }
        return getHeight(node.left) - getHeight(node.right);
    }

    private Node findMin(Node node){
        while (node.left != null){
            node = node.left;
        }
        return node;
    }

    @Override
    public boolean contains(int value) {
        Node tmp = root;
        while (tmp != null){
            if(tmp.value > value){
                tmp = tmp.left;
            }else if(tmp.value < value){
                tmp = tmp.right;
            }else{
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    @Override
    public void print() {
        Stack<Node> globalStack = new Stack<>();
        globalStack.push(root);
        int emptyLeaf = 32;
        boolean isRowEmpty = false;
        System.out.println("****......................................................****");
        while(isRowEmpty==false) {

            Stack<Node> localStack = new Stack();
            isRowEmpty = true;
            for(int j=0; j<emptyLeaf; j++)
                System.out.print(' ');
            while(globalStack.isEmpty()==false) {
                Node temp = globalStack.pop();
                if(temp != null)
                {
                    System.out.print(temp.value);
                    localStack.push(temp.left);
                    localStack.push(temp.right);
                    if(temp.left != null ||temp.right != null)
                        isRowEmpty = false;
                }
                else
                {
                    System.out.print("--");
                    localStack.push(null);
                    localStack.push(null);
                }
                for(int j=0; j<emptyLeaf*2-2; j++)
                    System.out.print(' ');
            }
            System.out.println();
            emptyLeaf /= 2;
            while(localStack.isEmpty()==false)
                globalStack.push( localStack.pop() );
        }
        System.out.println("****......................................................****\n\n");
    }
}
