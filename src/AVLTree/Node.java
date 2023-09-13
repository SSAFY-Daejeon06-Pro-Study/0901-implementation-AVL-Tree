package AVLTree;

/**
 * inner class로 두어도 상관 없음
 * Node 클래스도 알아서 구현
 */
public class Node {
    int value;
    int height;
    Node left;
    Node right;
    Node(int value, int height) {
        this.value = value;
        this.height = height;
    }
}
