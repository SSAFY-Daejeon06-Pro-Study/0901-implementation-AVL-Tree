package AVLTree;
import javax.print.attribute.standard.MediaSizeName;
import java.util.Stack;

public class AVL implements Tree {
    private Node treeRoot;
    private int size;

    private int getHeight(Node root) {
        if(root == null) return 0;
        return root.height;
    }

    //Balance Factor, -1 미만 or 1 초과면 불균형
    private int bf(Node root) {
        return getHeight(root.left) - getHeight(root.right);
    }

    //root를 왼쪽 자식의 오른쪽 서브 트리로 내려, 트리의 균형을 맞춘다.
    private Node rotateRight(Node root) {
        //루트(x), 루트 왼쪽 자식(y), y의 오른쪽 자식(z)
        Node x = root;
        Node y = root.left;
        Node z = y.right;

        //1. y 오른쪽 자식으로 root 설정
        y.right = x;

        //2. 원래 y의 오른쪽 자식인 z를 root의 왼쪽 자식으로 설정
        x.left = z;

        //새 루트 y 반환
        return y;
    }

    //root를 오른쪽 자식의 왼쪽 서브 트리로 내려, 트리의 균형을 맞춘다.
    private Node rotateLeft(Node root) {
        //루트(x), 루트 오른쪽(y), y의 왼쪽 자식(z)
        Node x = root;
        Node y = root.right;
        Node z = y.left;

        //1. y 왼쪽 자식으로 root 설정
        y.left = x;

        //2. 원래 y의 왼쪽 자식인 z를 root의 오른쪽 자식으로 설정
        x.right = z;

        //새 루트 y 반환
        return y;
    }

    private Node balance(Node root) {
        //left 서브트리의 높이가 2 더 높음
        if(bf(root) > 1) {
            //LR 케이스의 경우 root의 left를 대상으로 rotateLeft 연산 수행
            if (bf(root.left) < 0) root.left = rotateLeft(root.left);
            root = rotateRight(root);
        }
        //right 서브트리의 높이가 2 더 높음
        else if(bf(root) < -1) {
            //RL 케이스의 경우 root의 right를 대상으로 rotateRight 연산 수행
            if(bf(root.right) > 0) root.right = rotateRight(root.right);
            root = rotateLeft(root);
        }
        return root;
    }

    private boolean findNode(Node root, int value) {
        //탐색 노드가 없는 경우
        if(root == null) return false;

        if(root.value == value) return true;
            //Left로 이동
        else if(root.value > value) return findNode(root.left, value);
            //Right로 이동
        else return findNode(root.right, value);
    }

    /*** Node addNode(Node root, int value)
     * 1. 루트와 값이 같다면 예외 throws
     *
     * 2. 루트를 기준으로 왼쪽과 오른쪽 탐색,
     * 2-1. 왼쪽에 자식 노드가 없는 경우, 왼쪽 자식으로 새 노드 설정
     *      있는 경우, root.left = 재귀 호출
     *
     * 2-2. 오른쪽에 자식 노드가 없는 경우, 오른쪽 자식으로 새 노드 설정
     *      있는 경우, root.right = 재귀 호출
     *
     * 3. 루트의 높이 값 업데이트
     * 4. balance 호출, 리턴
     */

    /** Node minNode(Node root)

    /*** 반환값 Node deleteNode(Node root, int value, Node x)
     * 1. if(root.value == value) 루트가 삭제 값(삭제 수행)
     *    x.value = root.value
     * 1-1. if(root.left == null) return x.value = root.value
     *      else if(root.right == null) return root.left
     *      else
     *          root.value = minNode(root)
     *          return deleteNode(root.left, root.value)
     *
     *
     * 2. 삭제할 원소를 찾음
     * 2-1. if(value < root.value)
     *          if(root.left != null) root.left = deleteNode(root.left, value)
     *          else throw 예외
     *     else
     *          if(root.right != null) root.right = deleteNode(root.right, value)
     *          else throw 예외
     *
     * 3. 루트의 높이 값 업데이트
     * 4. balance 호출, 리턴
     */

    private Node addNode(Node root, int value) {
        //중복 노드가 있는 경우
        if(root.value == value) throw new DuplicateException();

        //value가 들어갈 위치를 찾음
        if(root.value > value) {
            if(root.left == null) root.left = new Node(value, 1);
            else root.left = addNode(root.left, value);
        }
        else {
            if(root.right == null) root.right = new Node(value, 1);
            else root.right = addNode(root.right, value);
        }

        root.height = Math.max(getHeight(root.left), getHeight(root.right)) + 1;
        return balance(root);
    }

    private Node findMinNode(Node root) {
        while(root.left != null) root = root.left;
        return root;
    }

    private Node deleteNode(Node root, int value, Node x) {
        //삭제 노드가 없는 경우
        if(root == null) {
            x.left = x;
            return null;
        }

        if(root.value == value) {
            //삭제 노드의 값을 x에 백업
            if(x.right != x) {
                x.value = root.value;
                x.right = x;
            }

            if(root.left == null) root = root.right;
            else if(root.right == null) root = root.left;
            else {
                root.value = findMinNode(root.right).value;
                root.right = deleteNode(root.right, root.value, x);
            }
            return root;
        }

        //삭제 노드는 현재 노드의 오른쪽 서브 트리에 존재 (오른쪽 자식이 삭제될 경우, 현재 노드의 오른쪽 자식 포인터도 변경)
        else if(root.value < value) root.right = deleteNode(root.right, value, x);
        //삭제 노드는 현재 노드의 왼쪽 서브 트리에 존재 (왼쪽 자식이 삭제될 경우, 현재 노드의 왼쪽 자식 포인터도 변경)
        else root.left = deleteNode(root.left, value, x);

        root.height = Math.max(getHeight(root.left), getHeight(root.right)) + 1;
        return balance(root);
    }

    @Override
    public boolean add(int value) {
        if(treeRoot == null) {
            treeRoot = new Node(value, 1);
            size++;
            return true;
        }
        try {
            treeRoot = addNode(treeRoot, value);
            size++;
            return true;
        } catch (DuplicateException e) {
            return false;
        }
    }

    @Override
    public boolean remove(int value) {
        Node x = new Node(0,0);
        treeRoot = deleteNode(treeRoot, value, x);
        if(x.left == x) return false;
        return true;
    }

    @Override
    public boolean contains(int value) {
        return findNode(treeRoot, value);
    }

    @Override
    public boolean isEmpty() {
        return (size == 0);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        treeRoot = null;
        size = 0;
    }

    @Override
    public void print() {
        Stack<Node> globalStack = new Stack();
        globalStack.push(treeRoot);
        int emptyLeaf = 32;
        boolean isRowEmpty = false;
        System.out.println("****......................................................****");
        while (!isRowEmpty) {
            Stack<Node> localStack = new Stack();
            isRowEmpty = true;
            for (int j = 0; j < emptyLeaf; j++)
                System.out.print(' ');
            while (!globalStack.isEmpty()) {
                Node temp = globalStack.pop();
                if (temp != null) {
                    System.out.print(temp.value);
                    localStack.push(temp.left);
                    localStack.push(temp.right);
                    if (temp.left != null || temp.right != null)
                        isRowEmpty = false;
                } else {
                    System.out.print("--");
                    localStack.push(null);
                    localStack.push(null);
                }
                for (int j = 0; j < emptyLeaf * 2 - 2; j++)
                    System.out.print(' ');
            }
            System.out.println();
            emptyLeaf /= 2;
            while (!localStack.isEmpty())
                globalStack.push(localStack.pop());
        }
        System.out.println("****......................................................****\n\n");
    }
}