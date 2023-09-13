package AVLTree;

import java.util.Stack;
public class AVLTree implements Tree {
    private Node root;
    private int size;

    public AVLTree() {
        root = null;
        size = 0;
    }

    @Override
    public boolean add(int value) {
        if (contains(value)) {
            return false;
        } else {
            size++;
            root = addNode(root, value);
            return true;
        }
    }

    private Node addNode(Node node, int value) {
        if (node == null) {
            return new Node(value);
        }

        if (value < node.value) { // 왼쪽
            node.lChild = addNode(node.lChild, value);
            if (getHeight(node.lChild) - getHeight(node.rChild) == 2) {
                if (value < node.lChild.value) {
                    node = rightRotation(node);
                } else {
                    node.lChild = leftRotation(node.lChild);
                    node = rightRotation(node);
                }
            }
        } else { // 오른쪽
            node.rChild = addNode(node.rChild, value);
            if (getHeight(node.lChild) - getHeight(node.rChild) == -2) {
                if (value < node.rChild.value) {
                    node.rChild = rightRotation(node.rChild);
                    node = leftRotation(node);
                } else {
                    node = leftRotation(node);
                }
            }

        }
        updateHeight(node);
        return node;
    }

    private void updateHeight(Node node) {
        node.height = Math.max(getHeight(node.lChild), getHeight(node.rChild)) + 1;
    }

    private Node leftRotation(Node node) {
        Node parent = node.rChild;
        node.rChild = parent.lChild;
        parent.lChild = node;

        updateHeight(parent);
        updateHeight(node);
        return parent;
    }
    private Node rightRotation(Node node) {
        Node parent = node.lChild;
        node.lChild = parent.rChild;
        parent.rChild = node;

        updateHeight(parent);
        updateHeight(node);
        return parent;
    }

    private int getBalanceFactor(Node node) {
        if (node == null) return 0;
        return getHeight(node.lChild) - getHeight(node.rChild);
    }

    private int getHeight(Node node) {
        if (node == null) return -1;
        return node.height;
    }

    @Override
    public boolean remove(int value) {
        if (contains(value)) {
            size--;
            root = removeNode(root, value);
            return true;
        }
        return false;
    }

    private Node removeNode(Node node, int value) {
        if(node == null){
            return null;
        }

        if(value < node.value) {
            node.lChild = removeNode(node.lChild, value);
        }else if(value > node.value) {
            node.rChild = removeNode(node.rChild, value);
        }else{
            if(node.lChild == null && node.rChild == null){
                node = null;
            } else if(node.rChild == null) {
                node = node.lChild;
            } else if(node.lChild == null) {
                node = node.rChild;
            } else {
                Node min = findMinNodeInRightSubTree(node.rChild);
                int tmp = min.value;
                node.value = tmp;
                node.rChild = removeNode(node.rChild, tmp);
            }
        }

        if(node == null) {
            return null;
        }

        int balance = getBalanceFactor(node);
        updateHeight(node);
        // ll
        if(balance >= 2 && getBalanceFactor(node.lChild) >= 0){
            return rightRotation(node);
        }

        // lr
        if(balance >= 2 && getBalanceFactor(node.lChild) < 0){
            node.lChild = leftRotation(node.lChild);
            return node = rightRotation(node);
        }

        // rl
        if(balance <= -2 && getBalanceFactor(node.rChild) > 0){
            node.rChild = rightRotation(node.rChild);
            return node = leftRotation(node);
        }

        //rr
        if(balance <= -2 && getBalanceFactor(node.rChild) <= 0){
            return leftRotation(node);
        }

        return node;
    }

    private Node findMinNodeInRightSubTree(Node node) {
        if (node.lChild != null) {
            return findMinNodeInRightSubTree(node.lChild);
        } else {
            int value = node.value;
            remove(value);
            return new Node(value);
        }
    }
    @Override
    public boolean contains(int value) {
        return search(root, value) != null;
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
    }

    @Override
    public void print() {
        Stack<Node> globalStack = new Stack();
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
                    localStack.push(temp.lChild);
                    localStack.push(temp.rChild);
                    if(temp.lChild != null ||temp.rChild != null)
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

    private Node search(Node node, int value) {
        if (node == null) {
            return null;
        }
        if (node.value == value) {
            return node;
        } else if (node.value > value) {
            return search(node.lChild, value);
        } else {
            return search(node.rChild, value);
        }
    }
}