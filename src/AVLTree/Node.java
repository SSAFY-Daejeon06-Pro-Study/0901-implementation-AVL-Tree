package AVLTree;

/**
 * inner class로 두어도 상관 없음
 * Node 클래스도 알아서 구현
 */
public class Node {
    int value;
    int height;
    Node rChild, lChild;

    public Node(int value) {
        this.value = value;
    }
}

//public class Node {
//    int value;
//    int height;
//    Node right, left;
//
//    public Node(int value) {
//        this.value = value;
//    }
//}
